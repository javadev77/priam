package fr.sacem.priam.model.services;

import fr.sacem.priam.model.dao.jpa.SareftrFamiltyputilDao;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.dao.jpa.SareftrTyputilDao;
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
    SareftrTyputilDao sareftrTyputilDao;
    SareftrFamiltyputilDao sareftrFamiltyputilDao;

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

    public SareftrTyputilDao getSareftrTyputilDao() {
        return sareftrTyputilDao;
    }

    public void setSareftrTyputilDao(SareftrTyputilDao sareftrTyputilDao) {
        this.sareftrTyputilDao = sareftrTyputilDao;
    }

    public SareftrFamiltyputilDao getSareftrFamiltyputilDao() {
        return sareftrFamiltyputilDao;
    }

    public void setSareftrFamiltyputilDao(SareftrFamiltyputilDao sareftrFamiltyputilDao) {
        this.sareftrFamiltyputilDao = sareftrFamiltyputilDao;
    }
}
