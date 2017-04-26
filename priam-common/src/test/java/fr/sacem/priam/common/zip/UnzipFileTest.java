package fr.sacem.priam.common.zip;

import fr.sacem.priam.common.exception.TechnicalException;
import org.apache.commons.compress.archivers.ArchiveException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 25/04/2017.
 */
public class UnzipFileTest {
    File outDir = new File("target/zip");

    @Before
    public  void setup() {
        if(!outDir.exists()) {
            outDir.mkdir();
        }
    }

    @After
    public void clean() {
        if(outDir != null) {
            outDir.delete();
        }
    }

    @Test
    public void testDecompress() throws IOException, ArchiveException, TechnicalException {
        File file = new File(getClass().getClassLoader().getResource("FF_PENEF_EXTRANA_EXTCATALOGUCMSFRA_20170405160757.zip").getFile());
        
        new UnzipFile(file).uncompress(outDir);

        assertThat(outDir).isDirectory();
        assertThat(outDir.listFiles()).isNotEmpty();

        for(File f : outDir.listFiles()) {
            assertThat(f.getName()).endsWith(".csv");
        }
    }

    @Test(expected = TechnicalException.class)
    public void test_null_zip_file() throws Exception, TechnicalException {
        new UnzipFile(null).uncompress(outDir);
    }

    @Test(expected = TechnicalException.class)
    public void test_null_or_not_exist_output_directory() throws Exception, TechnicalException {
        File file = new File(getClass().getClassLoader().getResource("FF_PENEF_EXTRANA_EXTCATALOGUCMSFRA_20170405160757.zip").getFile());
        new UnzipFile(file).uncompress(null);
    }

}