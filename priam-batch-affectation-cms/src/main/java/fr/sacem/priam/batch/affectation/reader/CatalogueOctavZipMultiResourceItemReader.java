package fr.sacem.priam.batch.affectation.reader;

import fr.sacem.dao.TraitementCmsDao;
import fr.sacem.domain.Fichier;
import fr.sacem.priam.common.util.FileUtils;
import fr.sacem.service.importPenef.FichierBatchService;
import fr.sacem.util.UtilFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by benmerzoukah on 01/12/2017.
 */
public class CatalogueOctavZipMultiResourceItemReader<T> extends MultiResourceItemReader<T> {
    private static final Logger LOG = LoggerFactory.getLogger(CatalogueOctavZipMultiResourceItemReader.class);
    private static final String EXTENTION_ZIP = "^(.*\\.((zip|ZIP)$))?[^.]*$";
    public static final String MESSAGE_NOM_FICHIER_INCORRECTE = "Le fichier ne peut être chargé car son nom n'a pas le bon format";
    private Resource[] archives;
    private ZipFile[] zipFiles;
    private StepExecution stepExecution;
    @Autowired
    private UtilFile utilFile;
    private ZipFile zipFile;
    @Autowired
    private FichierBatchService fichierBatchService;
    private static String FILE_ZIP_EN_COURS_DE_TRAITEMENT = "_en_cours_de_traitement";

    @Autowired
    TraitementCmsDao traitementCmsDao;


    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

        JobParameters jobParameters = stepExecution.getJobParameters();
        JobParameter catalogueOactavDir =jobParameters.getParameters().get("input.catalog.octav");
        JobParameter catalogueOactavArchivesDir =jobParameters.getParameters().get("archives.catalog.octav");

        String inputDirectory = (String)catalogueOactavDir.getValue();
        String outputDirectory = (String)catalogueOactavArchivesDir.getValue();
        // really used with archives?
        if (inputDirectory != null && outputDirectory != null) {
            String rep = inputDirectory;
            LOG.debug("=== inputDirectory : "+ inputDirectory + "===");

            FileSystemResource repertoireBase = new FileSystemResource(rep);
            this.setArchives(new Resource[]{repertoireBase});
            if (archives != null) {
                // overwrite the comparator to use description
                // instead of filename, the itemStream can only
                // have that description
                this.setComparator(new Comparator<Resource>() {
                    /**
                     * Compares resource descriptions.
                     */
                    @Override
                    public int compare(Resource r1, Resource r2) {
                        return r1.getDescription().compareTo(r2.getDescription());
                    }
                });

                List<Resource> extractedResources = new ArrayList<Resource>();
                try {
                    // controle le nombre de repertoires passé, pour refuser le traitement des sous repertoires
                    if (archives.length >= 1) {
                        if (archives[0] != null) {
                            Integer nbrDeFichierDansLeRepertoire = archives[0].getFile().listFiles().length;
                            List<File> fichiersDansLeRepertoire = Arrays.asList(archives[0].getFile().listFiles());
                            List<File> fichiersZipDansLeRepertoire = new ArrayList<File>();
                            Integer nbrDeFichierZipATraiter=0;
                            for (int j = 0; j < nbrDeFichierDansLeRepertoire; j++) {
                                File file = fichiersDansLeRepertoire.get(j);
                                LOG.debug("=== fichiers Dans Le Repertoire : "+file.getName()+" ===");
                                //on traite qu'un seul fichier zip par lancement de batch
                                if (file.getName().matches(EXTENTION_ZIP) && nbrDeFichierZipATraiter < 1) {

                                    File fichierEnCoursDeTraitement = new File(rep + file.getName() + FILE_ZIP_EN_COURS_DE_TRAITEMENT);
                                    LOG.debug("=== renomer le fichier en : "+fichierEnCoursDeTraitement.getName()+" ===");
                                    JobParameter jobParameterFichierZipEnCours = new JobParameter(fichierEnCoursDeTraitement.getAbsolutePath());
                                    JobParameter jobParameterNomFichierOriginal = new JobParameter(file.getName());


                                    boolean renommageOk = file.renameTo(fichierEnCoursDeTraitement);
                                    if (renommageOk) {
                                        fichiersZipDansLeRepertoire.add(fichierEnCoursDeTraitement);
                                        this.stepExecution.getExecutionContext().put("nomFichierOriginal", jobParameterNomFichierOriginal);
                                        this.stepExecution.getExecutionContext().put("fichierZipEnCours", jobParameterFichierZipEnCours);
                                        this.stepExecution.getExecutionContext().put("outputArchives", outputDirectory);
                                        this.stepExecution.getExecutionContext().put("ligne-programme-errors", new HashSet<>());
                                    }

                                    nbrDeFichierZipATraiter = nbrDeFichierZipATraiter + 1;
                                }
                                //}
                            }
                            if (fichiersZipDansLeRepertoire.size() >= 1) {
                                //on traite qu'un seul fichier zip par operation, ce fichier zip va etre déplacer si le batch est complet
                                File file = fichiersZipDansLeRepertoire.get(0);

                                String fileName = file.getName();
                                if((fileName.startsWith(FileUtils.PREFIX_OCTAV_CATALOGUE_FR)) || (fileName.startsWith(FileUtils.PREFIX_OCTAV_CATALOGUE_FR,1))
                                        ||(fileName.startsWith(FileUtils.PREFIX_OCTAV_CATALOGUE_ANF)) || (fileName.startsWith(FileUtils.PREFIX_OCTAV_CATALOGUE_ANF,1))) {
                                    // vider la baser et lancer le chargement
                                    if((fileName.startsWith(FileUtils.PREFIX_OCTAV_CATALOGUE_FR))){
                                        traitementCmsDao.viderCatalogueOctav(FileUtils.CATALOGUE_OCTAV_TYPE_CMS_FR);
                                        //stepExecution.getJobExecution().getExecutionContext().put("TYPE_CMS", FileUtils.CATALOGUE_OCTAV_TYPE_CMS_FR);
                                    } else {
                                        traitementCmsDao.viderCatalogueOctav(FileUtils.CATALOGUE_OCTAV_TYPE_CMS_ANF);
                                        //stepExecution.getJobExecution().getExecutionContext().put("TYPE_CMS", FileUtils.CATALOGUE_OCTAV_TYPE_CMS_ANF);
                                    }

                                    Charset cs = Charset.forName("IBM437");
                                    zipFile = new ZipFile(file,cs);
                                    // find files inside the current zip resource
                                    //La fonction extractFiles traite le fichier csv et retourne son nom
                                    //Le nom du fichier est entregister dans le context du step pour pouvoir l'utiliser dans le itemWriter


                                    Long idFichier = utilFile.extractFiles(zipFile, extractedResources);
                                    Fichier fichier = fichierBatchService.findById(idFichier);

                                    JobParameter jobParameterNomDuFichier = new JobParameter(fichier.getNom());
                                    this.stepExecution.getExecutionContext().put("nomFichier", jobParameterNomDuFichier);

                                    JobParameter jobParameterIdFichier = new JobParameter(fichier.getId());
                                    this.stepExecution.getExecutionContext().put("idFichier", jobParameterIdFichier);

                                    // utilisation de offset a 1 est pour cause la creation des fichier dans les zip avec un / sous linux, c'est un hack pour les fichiers creer sous linux
                                }


                            }
                        }
                    }
                } catch (Exception ex) {
                    throw new ItemStreamException(ex);
                }

                // propagate extracted resources
                this.setResources(extractedResources.toArray(new Resource[extractedResources.size()]));
            }
            super.open(executionContext);
        } else {
            LOG.error("Les parametres output.archives et input.archives ne doit pas être nulls");
        }
    }
    public void setArchives(Resource[] archives) {
        this.archives = archives;
    }


    @Override
    public void close() throws ItemStreamException {
        super.close();
        // try to close all used zipfiles
        if (zipFiles != null) {
            for (int i = 0; i < zipFiles.length; i++) {
                try {
                    zipFiles[i].close();
                } catch (IOException ex) {
                    throw new ItemStreamException(ex);
                }
            }
        }
    }

    /**
     * Extract only files from the zip archive.
     *
     * @param currentZipFile
     * @param extractedResources
     * @throws IOException
     */
    private void extractFiles(final ZipFile currentZipFile, final List<Resource> extractedResources) throws IOException {
        Enumeration<? extends ZipEntry> zipEntryEnum = currentZipFile.entries();
        while (zipEntryEnum.hasMoreElements()) {
            ZipEntry zipEntry = zipEntryEnum.nextElement();
            LOG.info("extracting:" + zipEntry.getName());
            // traverse directories
            if (!zipEntry.isDirectory()) {
                // add inputStream
                extractedResources.add(
                        new InputStreamResource(
                                currentZipFile.getInputStream(zipEntry),
                                zipEntry.getName()));
                LOG.info("using extracted file:" + zipEntry.getName());
            }
        }
    }


    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
