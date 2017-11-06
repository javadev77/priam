package fr.sacem.priam.model.services;

import fr.sacem.priam.model.dao.jpa.SareftrFamiltyputilDao;
import fr.sacem.priam.model.dao.jpa.cp.FichierCPDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.SareftrTyputilDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fandis on 26/07/2017.
 */
@Service
@Transactional(readOnly = true)
public class LigneProgrammeServiceImpl implements LigneProgrammeService {

    LigneProgrammeCPDao ligneProgrammeCPDao;
    FichierCPDao fichierCPDao;
    SareftrTyputilDao sareftrTyputilDao;
    SareftrFamiltyputilDao sareftrFamiltyputilDao;

    public LigneProgrammeCPDao getLigneProgrammeCPDao() {
        return ligneProgrammeCPDao;
    }

    public void setLigneProgrammeCPDao(LigneProgrammeCPDao ligneProgrammeCPDao) {
        this.ligneProgrammeCPDao = ligneProgrammeCPDao;
    }
    public FichierCPDao getFichierCPDao() {
        return fichierCPDao;
    }

    public void setFichierCPDao(FichierCPDao fichierCPDao) {
        this.fichierCPDao = fichierCPDao;
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
