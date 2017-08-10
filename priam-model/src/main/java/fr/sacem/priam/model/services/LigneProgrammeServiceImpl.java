package fr.sacem.priam.model.services;

import fr.sacem.priam.model.dao.jpa.FamilleDao;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.dao.jpa.TypeUtilisationDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fandis on 26/07/2017.
 */
@Service
@Transactional(readOnly = true)
public class LigneProgrammeServiceImpl implements LigneProgrammeService {

    LigneProgrammeDao ligneProgrammeDao;
    FichierDao fichierDao;
    TypeUtilisationDao typeUtilisationDao;
    FamilleDao familleDao;

    public LigneProgrammeDao getLigneProgrammeDao() {
        return ligneProgrammeDao;
    }

    public void setLigneProgrammeDao(LigneProgrammeDao ligneProgrammeDao) {
        this.ligneProgrammeDao = ligneProgrammeDao;
    }
    public FichierDao getFichierDao() {
        return fichierDao;
    }

    public void setFichierDao(FichierDao fichierDao) {
        this.fichierDao = fichierDao;
    }

    public TypeUtilisationDao getTypeUtilisationDao() {
        return typeUtilisationDao;
    }

    public void setTypeUtilisationDao(TypeUtilisationDao typeUtilisationDao) {
        this.typeUtilisationDao = typeUtilisationDao;
    }

    public FamilleDao getFamilleDao() {
        return familleDao;
    }

    public void setFamilleDao(FamilleDao familleDao) {
        this.familleDao = familleDao;
    }
}
