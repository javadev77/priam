package fr.sacem.priam.batch.fv.serviceimport.listener;

import fr.sacem.priam.batch.common.dao.FichierRepository;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.dao.jpa.fv.ImportProgrammeFVDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutImportProgramme;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.fv.ImportProgrammeFV;
import fr.sacem.priam.services.FichierService;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobImportFVListener extends JobExecutionListenerSupport {

    private static final String MESSAGE_FORMAT_FICHIER = "Le fichier ne peut être chargé car il n'a pas le bon format";
    private static String MESSAGE_ERREUR_TECHNIQUE = "Erreur technique dans l'application priam" ;
    private static  final Logger LOGGER = LoggerFactory.getLogger(JobImportFVListener.class);

    @Autowired
    ImportProgrammeFVDao importProgrammeFVDao;

    @Autowired
    private FichierRepository fichierRepository;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    DataSource dataSource;

    @Autowired
    FichierService fichierService;

    @Override
    public void beforeJob(JobExecution jobExecution) {
/*
        String numProg = jobExecution.getJobParameters().getString("numProg");
        ImportProgrammeFV fichierImporte = importProgrammeFVDao.getFichierImporte(numProg);
        fichierImporte.setStatutImportProgramme(StatutImportProgramme.EN_COURS);
        importProgrammeFVDao.save(fichierImporte);
*/
        String numProg = jobExecution.getJobParameters().getString("numProg");
        Fichier fichierLink = fichierService.getOrCreateFichierLink(numProg);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("DELETE FROM PRIAM_IMPORT_PROGRAMME_FV_DATA_BATCH WHERE ID_FICHIER=?", fichierLink.getId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String numProg = jobExecution.getJobParameters().getString("numProg");
        ImportProgrammeFV fichierImporte = importProgrammeFVDao.getFichierImporte(numProg);
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            String isError = jobExecution.getExecutionContext().getString("IsError");
            if (Boolean.valueOf(isError)) {
                fichierImporte.setStatutImportProgramme(StatutImportProgramme.CHARGEMENT_KO);
            } else {
                fichierImporte.setStatutImportProgramme(StatutImportProgramme.CHARGEMENT_OK);

                Programme prog = programmeDao.findOne(numProg);
                prog.setStatut(StatutProgramme.EN_COURS);

                programmeDao.saveAndFlush(prog);
            }

            importProgrammeFVDao.save(fichierImporte);

        } else {

            Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
            Iterator it = stepExecutions.iterator();
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                final ExecutionContext executionContext = myStepExecution.getExecutionContext();

                Set<String> errors = (Set<String>) executionContext.get("errors");
                if (errors == null || errors.isEmpty()) {
                    errors = new HashSet<>();
                }
                if (myStepExecution.getStatus() == BatchStatus.STOPPED || "FAILED".equals(myStepExecution.getExitStatus().getExitCode())) {
                    Throwable exception = myStepExecution.getFailureExceptions().iterator().next();
                    Throwable cause = exception.getCause();
                    if (cause instanceof PriamValidationException) {
                        PriamValidationException.ErrorType errorType = ((PriamValidationException) cause).getErrorType();

                        if (PriamValidationException.ErrorType.FORMAT_FICHIER.equals(errorType)) {
                            errors.add(MESSAGE_FORMAT_FICHIER);
                        } else if (PriamValidationException.ErrorType.FORMAT_ATTRIBUT.equals(errorType)) {
                            errors.add(cause.getMessage());
                        }

                    } else if (errors.isEmpty()) {
                        errors = new HashSet<>();
                        errors.add(MESSAGE_ERREUR_TECHNIQUE);
                    }
                    LOGGER.info(errors.toString());
                    fichierRepository.enregistrerLog(fichierImporte.getId(), errors);
                }

            }


            fichierImporte.setStatutImportProgramme(StatutImportProgramme.CHARGEMENT_KO);
            importProgrammeFVDao.save(fichierImporte);

        }
    }
}
