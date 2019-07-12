package fr.sacem.priam.batch.fv.adclesprotection.rep.processor;

import fr.sacem.priam.batch.common.fv.util.OctavDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * AdclesRepValidator
 *
 * @author Habib BENMEZOUKA
 */
@Component
public class AdclesRepValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return OctavDTO.class.equals(clazz);
    }

    @Override
    public void validate(final Object o, final Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12RepCoad", "error.ide12RepCoad");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypIde12RepCoad", "error.cdeTypIde12RepCoad");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rolad", "error.rolad");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "coad", "error.coad");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clead", "error.clead");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypProtec", "error.cdeTypProtec");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numPers", "error.numPers");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numCatal", "error.numCatal");

    }
}
