package fr.sacem.service.importPenef;

import fr.sacem.domain.LigneProgramme;
import fr.sacem.util.valdiator.importPenef.LigneProgrammeSpringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Set;

/**
 * Created by fandis on 04/05/2017.
 */

public class LigneProgrammeItemProcessor implements ItemProcessor<LigneProgramme, LigneProgramme> {

    private static final Logger log = LoggerFactory.getLogger(LigneProgrammeItemProcessor.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String MESSAGE_CHAMPS_OBLIGATOIRE = "Ligne '%s': Le champ %s est obligatoire et non renseigné";
    public static final String LIGNE_PROGRAMME_ERRORS = "ligne-programme-errors";

    private ExecutionContext executionContext;

    @Autowired
    private LigneProgrammeSpringValidator validator;

    @Override
    public LigneProgramme process(final LigneProgramme ligneProgramme) throws Exception {

        Set<String> errorSet = (Set<String>) executionContext.get(LIGNE_PROGRAMME_ERRORS);

        JobParameter parameter_nom_fichier = (JobParameter) executionContext.get("nomFichier");
        JobParameter parameter_id_fichier=(JobParameter) executionContext.get("idFichier");
        Long idFichier = Long.valueOf(parameter_id_fichier.getValue().toString());
        String nomFichier=(String) parameter_nom_fichier.getValue();

        // recupération de Id fichier , Id fichier est obtenu apres la creation du fichier
        ligneProgramme.setIdFichier(idFichier);
        log.info("Fichier : < " + nomFichier + " > Traitement de la ligne_programme id12 =(" + ligneProgramme.getIde12() + ")");

        BindingResult errors = new BeanPropertyBindingResult(ligneProgramme, "ligneProgramme-"+ ligneProgramme.getLineNumber());
        validator.validate(ligneProgramme, errors);
    
        if (!errors.hasErrors()) {
            return ligneProgramme;
        }
        for(FieldError fe : errors.getFieldErrors()) {

		if(fe.getCode().startsWith("format.")){
		    errorSet.add(String.format(MESSAGE_FORMAT, ligneProgramme.getLineNumber(), fe.getField(), fe.getRejectedValue()));
		} else {
		    errorSet.add(String.format(MESSAGE_CHAMPS_OBLIGATOIRE, ligneProgramme.getLineNumber(), fe.getField()));
		}

	  }
    
        return ligneProgramme;
    }

    private String getValeurDuChamp(String message) {
        String text = message.split("'")[4];
        return text.substring(text.indexOf('[') + 1, text.indexOf(']') );
    }

    private String getNomDuChamp(String message) {

        return message.split("'")[3];

    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.executionContext = stepExecution.getExecutionContext();
    }

}