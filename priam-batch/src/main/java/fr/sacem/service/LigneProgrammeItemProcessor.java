package fr.sacem.service;

import fr.sacem.domain.Fichier;
import fr.sacem.domain.LigneProgramme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by fandis on 04/05/2017.
 */

public class LigneProgrammeItemProcessor implements ItemProcessor<LigneProgramme, LigneProgramme> {

    private static final Logger log = LoggerFactory.getLogger(LigneProgrammeItemProcessor.class);
    @Autowired
    private FichierService fichierService;
    private ExecutionContext executionContext;

    @Override
    public LigneProgramme process(final LigneProgramme ligneProgramme) throws Exception {
        JobParameter parameter = (JobParameter) executionContext.get("nomFichier");
        String nomFichier = (String) parameter.getValue();
        Fichier fichier = fichierService.findByName(nomFichier);
        ligneProgramme.setIdFichier(fichier.getId());
        log.info("Fichier : < " + nomFichier + " > Traitement de la ligne_programme id12 =(" + ligneProgramme.getIde12() + ")");

        return ligneProgramme;
    }


    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.executionContext = stepExecution.getExecutionContext();
    }

    public FichierService getFichierService() {
        return fichierService;
    }

    public void setFichierService(FichierService fichierService) {
        this.fichierService = fichierService;
    }
}