package fr.sacem.priam.batch.fv.affectation.listener;

import fr.sacem.priam.batch.common.dao.AyantDroitDao;
import fr.sacem.priam.batch.common.dao.LigneProgrammeBatchDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
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

    @Autowired
    AyantDroitDao ayantDroitDao;

    @Autowired
    ProgrammeViewDao programmeViewDao;


    @Override
    public void beforeStep(StepExecution stepExecution) {
        String numProg = stepExecution.getJobParameters().getString("numProg");
        String[] fichiersAffectes = stepExecution.getJobParameters().getString("fichiersAffectes").split(",");
        List<Long> fichiersAffectesIds = Stream.of(fichiersAffectes).map(Long::parseLong).collect(Collectors.toList());
        List<Fichier> listFichiersByIds = fichierService.findListFichiersByIds(fichiersAffectesIds);
        String userId = stepExecution.getJobParameters().getString("userId");
        fichierService.majFichiersAffectesAuProgramme(numProg, listFichiersByIds, userId);

        /*ayantDroitDao.deleteDedoublonnageTableAD(numProg);*/
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        String numProg = stepExecution.getJobParameters().getString("numProg");
        ProgrammeDto programmeDto = programmeViewDao.findByNumProg(numProg);
        if(programmeDto!=null && "OEUVRE".equals(programmeDto.getTypeRepart().name())) {
            this.ligneProgrammeBatchDao.deleteDedoublonnageFVOeuvre(numProg);
        } else {
            ayantDroitDao.deleteDedoublonnageTableAD(numProg);
            /*this.ligneProgrammeBatchDao.deleteDedoublonnageFVAD(numProg);*/
            this.ligneProgrammeBatchDao.deleteDedoublonnageFVOeuvre(numProg);
        }
        return stepExecution.getExitStatus();
    }
}
