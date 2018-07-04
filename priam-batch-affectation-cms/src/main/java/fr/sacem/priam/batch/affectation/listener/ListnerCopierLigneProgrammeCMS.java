package fr.sacem.priam.batch.affectation.listener;

import fr.sacem.dao.FichierRepository;
import fr.sacem.dao.TraitementCmsDao;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ListnerCopierLigneProgrammeCMS extends StepExecutionListenerSupport {

    @Autowired
    private FichierRepository fichierRepository;

    @Autowired
    private TraitementCmsDao traitementCmsDao;

    private String numProg = "";

    @Override
    public void beforeStep(StepExecution stepExecution) {

        numProg = stepExecution.getJobParameters().getString("numProg");
        Long idFichier = fichierRepository.addFichierLink(numProg);

        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        jobContext.put("ID_FICHIER_LINK", idFichier);
        stepExecution.setExecutionContext(jobContext);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        traitementCmsDao.viderCatalogueOctavAnt(numProg);

        return stepExecution.getExitStatus();
    }
}
