package fr.sacem.config;

import fr.sacem.service.FichierServiceImpl;
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

import java.io.File;
import java.util.Collection;
import java.util.Iterator;


@Component
public class JobCompletionNotificationLigneProgrammeListener extends JobExecutionListenerSupport {
    private static String NOM_FICHIER_CSV_EN_COURS = "nomFichier";
    private static String FICHIER_ZIP_EN_COURS = "fichierZipEnCours";
    private static String NOM_ORIGINAL_FICHIER_ZIP = "nomFichierOriginal";
    private static String REPERTOIRE_DE_DESTINATION = "output.archives";
    private ExecutionContext executionContext;
    private FichierServiceImpl fichierService;
    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationLigneProgrammeListener.class);

    @Autowired
    public JobCompletionNotificationLigneProgrammeListener() {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
            Iterator it = stepExecutions.iterator();
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                if (executionContext != null) {
                    JobParameter parameterNomFichierCSV = (JobParameter) executionContext.get(NOM_FICHIER_CSV_EN_COURS);
                    JobParameter parameterFichierZipEnCours = (JobParameter) executionContext.get(FICHIER_ZIP_EN_COURS);
                    JobParameter parameterNomFichierOriginal = (JobParameter) executionContext.get(NOM_ORIGINAL_FICHIER_ZIP);
                    JobParameter outputDirectory = jobExecution.getJobParameters().getParameters().get(REPERTOIRE_DE_DESTINATION);

                    if (parameterNomFichierCSV != null) {
                        String nomFichier = (String) parameterNomFichierCSV.getValue();
                        fichierService.updateFichierDate(nomFichier);
                    } else {
                        LOG.debug("Pas de ficiher CSV traité ");
                    }
                    if (parameterNomFichierOriginal != null && parameterFichierZipEnCours != null && outputDirectory != null) {
                        String nomFichierTraiementEnCours = (String) parameterFichierZipEnCours.getValue();
                        File fichierTraitementEnCours = new File(nomFichierTraiementEnCours);
                        File fichierTraitementOk = new File((String) outputDirectory.getValue() + (String) parameterNomFichierOriginal.getValue());
                        if (fichierTraitementOk.exists())
                            fichierTraitementOk.delete();
                        Boolean deplacementOK = fichierTraitementEnCours.renameTo(fichierTraitementOk);
                        if (deplacementOK) {
                            fichierTraitementEnCours.delete();
                        } else {
                            LOG.error("Déplacement de ficiher en cours de traitement KO ");
                        }
                    }

                } else {
                    LOG.debug("Pas de excution context pour le step en cours : " + myStepExecution.getStepName());
                }
            }
        }

    }

    public FichierServiceImpl getFichierService() {
        return fichierService;
    }

    public void setFichierService(FichierServiceImpl fichierService) {
        this.fichierService = fichierService;
    }
}
