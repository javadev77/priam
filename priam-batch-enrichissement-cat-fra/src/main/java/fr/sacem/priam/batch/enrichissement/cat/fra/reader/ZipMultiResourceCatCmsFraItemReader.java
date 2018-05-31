package fr.sacem.priam.batch.enrichissement.cat.fra.reader;

import fr.sacem.priam.batch.common.domain.Fichier;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.common.util.FileUtils;
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
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipFile;

public class ZipMultiResourceCatCmsFraItemReader<T> extends MultiResourceItemReader<T> {
    private static final Logger LOG = LoggerFactory.getLogger(ZipMultiResourceCatCmsFraItemReader.class);
    private static final String EXTENTION_ZIP = "^(.*\\.((zip|ZIP)$))?[^.]*$";
    private Resource[] archives;
    private ZipFile zipFile;

    private StepExecution stepExecution;
    private UtilFile utilFile;
    private static String FILE_ZIP_EN_COURS_DE_TRAITEMENT = "_en_cours_de_traitement";
    @Autowired
    private FichierBatchService fichierBatchService;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        JobParameters jobParameters = stepExecution.getJobParameters();
        JobParameter inputDirectoryParam =jobParameters.getParameters().get("input.archives");
        JobParameter outputDirectoryParam =jobParameters.getParameters().get("output.archives");
        String inputDirectory = (String)inputDirectoryParam.getValue();
        String outputDirectory = (String)outputDirectoryParam.getValue();

        if (inputDirectory != null && outputDirectory != null) {
            String rep = inputDirectory;
            LOG.debug("=== inputDirectory : "+ inputDirectory + "===");

            FileSystemResource repertoireBase = new FileSystemResource(rep);
            this.setArchives(new Resource[]{repertoireBase});
            if (archives != null) {
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
                                if (file.getName().matches(EXTENTION_ZIP)
                                        && nbrDeFichierZipATraiter < 1
                                        && file.getName().startsWith(FileUtils.PREFIX_PENEF_CATALOGUE_FR)) {

                                    File fichierEnCoursDeTraitement = new File(rep + file.getName() + FILE_ZIP_EN_COURS_DE_TRAITEMENT);
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
                            }
                            if (fichiersZipDansLeRepertoire.size() >= 1) {
                                                                File file = fichiersZipDansLeRepertoire.get(0);
                                Charset cs = Charset.forName("IBM437");
                                zipFile = new ZipFile(file,cs);

                                Long idFichier = utilFile.extractFiles(zipFile, extractedResources);
                                Fichier fichier = fichierBatchService.findById(idFichier);
                                String nomFichier = fichier.getNom();
                                JobParameter jobParameterNomDuFichier = new JobParameter(nomFichier);
                                this.stepExecution.getExecutionContext().put("nomFichier", jobParameterNomDuFichier);
                                JobParameter jobParameterIdFichier = new JobParameter(fichier.getId());
                                this.stepExecution.getExecutionContext().put("idFichier", idFichier);
                                this.stepExecution.getJobExecution().getExecutionContext().put("ID_FICHIER", idFichier);

                            } else {

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

}
