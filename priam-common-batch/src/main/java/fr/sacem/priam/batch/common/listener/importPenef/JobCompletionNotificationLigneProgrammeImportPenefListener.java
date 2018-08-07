package fr.sacem.priam.batch.common.listener.importPenef;

import fr.sacem.priam.batch.common.dao.FichierRepository;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchServiceImpl;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import static fr.sacem.priam.batch.common.util.exception.PriamValidationException.ErrorType;


@Component
public class JobCompletionNotificationLigneProgrammeImportPenefListener extends JobExecutionListenerSupport {
    public static final String LIGNE_PROGRAMME_ERRORS = "ligne-programme-errors";
    public static final String MESSAGE_ERREUR_TECHNIQUE = "Le chargement a été interrompu à cause d'un problème technique";
    public static final String MESSAGE_FICHIER_CHARGE = " - Le fichier \"%s\" a bien été chargé";
    public static final String FORMAT_DATE = "dd/MM/yyyy HH:mm";
    public static final String MESSAGE_FORMAT_FICHIER = "Le fichier ne peut être chargé car il n'a pas le bon format";
    private static String NOM_FICHIER_CSV_EN_COURS = "nomFichier";
    private static String FILE_ERREUR = "erreur" ;
    private ExecutionContext executionContext;
    private FichierBatchServiceImpl fichierBatchService;
    private UtilFile utilFile;
    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationLigneProgrammeImportPenefListener.class);

    @Autowired
    FichierRepository fichierRepository;

    String typeFichier;

    @Autowired
    public JobCompletionNotificationLigneProgrammeImportPenefListener() {

        utilFile = new UtilFile();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

            Iterator it = stepExecutions.iterator();
            JobParameter idFichier = null;
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();

                if (executionContext != null) {
                    JobParameter parameterNomFichierCSV = (JobParameter) executionContext.get(NOM_FICHIER_CSV_EN_COURS);
                    Set<String> errors = (Set<String>) executionContext.get(LIGNE_PROGRAMME_ERRORS);
                    if(errors == null || errors.isEmpty()) {
                        if (parameterNomFichierCSV != null) {
                            idFichier = (JobParameter)executionContext.get("idFichier");
                            fichierBatchService.updateFichierById((Long)idFichier.getValue());

                            StringBuilder log = new StringBuilder();
                            log.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_DATE))).
                            append(String.format(MESSAGE_FICHIER_CHARGE, parameterNomFichierCSV));
                            fichierBatchService.creerlog((Long)idFichier.getValue(), log.toString());
                        } else {
                            LOG.debug("Pas de ficiher CSV traité ");
                        }
                    } else {
                        idFichier =(JobParameter) executionContext.get("idFichier");
                        fichierBatchService.rejeterFichier((Long)idFichier.getValue(), errors);
                    }
                } else {
                    LOG.debug("Pas de excution context pour le step en cours : " + myStepExecution.getStepName());
                }

                if(idFichier != null && ("CMS".equalsIgnoreCase(typeFichier) || "CP".equalsIgnoreCase(typeFichier))) {

                    fichierRepository.supprimerLigneProgrammeParIdFichier((Long)idFichier.getValue());
                }

            }

        } else {
            Iterator it = stepExecutions.iterator();
            JobParameter idFichier = null;

            Long idFile = null;
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();

                Set<String> errors = (Set<String>) executionContext.get("ligne-programme-errors");
                if(myStepExecution.getStatus() == BatchStatus.STOPPED){
                    if( ! errors.isEmpty()) {
                        idFichier = (JobParameter)executionContext.get("idFichier");
                        fichierBatchService.rejeterFichier((Long)idFichier.getValue(), errors);
                    }
                } else if("FAILED".equals(myStepExecution.getExitStatus().getExitCode()))
                {
                    System.out.println("--------------------------------");
                    Throwable exception = myStepExecution.getFailureExceptions().iterator().next();
                    if(exception instanceof PriamValidationException) {
                        ErrorType errorType = ((PriamValidationException) exception).getErrorType();

                        if(ErrorType.FORMAT_FICHIER.equals(errorType)) {
                            errors.add(MESSAGE_FORMAT_FICHIER);
                        }
                        else if(ErrorType.FORMAT_ATTRIBUT.equals(errorType)) {
                            errors.add(exception.getMessage());
                        }

                    } else if(errors.isEmpty()) {
                        errors.add(MESSAGE_ERREUR_TECHNIQUE);
                    }

                    JobParameter jobParameter = (JobParameter) executionContext.get("idFichier");

                    if(jobParameter == null) {
                        idFile = ((PriamValidationException)exception.getCause()).getIdFichier();
                        errors.clear();
                        errors.add(MESSAGE_FORMAT_FICHIER);
                    } else {
                        idFile = (Long) jobParameter.getValue();
                    }

                    fichierBatchService.rejeterFichier(idFile, errors);
                }
            }


            if(idFichier != null && ("CMS".equalsIgnoreCase(typeFichier) || "CP".equalsIgnoreCase(typeFichier))) {
                fichierRepository.supprimerLigneProgrammeParIdFichier(idFile);
            }
        }
        utilFile.deplacerFichierEtSuppressionFlag(jobExecution);
    }


    private void renommerFichierEnErreur(){

    }

    public void setFichierBatchService(FichierBatchServiceImpl fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }

    public String getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(String typeFichier) {
        this.typeFichier = typeFichier;
    }
}
