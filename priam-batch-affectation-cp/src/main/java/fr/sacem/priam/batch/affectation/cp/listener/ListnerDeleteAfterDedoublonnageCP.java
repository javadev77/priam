package fr.sacem.priam.batch.affectation.cp.listener;

import fr.sacem.dao.LigneProgrammeBatchDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.services.FichierService;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by fandis on 15/12/2017.
 */
public class ListnerDeleteAfterDedoublonnageCP extends StepExecutionListenerSupport {

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
        fichierService.majFichiersAffectesAuProgramme(numProg, listFichiersByIds, "GUEST");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        System.out.println("ListnerDeleteAfterDedoublonnageCP = " + stepExecution.getStepName());
        String numProg = stepExecution.getJobParameters().getString("numProg");
        this.ligneProgrammeBatchDao.deleteDedoublonnageCP(numProg);

        return stepExecution.getExitStatus();
    }
}
