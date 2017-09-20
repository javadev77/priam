package fr.sacem.service;

import fr.sacem.domain.Repartition;
import fr.sacem.util.valdiator.RepartitionSpringValidator;
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

public class RepartitionItemProcessor implements ItemProcessor<Repartition, Repartition> {

    private static final Logger log = LoggerFactory.getLogger(RepartitionItemProcessor.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String MESSAGE_CHAMPS_OBLIGATOIRE = "Ligne '%s': Le champ %s est obligatoire et non renseigné";
    public static final String REPARTITION_ERRORS = "repartition-errors";

    private ExecutionContext executionContext;

    @Autowired
    private RepartitionSpringValidator validator;

    @Override
    public Repartition process(final Repartition repartition) throws Exception {

        Set<String> errorSet = (Set<String>) executionContext.get(REPARTITION_ERRORS);

        BindingResult errors = new BeanPropertyBindingResult(repartition, "repartition-"+ repartition.getLineNumber());
        validator.validate(repartition, errors);
        //gestion des status de programme venant de FELIX
        if(repartition.getStatus()!=null && !repartition.getStatus().equals("")){
            if(repartition.getStatus().equalsIgnoreCase("OK")) {
                repartition.setStatus("REPARTI");
            }
            else if(repartition.getStatus().equalsIgnoreCase("KO")){
                repartition.setStatus("VALIDE");
            }
        }
        if(errors.hasErrors()) {
            for(FieldError fe : errors.getFieldErrors()) {

                if(fe.getCode().startsWith("format.")){
                    errorSet.add(String.format(MESSAGE_FORMAT, repartition.getLineNumber(), fe.getField(), fe.getRejectedValue()));
                } else {
                    errorSet.add(String.format(MESSAGE_CHAMPS_OBLIGATOIRE, repartition.getLineNumber(), fe.getField()));
                }

            }
        }

        return repartition;
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