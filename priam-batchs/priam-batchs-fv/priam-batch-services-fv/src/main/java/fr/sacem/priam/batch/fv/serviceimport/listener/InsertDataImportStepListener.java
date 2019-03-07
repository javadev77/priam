package fr.sacem.priam.batch.fv.serviceimport.listener;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.fv.AyantDroitFVDao;
import fr.sacem.priam.model.dao.jpa.fv.LigneProgrammeFVDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.services.FichierService;
import java.util.List;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class InsertDataImportStepListener extends StepExecutionListenerSupport {

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    @Autowired
    FichierService fichierService;

    @Autowired
    FichierDao fichierDao;

    @Autowired
    AyantDroitFVDao ayantDroitFVDao;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        //Supprimmer tous les données du programme
        String numProg = stepExecution.getJobExecution().getJobParameters().getString("numProg");

        List<Fichier> fichiersAffectes = fichierDao.findFichiersByIdProgramme(numProg, Status.AFFECTE);

        //fichierDao.clearSelectedFichiers(numProg, Status.CHARGEMENT_OK);
        ayantDroitFVDao.deleteByNumProg(numProg);
        fichiersAffectes.forEach(f ->  ligneProgrammeFVDao.deleteAllByFichierId(f.getId()));

        Fichier fichierLink = fichierDao.findFichierLink(numProg);
        ligneProgrammeFVDao.deleteAllByFichierId(fichierLink.getId());
    }
}
