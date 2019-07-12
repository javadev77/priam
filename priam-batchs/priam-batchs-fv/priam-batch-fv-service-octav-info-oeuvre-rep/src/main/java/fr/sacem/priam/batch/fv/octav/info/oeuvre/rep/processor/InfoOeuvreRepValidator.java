package fr.sacem.priam.batch.fv.octav.info.oeuvre.rep.processor;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
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
public class InfoOeuvreRepValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return LigneProgrammeFV.class.equals(clazz);
    }

    @Override
    public void validate(final Object o, final Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titreOeuvre", "error.titreOeuvre");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "labelValo", "error.labelValo");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dureeDeposee", "error.dureeDeposee");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taxOri", "error.taxOri");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "indicRepart", "error.indicRepart");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "genreOeuvre", "error.genreOeuvre");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paysOri", "error.paysOri");

    }
}
