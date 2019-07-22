package fr.sacem.priam.batch.fv.ad.info.rep.processor;

import fr.sacem.priam.batch.fv.ad.info.rep.domain.AyantDroitPers;
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
public class AdInfoRepValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return AyantDroitPers.class.equals(clazz);
    }

    @Override
    public void validate(final Object o, final Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "error.nom");
//        Donnée non obligatoire d'après FRIES car pour les Editeurs donnees non remplies
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prenom", "error.prenom");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "anneeNaissance", "error.anneeNaissance");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sousRole", "error.sousRole");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "indicSacem", "error.indicSacem");

    }
}
