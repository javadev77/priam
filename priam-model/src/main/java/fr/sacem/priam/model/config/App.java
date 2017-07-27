package fr.sacem.priam.model.config;

import fr.sacem.priam.model.dao.jpa.FamilleDao;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.dao.jpa.TypeUtilisationDao;
import fr.sacem.priam.model.services.LigneProgrammeServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class App {


    public static void main(String[] args) {
        ApplicationContext cxt = SpringApplication.run(App.class);
        LigneProgrammeDao ligneProgrammeDao = cxt.getBean(LigneProgrammeDao.class);
        FichierDao fichierDao = cxt.getBean(FichierDao.class);
        TypeUtilisationDao typeUtilisationDao=cxt.getBean(TypeUtilisationDao.class);
        FamilleDao familleDao=cxt.getBean(FamilleDao.class);
        LigneProgrammeServiceImpl ligneProgrammeServiceImpl = new LigneProgrammeServiceImpl();
        ligneProgrammeServiceImpl.setLigneProgrammeDao(ligneProgrammeDao);
        ligneProgrammeServiceImpl.setFichierDao(fichierDao);
        ligneProgrammeServiceImpl.setFamilleDao(familleDao);
        ligneProgrammeServiceImpl.setTypeUtilisationDao(typeUtilisationDao);
        ligneProgrammeServiceImpl.genererLigneProgramme("mon ficiher test 05");
    }
}