package fr.sacem.priam.batch.fv.export.listener;

import fr.sacem.priam.batch.fv.journal.JournalUtil;
import static fr.sacem.priam.batch.fv.journal.TypeLog.EXPORT;
import fr.sacem.priam.model.dao.jpa.fv.ExportProgrammeFVDao;
import fr.sacem.priam.model.dao.jpa.fv.ImportProgrammeFVDao;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobExportFVListener extends JobExecutionListenerSupport {

    public static final String EXPORT_EN_GENERATION = "EN_GENERATION";
    public static final String EXPORT_GENERE = "GENERE";
    private static String NOM_EXPORT_CSV = "REQ_FILE_NAME";

    private ExecutionContext executionContext;

    @Autowired
    ExportProgrammeFVDao exportProgrammeFVDao;

    @Autowired
    ImportProgrammeFVDao importProgrammeFVDao;

    @Autowired
    JournalUtil journalUtil;

    /*@Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;*/


    @Override
    public void beforeJob(JobExecution jobExecution) {
        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
        String numProg = jobExecution.getJobParameters().getString("numProg");
        exportProgrammeFVDao.insertExportProgramme(numProg, EXPORT_EN_GENERATION);
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
                        //exportProgrammeFVDao.insertStatutExportProgramme(numProg, path, EXPORT_GENERE);
                        exportProgrammeFVDao.updateStatutExportProgramme(numProg, path, EXPORT_GENERE);

                        // delete the previous file
                        importProgrammeFVDao.deleteLogsByNumProg(numProg);
                        importProgrammeFVDao.deleteByNumProg(numProg);

                        String userId = jobExecution.getJobParameters().getString("userId");
                        journalUtil.saveJournal(userId, EXPORT.getEvenement(), numProg, null);

                    }
                }
            }
        }

        /*final ProgrammeDto payload = programmeViewDao.findByNumProg(numProg);
        simpMessagingTemplate.convertAndSend("/global-message/affectation", payload);*/
    }


}
