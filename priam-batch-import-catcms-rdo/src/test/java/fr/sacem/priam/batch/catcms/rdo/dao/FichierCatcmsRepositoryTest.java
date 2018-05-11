package fr.sacem.priam.batch.catcms.rdo.dao;

import fr.sacem.priam.batch.common.domain.Fichier;
import fr.sacem.priam.batch.common.util.DateTimeUtils;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"test"})
public class FichierCatcmsRepositoryTest {

    private ApplicationContext context;

    private FichierCatcmsRepositoryImpl fichierCatcmsRepository;

    @Before
    public void setUp() {
        // Create the test configuration for the application from one file
        context = new ClassPathXmlApplicationContext(new String[]{"configuration/job-configuration.xml"});
        // Get the bean to use to invoke the application
        fichierCatcmsRepository = (FichierCatcmsRepositoryImpl) context.getBean("fichierRepository");
    }

    @Test
    public void addFichierTest() {
        Fichier fichier = new Fichier();
        fichier.setDateDebutChargt(new DateTimeUtils().getCurrentTimeStamp());
        fichier.setNom("FF_OCTAV_CATALOGUE");
        fichier.setStatut("EN_COURS");
        fichier.setNbLignes(0l);
        try {
            fichierCatcmsRepository.addFichier(fichier);
        } catch (PriamValidationException e) {
            e.printStackTrace();
        }
        Fichier fichier1 = fichierCatcmsRepository.findByName("FF_OCTAV_CATALOGUE");
        Assert.assertNotNull(fichier1);
        Assert.assertEquals("FF_OCTAV_CATALOGUE", fichier1.getNom());
    }

    @Test
    public void updateFichierByIdTest(){
        String nomFichier = "FF_OCTAV_CATALOGUE_ANF";
        Long idFichier = fichierCatcmsRepository.findByName(nomFichier).getId();
        fichierCatcmsRepository.updateFichierById(idFichier);
        Fichier fichierUpdate = fichierCatcmsRepository.findByName(nomFichier);
        Assert.assertNotNull(fichierUpdate.getDateFinChargt());
        String STATUT_OK = "CHARGEMENT_OK";
        Assert.assertEquals(STATUT_OK, fichierUpdate.getStatut());
    }

    @Test
    public void findByNameTest(){
        String nomFichier = "FF_OCTAV_CATALOGUE_FR";
        Fichier fichierUpdate = fichierCatcmsRepository.findByName(nomFichier);
        Assert.assertNotNull(fichierUpdate);
    }

    @Test
    public void findByIdTest(){
        Fichier fichier = fichierCatcmsRepository.findById(1L);
        Assert.assertNotNull(fichier);
    }

}
