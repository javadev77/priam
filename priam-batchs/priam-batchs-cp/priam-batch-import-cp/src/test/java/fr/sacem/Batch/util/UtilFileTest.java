package fr.sacem.Batch.util;

import fr.sacem.priam.batch.common.util.UtilFile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by soufi on 29/05/2017.
 */
@ActiveProfiles({"test"})
@RunWith(SpringJUnit4ClassRunner.class)

public class UtilFileTest {
    private static final String SINGLE_FILE = "src/test/resources/zipDirectory/FF_PENEF_EXTRANA_EXTCPRIVSONRD_20170406140540.csv";
    private static final String NOM_FICHIER_CSV = "FF_PENEF_EXTRANA_EXTCPRIVSONRD_20170406140540.csv";

    @Test
    public void nombreDeLignes() throws IOException {
        UtilFile utilFile = new UtilFile();
        File file = new File(SINGLE_FILE);
        InputStream targetStream = new FileInputStream(file);
        Long nombreDeLignes = utilFile.nombreDeLignes(targetStream);
        Assert.assertEquals(nombreDeLignes.longValue(), 10);
    }
}
