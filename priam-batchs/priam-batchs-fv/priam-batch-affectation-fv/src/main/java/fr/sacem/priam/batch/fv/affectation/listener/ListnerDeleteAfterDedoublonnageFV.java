package fr.sacem.priam.batch.fv.affectation.listener;

import fr.sacem.priam.batch.common.dao.LigneProgrammeBatchDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.services.FichierService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by fandis on 15/12/2017.
 */
public class ListnerDeleteAfterDedoublonnageFV extends StepExecutionListenerSupport {

    @Autowired
    LigneProgrammeBatchDao ligneProgrammeBatchDao;

    @Autowired
    FichierService fichierService;


    @Override
    public void beforeStep(StepExecution stepExecution) {
        String numProg = stepExecution.getJobParameters().getString("numProg");
        String[] fichiersAffectes = stepExecution.getJobParameters().getString("fichiersAffectes").split(",");
        List<Long> fichiersAffectesIds = Stream.of(fichiersAffectes).map(Long::parseLong).collect(Collectors.toList());
        List<Fichier> listFichiersByIds = fichierService.findListFichiersByIds(fichiersAffectesIds);
        String userId = stepExecution.getJobParameters().getString("userId");
        fichierService.majFichiersAffectesAuProgramme(numProg, listFichiersByIds, userId);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        String numProg = stepExecution.getJobParameters().getString("numProg");
        this.ligneProgrammeBatchDao.deleteDedoublonnageFV(numProg);

        return stepExecution.getExitStatus();
    }
}
