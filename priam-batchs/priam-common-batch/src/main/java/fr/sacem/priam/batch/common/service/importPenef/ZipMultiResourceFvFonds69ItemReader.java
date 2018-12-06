package fr.sacem.priam.batch.common.service.importPenef;

import fr.sacem.priam.batch.common.domain.Fichier;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.common.util.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipFile;

/**
 * Created by embouazzar on 22/11/2018.
 */
public class ZipMultiResourceFvFonds69ItemReader <T> extends MultiResourceItemReader<T> {

    private static final Logger LOG = LoggerFactory.getLogger(ZipMultiResourceFvFonds69ItemReader.class);
    private static final String EXTENTION_ZIP = "^(.*\\.((zip|ZIP)$))?[^.]*$";
    private Resource[] archives;
    private ZipFile zipFile;

    private StepExecution stepExecution;
    private UtilFile utilFile;
    private static String FILE_ZIP_EN_COURS_DE_TRAITEMENT = "_en_cours_de_traitement";
    private static final String PATTERN_TYPE_FONDS_06 = "06";
    private static final String PATTERN_TYPE_FONDS_09 = "09";

    @Autowired
    private FichierBatchService fichierBatchService;

    private String typeFichier;

    private String patternFileName = null;
    private StringBuilder patternFileNameFonds6 = null;
    private StringBuilder patternFileNameFonds9 = null;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        JobParameters jobParameters = stepExecution.getJobParameters();
        JobParameter inputDirectoryParam =jobParameters.getParameters().get("input.archives");
        JobParameter outputDirectoryParam =jobParameters.getParameters().get("output.archives");
        JobParameter patternFileNameParam =jobParameters.getParameters().get("pattern.file.name");

        String inputDirectory = (String)inputDirectoryParam.getValue();
        String outputDirectory = (String)outputDirectoryParam.getValue();
        patternFileName = (String)patternFileNameParam.getValue();

        if(StringUtils.isNotEmpty(patternFileName)) {
            patternFileNameFonds6 = new StringBuilder(patternFileName).insert(27, PATTERN_TYPE_FONDS_06);
            patternFileNameFonds9 = new StringBuilder(patternFileName).insert(27, PATTERN_TYPE_FONDS_09);
        }

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
                                //if(fichiersDansLeRepertoire.get(0)!=null || !fichiersDansLeRepertoire.get(0).equals("")) {
                                File file = fichiersDansLeRepertoire.get(j);
                                LOG.debug("=== fichiers Dans Le Repertoire : "+file.getName()+" ===");
                                //on traite qu'un seul fichier zip par lancement de batch
                                /*if (file.getName().matches(EXTENTION_ZIP)
                                        && nbrDeFichierZipATraiter < 1
                                        && (file.getName().startsWith(FileUtils.PREFIX_PENEF_FV_FONDS06) || file.getName().startsWith(FileUtils.PREFIX_PENEF_FV_FONDS09))) {*/
                                if (file.getName().matches(EXTENTION_ZIP)
                                        && nbrDeFichierZipATraiter < 1
                                        && (FilenameUtils.removeExtension(file.getName()).matches(patternFileNameFonds6.toString()) ||
                                        FilenameUtils.removeExtension(file.getName()).matches(patternFileNameFonds9.toString()))) {

                                    File fichierEnCoursDeTraitement = new File(rep + file.getName() + FILE_ZIP_EN_COURS_DE_TRAITEMENT);
                                    utilFile.suppressionFlag(fichierEnCoursDeTraitement);
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
                                Charset cs = Charset.forName("IBM437");
                                zipFile = new ZipFile(file,cs);
                                // find files inside the current zip resource
                                //La fonction extractFiles traite le fichier csv et retourne son nom
                                //Le nom du fichier est entregister dans le context du step pour pouvoir l'utiliser dans le itemWriter
                                Long idFichier = utilFile.extractFiles(zipFile, extractedResources);
                                Fichier fichier = fichierBatchService.findById(idFichier);
                                String nomFichier = fichier.getNom();
                                JobParameter jobParameterNomDuFichier = new JobParameter(nomFichier);
                                this.stepExecution.getExecutionContext().put("nomFichier", jobParameterNomDuFichier);
                                JobParameter jobParameterIdFichier = new JobParameter(fichier.getId());
                                this.stepExecution.getExecutionContext().put("idFichier", jobParameterIdFichier);

                                this.stepExecution.getJobExecution().getExecutionContext().put("ID_FICHIER_GLOBAL", idFichier);
                                // utilisation de offset a 1 est pour cause la creation des fichier dans les zip avec un / sous linux, c'est un hack pour les fichiers creer sous linux

                            }
                        }
                    }
                } catch (Exception ex) {
                    throw new ItemStreamException(ex);
                }

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

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }


    public void setUtilFile(UtilFile utilFile) {
        this.utilFile = utilFile;
    }

    public String getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(String typeFichier) {
        this.typeFichier = typeFichier;
    }
}
