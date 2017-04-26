package fr.sacem.priam.common.zip;

import fr.sacem.priam.common.exception.TechnicalException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by benmerzoukah on 25/04/2017.
 */
public class UnzipFile {

    private File file;

    public UnzipFile(File zipFile) {
        file = zipFile;
    }

    public void uncompress(File outputDir) throws IOException, ArchiveException, TechnicalException {
        if(file == null || !file.exists()) {
            throw new TechnicalException("Le fichier en entrée n'exsite pas : " + file);
        }
        if(outputDir ==null || !outputDir.exists() || !outputDir.isDirectory()) {
            throw new TechnicalException("Le chemin spécifié n'existe pas : " + outputDir);
        }
        try (ArchiveInputStream in=new ArchiveStreamFactory().createArchiveInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            ArchiveEntry entry;
            File entryFile;
            while ((entry = in.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    String name = entry.getName().replace('\\', '/');
                    entryFile = new File(outputDir, name);
                    FileUtils.forceMkdir(entryFile.getParentFile());
                    try (OutputStream out = new FileOutputStream(entryFile)) {
                        IOUtils.copy(in, out);
                    }

                }
            }
        }
    }
}
