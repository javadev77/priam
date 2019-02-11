package fr.sacem.priam.batch.fv.export.listener;

import fr.sacem.priam.batch.fv.export.dao.ExportProgrammeFVDao;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

@Component
public class JobExportFVListener extends JobExecutionListenerSupport {

    public static final String ERREUR_EXPORT = "ERREUR_EXPORT";
    public static final String EXPORT_GENERE = "GENERE";
    private static String NOM_EXPORT_CSV = "REQ_FILE_NAME";

    private ExecutionContext executionContext;

    @Autowired
    ExportProgrammeFVDao exportProgrammeFVDao;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
        String numProg = jobExecution.getJobParameters().getString("numProg");
        String path = jobExecution.getJobParameters().getString("priam.export.programme.fv");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Iterator it = stepExecutions.iterator();
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                if (executionContext != null) {
                    path = path + executionContext.getString(NOM_EXPORT_CSV);
                    if (path != null) {
                        exportProgrammeFVDao.insertStatutExportProgramme(numProg, path, EXPORT_GENERE);
                    }
                }
            }
        }
    }
}
