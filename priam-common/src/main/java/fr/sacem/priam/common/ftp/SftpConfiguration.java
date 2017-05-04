//package fr.sacem.priam.common.ftp;
//
//import com.jcraft.jsch.ChannelSftp;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.integration.file.remote.session.CachingSessionFactory;
//import org.springframework.integration.file.remote.session.SessionFactory;
//import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
//
///**
// * Created by benmerzoukah on 03/05/2017.
// */
////@Configuration
//public class SftpConfiguration {
//
//    @Value("${priam.ftp.deposit.server.name}")
//    private String serverName;
//
//    @Value("${priam.ftp.deposit.server.port}")
//    private Integer port;
//
//    @Value("${priam.ftp.deposit.user.name}")
//    private String userName;
//
//    @Value("${priam.ftp.deposit.user.password}")
//    private String password;
//
//    @Bean
//    public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
//        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
//        factory.setHost(serverName);
//        factory.setPort(port);
//        factory.setUser(userName);
//        factory.setPassword(password);
//        factory.setAllowUnknownKeys(true);
//
//        return new CachingSessionFactory<>(factory);
//    }
//
//}
