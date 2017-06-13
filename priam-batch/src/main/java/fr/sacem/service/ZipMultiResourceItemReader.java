package fr.sacem.service;

import fr.sacem.domain.Fichier;
import fr.sacem.util.UtilFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipFile;

public class ZipMultiResourceItemReader<T> extends MultiResourceItemReader<T> {

    private static final Logger LOG = LoggerFactory.getLogger(ZipMultiResourceItemReader.class);
    private static final String EXTENTION_ZIP = "^(.*\\.((zip|ZIP)$))?[^.]*$";
    private Resource[] archives;
    private ZipFile zipFile;
    private StepExecution stepExecution;
    @Value("#{jobParameters['input.archives']}")
    private String inputDirectory = null;
    @Value("#{jobParameters['output.archives']}")
    private String outputDirectory = null;
    private UtilFile utilFile;
    @Autowired
    private FichierService fichierService;

    /**
     * Tries to extract all files in the archives and adds them as resources to
     * the normal MultiResourceItemReader. Overwrites the Comparator from
     * the super class to get it working with itemstreams.
     *
     * @param executionContext
     * @throws ItemStreamException
     */
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        // really used with archives?
        if (inputDirectory != null && outputDirectory != null) {
            String rep = this.getInputDirectory();
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
                            for (int j = 0; j < nbrDeFichierDansLeRepertoire; j++) {
                                File file = fichiersDansLeRepertoire.get(j);
                                if (file.getName().matches(EXTENTION_ZIP)) {
                                    File fichierEnCoursDeTraitement = new File(rep + file.getName() + "_en_cours_de_traiement");
                                    boolean renommageOk = file.renameTo(fichierEnCoursDeTraitement);
                                    if (renommageOk) {
                                        fichiersZipDansLeRepertoire.add(fichierEnCoursDeTraitement);
                                        JobParameter jobParameterFichierZipEnCours = new JobParameter(fichierEnCoursDeTraitement.getAbsolutePath());
                                        JobParameter jobParameterNomFichierOriginal = new JobParameter(file.getName());
                                        this.stepExecution.getExecutionContext().put("nomFichierOriginal", jobParameterNomFichierOriginal);
                                        this.stepExecution.getExecutionContext().put("fichierZipEnCours", jobParameterFichierZipEnCours);
                                        this.stepExecution.getExecutionContext().put("outputArchives", outputDirectory);
                                    }
                                }
                            }
                            if (fichiersZipDansLeRepertoire.size() >= 1) {
                                //on traite qu'un seul fichier zip par operation, ce fichier zip va etre déplacer si le batch est complet
                                File file = fichiersZipDansLeRepertoire.get(0);
                                zipFile = new ZipFile(file);
                                // find files inside the current zip resource
                                //La fonction extractFiles traite le fichier csv et retourne son nom
                                //Le nom du fichier est entregister dans le context du step pour pouvoir l'utiliser dans le itemWriter
                                String nomFichier = utilFile.extractFiles(zipFile, extractedResources);
                                Fichier fichier = fichierService.findByName(nomFichier);
                                JobParameter jobParameterNomDuFichier = new JobParameter(nomFichier);
                                this.stepExecution.getExecutionContext().put("nomFichier", jobParameterNomDuFichier);
                                JobParameter jobParameterIdFichier = new JobParameter(fichier.getId());
                                this.stepExecution.getExecutionContext().put("idFichier", jobParameterIdFichier);
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

    /**
     * Calls super.close() and tries to close all used zip files.
     *
     * @throws ItemStreamException
     */
    @Override
    public void close() throws ItemStreamException {
        super.close();
        // try to close all used zipfiles
        if (zipFile != null) {
            try {
                zipFile.close();
            } catch (IOException ex) {
                throw new ItemStreamException(ex);
            }

        }
    }

    /**
     * Set archive files with normal Spring resources pattern, if not set, the
     * class will fallback to normal MultiResourceItemReader behaviour.
     *
     * @param archives
     */
    public void setArchives(Resource[] archives) {
        this.archives = archives;
    }

    public Resource[] getArchives() {
        return archives;
    }

    public ZipFile getZipFile() {
        return zipFile;
    }

    public void setZipFiles(ZipFile[] zipFiles) {
        this.zipFile = zipFile;
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    public String getInputDirectory() {
        return inputDirectory;
    }

    public void setInputDirectory(String inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    public UtilFile getUtilFile() {
        return utilFile;
    }

    public void setUtilFile(UtilFile utilFile) {
        this.utilFile = utilFile;
    }
}