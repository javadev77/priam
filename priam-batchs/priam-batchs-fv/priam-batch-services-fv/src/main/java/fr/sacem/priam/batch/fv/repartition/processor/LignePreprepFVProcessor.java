package fr.sacem.priam.batch.fv.repartition.processor;

import fr.sacem.priam.batch.fv.repartition.domain.LignePreprepFV;
import fr.sacem.priam.batch.fv.repartition.validator.LignePreprepFVDataSpringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class LignePreprepFVProcessor implements ItemProcessor<LignePreprepFV, LignePreprepFV> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LignePreprepFVProcessor.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String MESSAGE_CHAMPS_OBLIGATOIRE = "Ligne '%s': Le champ %s est obligatoire et non renseigné";
    public static final String LIGNE_PREPREP_ERRORS = "ligne-preprep-errors";

    public static final String TYPE_REPART_OEUVRE = "O";
    public static final String CDE_MOD_FAC = "FORFAI";
    public static final String PRINC = "PRINC";
    public static final String SANS = "SANS";
    public static final String CDE_UTIL = "XXX";
    public static final Integer CDE_TER = 250;



    private ExecutionContext executionContext;

    public Integer lineNumber = 1;

    @Autowired
    LignePreprepFVDataSpringValidator validator;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.executionContext = stepExecution.getExecutionContext();

    }

    @Override
    public LignePreprepFV process(final LignePreprepFV lignePreprepFV) throws Exception {

        lignePreprepFV.setTypRepart(TYPE_REPART_OEUVRE);
        lignePreprepFV.setCdeModFac(CDE_MOD_FAC);
        lignePreprepFV.setCdeTypProg(PRINC);
        lignePreprepFV.setCdeCompl(SANS);
        lignePreprepFV.setCdeUtil(CDE_UTIL);
        lignePreprepFV.setCdeTer(CDE_TER);

        List<String> errorList = (ArrayList<String>) executionContext.get(LIGNE_PREPREP_ERRORS);
        BindingResult errors = new BeanPropertyBindingResult(lignePreprepFV, "lignePreprepFV-"+ lineNumber);
        validator.validate(lignePreprepFV, errors);

        if (!errors.hasErrors()) {
            return lignePreprepFV;
        }
        for(FieldError fe : errors.getFieldErrors()) {

            if (fe.getCode().startsWith("format.")) {
                errorList.add(String.format(MESSAGE_FORMAT, lineNumber, fe.getField(), fe.getRejectedValue()));
            } else {
                errorList.add(String.format(MESSAGE_CHAMPS_OBLIGATOIRE, lineNumber, fe.getField()));
            }

        }

        lineNumber++;

        return lignePreprepFV;
    }
}
