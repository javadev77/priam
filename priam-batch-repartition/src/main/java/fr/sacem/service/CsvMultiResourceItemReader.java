package fr.sacem.service;


import fr.sacem.priam.common.constants.EnvConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.*;

public class CsvMultiResourceItemReader<T> extends MultiResourceItemReader<T> {

    private static final Logger LOG = LoggerFactory.getLogger(CsvMultiResourceItemReader.class);
    private static final String EXTENTION_CSV = "^(.*\\.((csv|CSV)$))?[^.]*$";
    public static final String INPUT_OUTPUT_EMPTY_ERROR ="Les parametres output.archives et input.archives ne doit pas être nulls";
    public static final String MESSAGE_NOM_FICHIER_INCORRECTE ="Le nom du fichier d'acquitement en cours de traitement n'est pas correcte il doit contenir le mot Acquittement ";
    public static final String PREFIX_FILE_ACQUITEMENT ="Acquittement";
    private Resource[] fichiersCSV;
    private StepExecution stepExecution;
    
    private String inputDirectory = null;
    
    private String outputDirectory = null;
    
    private static String FILE_CSV_EN_COURS_DE_TRAITEMENT = "_en_cours_de_traitement";

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        inputDirectory = String.valueOf(EnvConstants.FELIX_ACQT_INPUT_DIR);
        outputDirectory = String.valueOf(EnvConstants.FELIX_ACQT_ARCHIVES_DIR);
        // really used with archives?
        if (inputDirectory != null && outputDirectory != null) {
            String rep = this.getInputDirectory();
            FileSystemResource repertoireBase = new FileSystemResource(rep);
            // resources must not be null in spring batch
            this.setResources(new Resource[]{repertoireBase});
            this.setFichiersCSV(new Resource[]{repertoireBase});
            if (fichiersCSV != null) {
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

                try {
                    // controle le nombre de repertoires passé, pour refuser le traitement des sous repertoires
                    if (fichiersCSV.length >= 1) {
                        if (fichiersCSV[0] != null) {
                            Integer nbrDeFichierDansLeRepertoire = fichiersCSV[0].getFile().listFiles().length;
                            List<File> fichiersDansLeRepertoire = Arrays.asList(fichiersCSV[0].getFile().listFiles());
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
                                        this.stepExecution.getExecutionContext().put("output.felix", outputDirectory);
                                        this.stepExecution.getExecutionContext().put("repartition-errors", new HashSet<>());
                                    }

                                    nbrDeFichierCSVATraiter = nbrDeFichierCSVATraiter + 1;
                                }
                            //}
                            }
                            if (fichiersCSVDansLeRepertoire.size() >= 1) {
                                //on traite qu'un seul fichier csv par operation, ce fichier csv va etre déplacer si le batch est ok
                                File file = fichiersCSVDansLeRepertoire.get(0);
                                if(!file.getName().contains(PREFIX_FILE_ACQUITEMENT)) {
                                    Set<String> errorSet = (Set<String>) executionContext.get("repartition-errors");
                                    LOG.error(MESSAGE_NOM_FICHIER_INCORRECTE);
                                    errorSet.add(MESSAGE_NOM_FICHIER_INCORRECTE);
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