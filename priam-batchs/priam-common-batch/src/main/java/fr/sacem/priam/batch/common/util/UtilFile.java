package fr.sacem.priam.batch.common.util;

import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

/**
 * Created by fandis on 10/05/2017.
 */
public class UtilFile {
    private static final Logger LOG = LoggerFactory.getLogger(UtilFile.class);
    private static final String MON_FILTER = "TRACABILITE";
    private static final String EXTENTION_CSV = ".csv";
    private static final String EXTENTION_FLAG = ".flag";
    private static final String EXTENTION_ZIP = ".zip";
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
        zos.close();
        fis.close();
    }

    public void addToZipFileAndFlag(String fileName, String outputFile) throws IOException {

        try {
            String fileNameExtensionZip = getFileNameWithoutExtension(fileName) + EXTENTION_ZIP;
            Charset cs = Charset.forName("IBM437");
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outputFile + fileNameExtensionZip), cs);
            addToZipFile(outputFile + fileName, zos);

            addFlag(outputFile, fileNameExtensionZip);

            File file = new File(outputFile + fileName);
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFlag(String outputFile, String fileNameExtensionZip){

        String fileNameExtensionFlag = getFileNameWithoutExtension(fileNameExtensionZip) + EXTENTION_FLAG;

        try(OutputStream out = new FileOutputStream(new File(outputFile + File.separator + fileNameExtensionFlag)))
        {
            out.write((fileNameExtensionZip + "\n").getBytes());
        } catch (IOException e) {
            LOG.error("Erreur lors de la generation du fichier Flag !!! ", e);
        }

    }

    public String getFileNameWithoutExtension(String fileName){
        return fileName.substring(0, fileName.lastIndexOf('.'));
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
            deleteFile(nomFichierFlag);
        }
    }

    private void deleteFile(String nomFichierFlag) {
        File fichierFlag = new File(nomFichierFlag);
        if (fichierFlag.exists()) {
            fichierFlag.delete();
        } else {
            LOG.error("Le fichier Flag n'existe pas");
        }
    }

    public void suppressionFlagDemiInterface(String flagFilename){
        if(flagFilename != null){
            deleteFile(flagFilename);
        }
    }

    public void suppressionFlagDemiInterface(File fichierEnCoursDeTraitement){
        if(fichierEnCoursDeTraitement != null){
            String nomFichierFlag = FilenameUtils.removeExtension(fichierEnCoursDeTraitement.getAbsolutePath());
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

                    JobParameter parameterFichierZipEnCours = (JobParameter) executionContext.get(FICHIER_ZIP_EN_COURS);
                    JobParameter parameterNomFichierOriginal = (JobParameter) executionContext.get(NOM_ORIGINAL_FICHIER_ZIP);
                    JobParameter outputDirectory = jobExecution.getJobParameters().getParameters().get(REPERTOIRE_DE_DESTINATION);

                    deplacerFichier(parameterFichierZipEnCours, parameterNomFichierOriginal, outputDirectory);

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