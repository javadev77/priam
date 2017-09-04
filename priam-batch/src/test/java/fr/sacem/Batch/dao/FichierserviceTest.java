package fr.sacem.Batch.dao;

import fr.sacem.domain.Fichier;
import fr.sacem.service.FichierServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by soufi on 28/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"batch-test"})
public class FichierserviceTest {
    private ApplicationContext context;
    private static final String SINGLE_FILE = "src/test/resources/zipDirectory/FF_PENEF_EXTRANA_EXTCPRIVSONORD_20170406140540.csv";
    private static final String NOM_FICHIER_CSV = "FF_PENEF_EXTRANA_EXTCPRIVSONORD_20170406140540.csv";
    private FichierServiceImpl fichierService;

    @Before
    public void setUp() {
        // Create the test configuration for the application from one file
        context = new ClassPathXmlApplicationContext(new String[]{"configuration/job-configuration.xml"});
        // Get the bean to use to invoke the application
        fichierService = (FichierServiceImpl) context.getBean("fichierService");
    }

    @Test
    public void addFichier() throws IOException {
        File file = new File(SINGLE_FILE);
        InputStream targetStream = new FileInputStream(file);
        fichierService.addFichier(targetStream, NOM_FICHIER_CSV);
        Fichier fichier = fichierService.findByName(NOM_FICHIER_CSV);
        Assert.assertNotNull(fichier);
        Assert.assertEquals(fichier.getNom(), NOM_FICHIER_CSV);
    }

    @Test
    public void updateFichiereDate() throws IOException {
        File file = new File(SINGLE_FILE);
        InputStream targetStream = new FileInputStream(file);
        fichierService.addFichier(targetStream, NOM_FICHIER_CSV);
        fichierService.updateFichierDate(NOM_FICHIER_CSV);
        Fichier fichier1 = fichierService.findByName(NOM_FICHIER_CSV);
        Assert.assertNotNull(fichier1);
        Assert.assertNotNull(fichier1.getDateFinChargt());
    }

    @Test
    public void finByNameTest() throws IOException {
        File file = new File(SINGLE_FILE);
        InputStream targetStream = new FileInputStream(file);
        fichierService.addFichier(targetStream, NOM_FICHIER_CSV);
        fichierService.updateFichierDate(NOM_FICHIER_CSV);
        Fichier fichier1 = fichierService.findByName(NOM_FICHIER_CSV);
        Assert.assertNotNull(fichier1);
        Assert.assertNotNull(fichier1);
    }
}
