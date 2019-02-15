package fr.sacem.priam.batch.fv.serviceimport.listener;

import fr.sacem.priam.model.dao.jpa.fv.ImportProgrammeFVDao;
import fr.sacem.priam.model.domain.StatutImportProgramme;
import fr.sacem.priam.model.domain.fv.ImportProgrammeFV;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobImportFVListener extends JobExecutionListenerSupport {

    @Autowired
    ImportProgrammeFVDao importProgrammeFVDao;

    @Override
    public void beforeJob(JobExecution jobExecution) {
/*
        String numProg = jobExecution.getJobParameters().getString("numProg");
        ImportProgrammeFV fichierImporte = importProgrammeFVDao.getFichierImporte(numProg);
        fichierImporte.setStatutImportProgramme(StatutImportProgramme.EN_COURS);
        importProgrammeFVDao.save(fichierImporte);
*/
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String numProg = jobExecution.getJobParameters().getString("numProg");
        ImportProgrammeFV fichierImporte = importProgrammeFVDao.getFichierImporte(numProg);
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            String isError = jobExecution.getExecutionContext().getString("IsError");
            if(Boolean.valueOf(isError)) {
                fichierImporte.setStatutImportProgramme(StatutImportProgramme.CHARGEMENT_KO);
            } else {
                fichierImporte.setStatutImportProgramme(StatutImportProgramme.CHARGEMENT_OK);
            }

            importProgrammeFVDao.save(fichierImporte);


        } else {
            fichierImporte.setStatutImportProgramme(StatutImportProgramme.CHARGEMENT_KO);
            importProgrammeFVDao.save(fichierImporte);
        }
    }
}
