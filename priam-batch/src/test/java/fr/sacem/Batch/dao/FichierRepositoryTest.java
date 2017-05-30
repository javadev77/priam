package fr.sacem.Batch.dao;

import fr.sacem.dao.FichierRepositoryImpl;
import fr.sacem.domain.Fichier;
import fr.sacem.util.UtilFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by soufi on 28/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"batch-test"})
public class FichierRepositoryTest {

    private ApplicationContext context;

    private FichierRepositoryImpl fichierRepository;

    @Before
    public void setUp() {
        // Create the test configuration for the application from one file
        context = new ClassPathXmlApplicationContext(new String[]{"configuration/job-configuration.xml"});
        // Get the bean to use to invoke the application
        fichierRepository = (FichierRepositoryImpl) context.getBean("fichierRepository");
    }

    @Test
    public void addFichierTest() {
        Fichier fichier = new Fichier();
        fichier.setDateDebutChargt(UtilFile.getCurrentTimeStamp());
        fichier.setFamille("COPIEPRIV");
        fichier.setNom("MON_FICHIER1");
        fichier.setTypeUtilisation("CPRIVSONPH");
        fichier.setStatut("EN_COURS");
        fichier.setNbLignes(0l);
        fichierRepository.addFichier(fichier);
        Fichier fichier1 = fichierRepository.findByName("MON_FICHIER1");
        Assert.assertNotNull(fichier1);
        Assert.assertEquals("MON_FICHIER1", fichier1.getNom());
        Assert.assertEquals(fichier1.getFamille(), "COPIEPRIV");

    }

    @Test
    public void updateFichierDate() {
        Fichier fichier = new Fichier();
        fichier.setDateDebutChargt(UtilFile.getCurrentTimeStamp());
        fichier.setFamille("COPIEPRIV");
        fichier.setNom("MON_FICHIER2");
        fichier.setTypeUtilisation("CPRIVSONPH");
        fichier.setStatut("EN_COURS");
        fichier.setNbLignes(0l);
        fichierRepository.addFichier(fichier);
        fichierRepository.updateFichierDate("MON_FICHIER2");
        Fichier fichier1 = fichierRepository.findByName("MON_FICHIER2");
        Assert.assertNotNull(fichier1);
        Assert.assertNotNull(fichier1.getDateFinChargt());

    }

    @Test
    public void finByNameTest() {
        Fichier fichier1 = fichierRepository.findByName("Fichier 01");
        Assert.assertNotNull(fichier1);
    }
}
