package fr.sacem.priam.common.util;

import com.google.common.base.Throwables;
import com.jcraft.jsch.*;
import fr.sacem.priam.common.constants.ConfigurableProperty;
import org.springframework.util.Assert;

import java.io.*;
import java.util.Hashtable;
import java.util.Vector;


import static fr.sacem.priam.common.constants.EnvConstants.*;
import static org.springframework.util.StringUtils.hasText;

/**
 * Created by benmerzoukah.
 */

public class SftpUtil {
    
    public static final String NEW_LINE = "\n";
    
    public enum SftpServer {

        FELIX(FTP_FELIX_USERNAME, FTP_FELIX_SERVER, FTP_FELIX_PORT, FTP_FELIX_PASSWORD, FTP_FELIX_HOME_DIR);

        private String user;
        private String host;
        private int port;
        private String pwd;
        private String homeDir;

        SftpServer(ConfigurableProperty user, ConfigurableProperty host, ConfigurableProperty port, ConfigurableProperty pwd, ConfigurableProperty homeDir) {
            this.user= user.toString();
            this.host = host.toString();
            String portStr = port.toString();
            try{
                this.port = hasText(portStr) ? Integer.parseInt(portStr) : 22;
            }catch (NumberFormatException e){
                this.port = 22;
            }
            this.pwd = pwd.toString();

            String dir = homeDir.toString();
            this.homeDir = dir != null ? dir: "";
        }

        public boolean isValid(){
            return hasText(host) && hasText(user) && hasText(homeDir);
        }
    }

    public static ChannelSftp getSftpChannel(SftpServer sftpServer) throws JSchException, SftpException {
        JSch jsch = new JSch();
        Hashtable<String, String> config = new Hashtable<>();
        config.put("StrictHostKeyChecking", "no");
        JSch.setConfig(config);
        Session session = jsch.getSession(sftpServer.user, sftpServer.host, sftpServer.port);
        session.setPassword(sftpServer.pwd);
        session.connect();

        // open sftp channel
        ChannelSftp channel = null;
        try {
            channel = (ChannelSftp) session.openChannel( "sftp" );
            channel.connect();
            channel.cd(sftpServer.homeDir);
        } catch (Exception e) {
            // for any exception, disconnect ssh session and propagate
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            
            if (session.isConnected()) {
                session.disconnect();
            }
            Throwables.propagate(e);
        }

        return channel;
    }

    public static void closeSftpChannel(Channel sftpChannel) {
        try {
            if (sftpChannel != null) {
                if (sftpChannel.isConnected()) {
                    sftpChannel.disconnect();
                }

                Session session = sftpChannel.getSession();
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            }
        } catch (JSchException e) {
            //ignore
        }
    }

    public static Vector<ChannelSftp.LsEntry> lsFilesOnlyWithSuffix(ChannelSftp sftpChannel, String path, final String suffix) throws SftpException {
        final Vector<ChannelSftp.LsEntry> files = new Vector<>();
        if(sftpChannel != null && path != null){
            sftpChannel.ls(path, new ChannelSftp.LsEntrySelector() {
                @Override
                public int select(ChannelSftp.LsEntry entry) {
                    if(!entry.getAttrs().isDir() && entry.getFilename().endsWith(suffix)){
                        files.add(entry);
                    }
                    return ChannelSftp.LsEntrySelector.CONTINUE;
                }
            });
        }
        return files;
    }

    public static Vector<ChannelSftp.LsEntry> lsFilesOnly(ChannelSftp sftpChannel, String path) throws SftpException {
        final Vector<ChannelSftp.LsEntry> files = new Vector<>();
        if(sftpChannel != null && path != null){
            sftpChannel.ls(path, new ChannelSftp.LsEntrySelector() {
                @Override
                public int select(ChannelSftp.LsEntry entry) {
                    if(!entry.getAttrs().isDir()){
                        files.add(entry);
                    }
                    return ChannelSftp.LsEntrySelector.CONTINUE;
                }
            });
        }
        return files;
    }

    public static Vector<ChannelSftp.LsEntry> lsDirectoriesOnly(ChannelSftp sftpChannel, String path) throws SftpException {
        final Vector<ChannelSftp.LsEntry> files = new Vector<>();
        if(sftpChannel != null && path != null){
            sftpChannel.ls(path, new ChannelSftp.LsEntrySelector() {
                @Override
                public int select(ChannelSftp.LsEntry entry) {
                    if(entry.getAttrs().isDir()){
                        files.add(entry);
                    }
                    return ChannelSftp.LsEntrySelector.CONTINUE;
                }
            });
        }
        return files;
    }

    public static void uploadFile(SftpServer sftpServer, File fileToUpload) throws JSchException, SftpException, IOException {
        uploadFile(sftpServer, fileToUpload, null);
    }

    public static void uploadFile(SftpServer sftpServer, File fileToUpload, String remoteName) throws JSchException, SftpException, IOException {
        uploadFile(sftpServer, fileToUpload, remoteName, null);
    }

    public static void uploadFile(SftpServer sftpServer, File fileToUpload, String remoteName, String flagFileName) throws JSchException, SftpException, IOException {
        Assert.isTrue(fileToUpload.exists() && fileToUpload.isFile(), "Le fichier " + remoteName + " doit exister !!");
        ChannelSftp channelSftp = null;
        try{
            channelSftp = getSftpChannel(sftpServer);
            _uploadFile(channelSftp, fileToUpload, remoteName);
            if(hasText(flagFileName)){
                _createFile(channelSftp, flagFileName, remoteName);
            }
        }finally {
            closeSftpChannel(channelSftp);
        }
    }

    private static void _uploadFile(ChannelSftp channelSftp, File fileToUpload, String remoteName) throws JSchException, SftpException, IOException {
        String targetFileName = hasText(remoteName) ? remoteName : fileToUpload.getName();
        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileToUpload))){
            channelSftp.put(bufferedInputStream, targetFileName);
        } catch (FileNotFoundException e) {
            //we can ignore this, the file is validated before
        }
    }

    private static void _createFile(ChannelSftp channelSftp, String fileName, String text) throws SftpException, IOException {
        Assert.hasText(fileName, "");
        try(OutputStream out = channelSftp.put(fileName)){
            out.write((text + NEW_LINE).getBytes());
        }
    }
}
