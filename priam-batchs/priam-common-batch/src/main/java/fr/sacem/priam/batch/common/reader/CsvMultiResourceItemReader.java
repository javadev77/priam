package fr.sacem.priam.batch.common.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class CsvMultiResourceItemReader<T> extends MultiResourceItemReader<T> {

    private static final Logger LOG = LoggerFactory.getLogger(CsvMultiResourceItemReader.class);
    private static final String EXTENTION_CSV = "^.*\\.(csv|CSV)$";
    public static final String INPUT_OUTPUT_EMPTY_ERROR ="Les parametres output.archives et input.archives ne doit pas être nulls";
    public static final String MESSAGE_NOM_FICHIER_INCORRECTE ="Le nom du fichier reponse en cours de traitement n'est pas correcte. Le nom doit être %s";
    private Resource[] fichiersCSV;
    private StepExecution stepExecution;

    private String inputDirectory = null;

    private String outputDirectory = null;

    private String patternFileName = null;

    private static String FILE_CSV_EN_COURS_DE_TRAITEMENT = "_en_cours_de_traitement";

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        JobParameters jobParameters = stepExecution.getJobParameters();
        JobParameter inputDirectoryParam =jobParameters.getParameters().get("input.archives");
        JobParameter outputDirectoryParam =jobParameters.getParameters().get("output.archives");
        JobParameter patternFileNameParam =jobParameters.getParameters().get("pattern.file.name");

        inputDirectory = (String)inputDirectoryParam.getValue();
        outputDirectory = (String)outputDirectoryParam.getValue();
        patternFileName = (String)patternFileNameParam.getValue();

        if(inputDirectory != null && outputDirectory != null) {
            String rep = this.getInputDirectory();
            FileSystemResource repertoireBase = new FileSystemResource(rep);

            this.setResources(new Resource[]{repertoireBase});
            this.setFichiersCSV(new Resource[]{repertoireBase});

            if (fichiersCSV != null) {

                this.setComparator(new Comparator<Resource>() {
                    /**
                     * Compares resource descriptions.
                     */
                    @Override
                    public int compare(Resource r1, Resource r2) {
                        return r1.getDescription().compareTo(r2.getDescription());
                    }
                });

                try {
                    // controle le nombre de repertoires passé, pour refuser le traitement des sous repertoires
                    if (fichiersCSV.length >= 1) {
                        if (fichiersCSV[0] != null && fichiersCSV[0].getFile().listFiles() != null) {
                            File[] files = fichiersCSV[0].getFile().listFiles();
                            Integer nbrDeFichierDansLeRepertoire = files.length;
                            List<File> fichiersDansLeRepertoire = Arrays.asList(files);
                            List<File> fichiersCSVDansLeRepertoire = new ArrayList<File>();
                            Integer nbrDeFichierCSVATraiter=0;
                            for (int j = 0; j < nbrDeFichierDansLeRepertoire; j++) {
                                //if(fichiersDansLeRepertoire.get(0)!=null || !fichiersDansLeRepertoire.get(0).equals("")) {
                                File file = fichiersDansLeRepertoire.get(j);
                                //on traite qu'un seul fichier zip par lancement de batch
                                if (file.getName().matches(EXTENTION_CSV) && nbrDeFichierCSVATraiter < 1) {

                                    File fichierEnCoursDeTraitement = new File(rep + file.getName() + FILE_CSV_EN_COURS_DE_TRAITEMENT);
                                    JobParameter jobParameterFichierCSVEnCours = new JobParameter(fichierEnCoursDeTraitement.getAbsolutePath());
                                    JobParameter jobParameterNomFichierOriginal = new JobParameter(file.getName());

                                    boolean renommageOk = file.renameTo(fichierEnCoursDeTraitement);
                                    if (renommageOk) {
                                        fichiersCSVDansLeRepertoire.add(fichierEnCoursDeTraitement);
                                        this.stepExecution.getExecutionContext().put("nomFichierOriginal", jobParameterNomFichierOriginal);
                                        this.stepExecution.getExecutionContext().put("fichierCSVEnCours", jobParameterFichierCSVEnCours);
                                        this.stepExecution.getExecutionContext().put("output.archives", outputDirectory);
                                        this.stepExecution.getExecutionContext().put("repartition-errors", new HashSet<>());
                                    }

                                    nbrDeFichierCSVATraiter = nbrDeFichierCSVATraiter + 1;
                                }
                                //}
                            }
                            if (fichiersCSVDansLeRepertoire.size() >= 1) {
                                //on traite qu'un seul fichier csv par operation, ce fichier csv va etre déplacer si le batch est ok
                                File file = fichiersCSVDansLeRepertoire.get(0);
                                String filenameCsv = FilenameUtils.getBaseName(file.getName());
                                LOG.info(String.format("filenameCsv : %s", filenameCsv));
                                if(!filenameCsv.matches(patternFileName)) {
                                    Set<String> errorSet = (Set<String>) executionContext.get("repartition-errors");
                                    LOG.error(String.format(MESSAGE_NOM_FICHIER_INCORRECTE, patternFileName));
                                    errorSet.add(String.format(MESSAGE_NOM_FICHIER_INCORRECTE, patternFileName));
                                    this.stepExecution.getJobExecution().stop();
                                }else{
                                    JobParameter jobParameterNomDuFichier = new JobParameter(file.getName());
                                    this.stepExecution.getExecutionContext().put("nomFichier", jobParameterNomDuFichier);
                                    FileSystemResource fichierResource = new FileSystemResource(file);
                                    this.setResources(new Resource[]{fichierResource});
                                }

                            }
                        }
                    }
                } catch (Exception ex) {
                    throw new ItemStreamException(ex);
                }

            }

            super.open(executionContext);
        } else {
            LOG.error(this.INPUT_OUTPUT_EMPTY_ERROR);
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
    }

    /**
     * Set fichiersCSV files with normal Spring resources pattern, if not set, the
     * class will fallback to normal MultiResourceItemReader behaviour.
     *
     * @param fichiersCSV
     */
    public void setFichiersCSV(Resource[] fichiersCSV) {
        this.fichiersCSV = fichiersCSV;
    }




    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    public String getInputDirectory() {
        return inputDirectory;
    }

}
