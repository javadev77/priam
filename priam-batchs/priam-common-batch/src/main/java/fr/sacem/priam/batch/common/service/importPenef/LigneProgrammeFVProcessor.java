package fr.sacem.priam.batch.common.service.importPenef;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.util.valdiator.importPenef.LigneProgrammeFVSpringValidator;
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
 * Created by embouazzar on 23/11/2018.
 */
public class LigneProgrammeFVProcessor implements ItemProcessor<LigneProgrammeFV, LigneProgrammeFV> {

    private static final Logger log = LoggerFactory.getLogger(LigneProgrammeFVProcessor.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String MESSAGE_CHAMPS_OBLIGATOIRE = "Ligne '%s': Le champ %s est obligatoire et non renseigné";
    public static final String LIGNE_PROGRAMME_ERRORS = "ligne-programme-errors";

    private ExecutionContext executionContext;

    @Autowired
    private LigneProgrammeFVSpringValidator validator;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.executionContext = stepExecution.getExecutionContext();
    }


    @Override
    public LigneProgrammeFV process(LigneProgrammeFV ligneProgrammeFV) throws Exception {

        Set<String> errorSet = (Set<String>) executionContext.get(LIGNE_PROGRAMME_ERRORS);

        JobParameter parameter_nom_fichier = (JobParameter) executionContext.get("nomFichier");
        JobParameter parameter_id_fichier=(JobParameter) executionContext.get("idFichier");
        Long idFichier = Long.valueOf(parameter_id_fichier.getValue().toString());
        String nomFichier=(String) parameter_nom_fichier.getValue();

        ligneProgrammeFV.setIdFichier(idFichier);
        log.info("Fichier : < " + nomFichier + " > Traitement de la ligne_programme id12 =(" + ligneProgrammeFV.getIde12() + ")");

        BindingResult errors = new BeanPropertyBindingResult(ligneProgrammeFV, "ligneProgramme-"+ ligneProgrammeFV.getLineNumber());
        validator.validate(ligneProgrammeFV, errors);

        if (!errors.hasErrors()) {
            return ligneProgrammeFV;
        }
        for(FieldError fe : errors.getFieldErrors()) {

            if (fe.getCode().startsWith("format.")) {
                errorSet.add(String.format(MESSAGE_FORMAT, ligneProgrammeFV.getLineNumber(), fe.getField(), fe.getRejectedValue()));
            } else {
                errorSet.add(String.format(MESSAGE_CHAMPS_OBLIGATOIRE, ligneProgrammeFV.getLineNumber(), fe.getField()));
            }

        }

        return ligneProgrammeFV;
    }
}
