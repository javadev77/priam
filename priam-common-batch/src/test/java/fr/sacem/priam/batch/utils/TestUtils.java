package fr.sacem.priam.batch.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by benmerzoukah on 31/05/2018.
 */
public class TestUtils {

    public static void csvToZip(String outputFile, String inputFilePath, String csvFileName) {
        byte[] buffer = new byte[1024];
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze = new ZipEntry(csvFileName);
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(inputFilePath);

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
            zos.closeEntry();

            //remember close it
            zos.close();

        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
