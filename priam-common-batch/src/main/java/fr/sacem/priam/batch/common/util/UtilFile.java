package fr.sacem.priam.batch.common.util;

import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by fandis on 10/05/2017.
 */
public class UtilFile {
    private static final Logger LOG = LoggerFactory.getLogger(UtilFile.class);
    private static final String MON_FILTER = "TRACABILITE";
    private static final String EXTENTION_CSV = ".csv";
    private static final String EXTENTION_FLAG = ".flag";
//    private static String NOM_FICHIER_CSV_EN_COURS = "nomFichier";
    private static String FICHIER_ZIP_EN_COURS = "fichierZipEnCours";
    private static String NOM_ORIGINAL_FICHIER_ZIP = "nomFichierOriginal";
    private static String REPERTOIRE_DE_DESTINATION = "output.archives";
    private FichierBatchService fichierBatchService;


    public Long nombreDeLignes(InputStream inputStream) {
        String input ;
        Long count = 0L;
        try {
            InputStream is = inputStream;
            Reader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((input = bufferedReader.readLine()) != null) {
                if (!input.startsWith("#"))
                    count++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Count : " + count);
        return count;
    }





    /**
     * Extract only files from the zip archive.
     *
     * @param currentZipFile
     * @param extractedResources
     * @throws IOException
     */
    public Long extractFiles(final ZipFile currentZipFile, final List<Resource> extractedResources) throws IOException, PriamValidationException {
        Enumeration<? extends ZipEntry> zipEntryEnum = currentZipFile.entries();

        //Le code support que l'operation traite un seul fichier csv comme demander fonctionnellement
        String nomFichier = "";
        Long idFichier = null;
        while (zipEntryEnum.hasMoreElements()) {
            ZipEntry zipEntry = zipEntryEnum.nextElement();

            LOG.info("extracting:" + zipEntry.getName());
            // traverse directories
            if (!zipEntry.isDirectory() && !zipEntry.getName().contains(MON_FILTER) && zipEntry.getName().contains(EXTENTION_CSV)) {
                // add inputStream
                InputStreamResource inputStreamResourceForExtraction = new InputStreamResource(currentZipFile.getInputStream(zipEntry), zipEntry.getName());
                InputStream csvInputStream = currentZipFile.getInputStream(zipEntry);
                InputStreamResource inputStreamResourceForBDD = new InputStreamResource(csvInputStream,zipEntry.getName());
                idFichier = fichierBatchService.addFichier(inputStreamResourceForBDD.getInputStream(), zipEntry.getName());
                extractedResources.add(inputStreamResourceForExtraction);
                LOG.info("using extracted file:" + zipEntry.getName());
            }
        }
        return idFichier;
    }

    public void addToZipFile(String fileName, ZipOutputStream zos) throws IOException {

        System.out.println("Writing '" + fileName + "' to zip file");

        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        zos.closeEntry();
        fis.close();
    }
    public void deplacerFichier(JobParameter parameterFichierZipEnCours, JobParameter parameterNomFichierOriginal, JobParameter outputDirectory) {
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
    }

    public void suppressionFlag(File fichierEnCoursDeTraitement){
        if(fichierEnCoursDeTraitement != null){
            String nomFichierFlag = FilenameUtils.removeExtension(fichierEnCoursDeTraitement.getAbsolutePath()) + EXTENTION_FLAG;
//            String nomFichierFlag = FilenameUtils.removeExtension(parameterFichierZipEnCours) + EXTENTION_FLAG;
            File fichierFlag = new File(nomFichierFlag);
            if(fichierFlag.exists()){
                fichierFlag.delete();
            } else {
                LOG.error("Suppresion flag KO ");
            }
        }
    }

    public void deplacerFichierEtSuppressionFlag(JobExecution jobExecution){
        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
        ExecutionContext executionContext;
        if(!stepExecutions.isEmpty()){
            Iterator it = stepExecutions.iterator();
            while (it.hasNext()) {
                StepExecution myStepExecution = (StepExecution) it.next();
                executionContext = myStepExecution.getExecutionContext();
                if (executionContext != null) {
//                    JobParameter parameterNomFichierCSV = (JobParameter) executionContext.get(NOM_FICHIER_CSV_EN_COURS);
                    JobParameter parameterFichierZipEnCours = (JobParameter) executionContext.get(FICHIER_ZIP_EN_COURS);
                    JobParameter parameterNomFichierOriginal = (JobParameter) executionContext.get(NOM_ORIGINAL_FICHIER_ZIP);
                    JobParameter outputDirectory = jobExecution.getJobParameters().getParameters().get(REPERTOIRE_DE_DESTINATION);
                    /*deplacerFichier(parameterFichierZipEnCours, parameterNomFichierOriginal, outputDirectory);*/

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
                }
            }
        }
    }

    public UtilFile(FichierBatchService fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }

    public UtilFile() {
    }

    public FichierBatchService getFichierBatchService() {
        return fichierBatchService;
    }

    public void setFichierBatchService(FichierBatchService fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }

}