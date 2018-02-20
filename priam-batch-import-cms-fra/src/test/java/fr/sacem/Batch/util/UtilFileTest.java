package fr.sacem.Batch.util;

import fr.sacem.domain.Fichier;
import fr.sacem.util.UtilFile;
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
    private static final String SINGLE_FILE = "src/test/resources/zipDirectory/FF_PENEF_EXTRANA_EXTUCSONANT_RION-4_20171016172353.csv";
    private static final String NOM_FICHIER_CSV = "FF_PENEF_EXTRANA_EXTUCSONANT_RION-4_20171016172353.csv";

    @Test
    public void nombreDeLignes() throws IOException {
        UtilFile utilFile = new UtilFile();
        File file = new File(SINGLE_FILE);
        InputStream targetStream = new FileInputStream(file);
        Long nombreDeLignes = utilFile.nombreDeLignes(targetStream);
        Assert.assertEquals(nombreDeLignes.longValue(), 2558);
    }

    @Test
    public void chargerLesDonneesTest() throws IOException {
        UtilFile utilFile = new UtilFile();
        File file = new File(SINGLE_FILE);
        InputStream targetStream = new FileInputStream(file);
        Fichier fichier = null;//utilFile.chargerLesDonnees(targetStream, NOM_FICHIER_CSV);
        Assert.assertEquals(fichier.getNom(), NOM_FICHIER_CSV);
        Assert.assertEquals(fichier.getFamille(), "UC");
        Assert.assertEquals(fichier.getTypeUtilisation(), "SONOFRA");
        Assert.assertNotNull(fichier.getDateDebutChargt());

    }
}
