package fr.sacem.priam.batch.enrichissement.cat.listener;

import com.google.common.base.Strings;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchServiceImpl;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.enrichissement.cat.dao.CatalogueCmsPenefRepository;
import fr.sacem.priam.batch.enrichissement.cat.dao.CatalogueCmsRepository;
import fr.sacem.priam.batch.enrichissement.cat.dao.StatistiquesCatcmsRepository;
import fr.sacem.priam.model.domain.catcms.StatistiqueCatcms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

public class JobCompletionNotificationCatPenefFraListener extends JobExecutionListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationCatPenefFraListener.class);
    private static String NOM_FICHIER_CSV_EN_COURS = "nomFichier";

    private FichierBatchServiceImpl fichierBatchService;

    private ExecutionContext executionContext;

    private UtilFile utilFile;

    @Autowired
    CatalogueCmsPenefRepository catalogueCmsPenefRepository;

    @Autowired
    CatalogueCmsRepository catalogueCmsRepository;

    @Autowired
    StatistiquesCatcmsRepository statistiquesCatcmsRepository;

    /*@Value("#{jobExecutionContext['nomFichier']}")
    public String nomFichier;*/

    @Autowired
    public JobCompletionNotificationCatPenefFraListener(UtilFile utilFile) {
        this.utilFile = utilFile;
    }



    @Override
    public void beforeJob(JobExecution jobExecution) {
        String typeCMS = jobExecution.getJobParameters().getString("typeCMS");
        String nbOeuvresAvantEnrichissement = String.valueOf(catalogueCmsRepository.nbTotalOeuvresCatalogueByTypeCMS(typeCMS));
        jobExecution.getExecutionContext().put("nbOeuvresAvantEnrichissement",nbOeuvresAvantEnrichissement);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
        Long fileID = null;
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Iterator it = stepExecutions.iterator();
            JobParameter idFichier = null;
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                if (executionContext != null) {
                    JobParameter parameterNomFichierCSV = (JobParameter) executionContext.get(NOM_FICHIER_CSV_EN_COURS);
                    if (parameterNomFichierCSV != null) {

                        fileID = executionContext.getLong("idFichier");
                        fichierBatchService.updateFichierById(fileID);
                    }
                } else {
                    LOG.debug("Pas de excution context pour le step en cours : " + myStepExecution.getStepName());
                }

            }
        } else {
            Iterator it = stepExecutions.iterator();
            JobParameter idFichier = null;

            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                fileID = executionContext.getLong("idFichier", 0L);

                fichierBatchService.rejeterFichier(fileID,   null);

            }
        }

        utilFile.deplacerFichierEtSuppressionFlag(jobExecution);
        catalogueCmsPenefRepository.supprimerDonneesCatPenefParIdFichier(fileID);
        renomerFichierNpu(jobExecution);

        if(jobExecution.getExecutionContext().containsKey("ID_FICHIER")) {
            StatistiqueCatcms statistiqueCatcms = getStatistiquesCatcms(jobExecution);
            statistiquesCatcmsRepository.saveStatistique(statistiqueCatcms);
        }
    }

    private void renomerFichierNpu(JobExecution jobExecution) {
        JobParameter fichierCSVEnCoursParam = (JobParameter)jobExecution.getExecutionContext().get("fichierCSVEnCours");
        JobParameter nomFichierOriginalParam = (JobParameter)jobExecution.getExecutionContext().get("nomFichierOriginal");
        String fichierCSVEnCours = (String) fichierCSVEnCoursParam.getValue();
        String nomFichierOriginal = (String) nomFichierOriginalParam.getValue();
        if(!Strings.isNullOrEmpty(fichierCSVEnCours) && !Strings.isNullOrEmpty(nomFichierOriginal)) {
            File file = new File(fichierCSVEnCours);
            String absolutePath = file.getAbsolutePath();
            String filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
            file.renameTo(new File(filePath + File.separator + nomFichierOriginal));
        }
    }

    private StatistiqueCatcms getStatistiquesCatcms(JobExecution jobExecution) {
        StatistiqueCatcms statistiqueCatcms = new StatistiqueCatcms();

        String typeCMS = jobExecution.getJobParameters().getString("typeCMS");
        statistiqueCatcms.setTypeCMS(typeCMS);

        /*String nomFichier = jobExecution.getExecutionContext().getString("nomFichier");
        statistiqueCatcms.setNomFichier(nomFichier);*/

        jobExecution.getStepExecutions().forEach(stepExecution -> {
            if(stepExecution.getStepName().equals("archiveFlatFileReaderStep")){
                JobParameter nomFichierParam = (JobParameter) stepExecution.getExecutionContext().get("nomFichier");
                statistiqueCatcms.setNomFichier((String) nomFichierParam.getValue());
            }
        });


        long idFichier= jobExecution.getExecutionContext().getLong("ID_FICHIER");
        Long nbOeuvres = catalogueCmsPenefRepository.nbLignesByIdFichier(idFichier);
        statistiqueCatcms.setNbOeuvres(nbOeuvres);

        Long nbRenouvellement = Long.valueOf(jobExecution.getExecutionContext().getString("nbRenouvellement"));
        statistiqueCatcms.setNbRenouvellement(nbRenouvellement);

        Long nbCreation = Long.valueOf(jobExecution.getExecutionContext().getString("nbCreationStepIDE12NotExist"));
        nbCreation = nbCreation + Long.valueOf(jobExecution.getExecutionContext().getString("nbCreationStepIDE12ExistEtDateSortieNonVide"));
        statistiqueCatcms.setNbCreation(nbCreation);


        Long nbOeuvresAvantEnrichissement = Long.valueOf(jobExecution.getExecutionContext().getString("nbOeuvresAvantEnrichissement"));
        statistiqueCatcms.setNbTotalOeuvres(nbOeuvresAvantEnrichissement + nbCreation);


        return statistiqueCatcms;
    }

    public FichierBatchServiceImpl getFichierBatchService() {
        return fichierBatchService;
    }

    public void setFichierBatchService(FichierBatchServiceImpl fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }

    public UtilFile getUtilFile() {
        return utilFile;
    }

    public void setUtilFile(UtilFile utilFile) {
        this.utilFile = utilFile;
    }
}
