package fr.sacem.priam.batch.affectation.listener;

import fr.sacem.dao.FichierRepository;
import fr.sacem.dao.TraitementCmsDao;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
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
        super.beforeStep(stepExecution);

        numProg = stepExecution.getJobParameters().getString("numProg");
        Long idFichier = idFichier = fichierRepository.addFichierLink(numProg);

        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        jobContext.put("idFichier", new JobParameter(idFichier));
        stepExecution.setExecutionContext(jobContext);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        traitementCmsDao.viderCatalogueOctavAnt(numProg);
        return super.afterStep(stepExecution);
    }
}
