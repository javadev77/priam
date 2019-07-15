package fr.sacem.priam.batch.fv.octav.rep.processor;

import fr.sacem.priam.batch.common.domain.OctavCtnu;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * OctavCtnuRepValidator
 *
 * @author Habib BENMEZOUKA
 */
@Component
public class OctavCtnuRepValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return OctavCtnu.class.equals(clazz);
    }

    @Override
    public void validate(final Object o, final Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12Ctnu", "error.ide12Ctnu");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypIde12Ctnu", "error.cdeTypIde12Ctnu");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "indCmplxCtnu", "error.indCmplxCtnu");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "seqOeuvCtnu", "error.seqOeuvCtnu");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "seqOeuvPere", "error.seqOeuvPere");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durDsOeuvPere", "error.durDsOeuvPere");


    }
}
