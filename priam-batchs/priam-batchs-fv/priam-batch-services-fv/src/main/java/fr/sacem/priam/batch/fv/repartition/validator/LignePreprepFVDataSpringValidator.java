package fr.sacem.priam.batch.fv.repartition.validator;


import fr.sacem.priam.batch.fv.repartition.domain.LignePreprepFV;
import fr.sacem.priam.model.util.TypeUtilisationPriam;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LignePreprepFVDataSpringValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return LignePreprepFV.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        String cdeTypUtil = ((LignePreprepFV)o).getCdeTypUtil();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typRepart", "error.typRepart");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeCisac", "error.cdeCisac");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTer", "error.cdeTer");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rionEffet", "error.rionEffet");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtil", "error.cdeFamilTypUtil");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numProg", "error.numProg");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "error.id");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeUtil", "error.cdeUtil");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtil", "error.cdeTypUtil");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeModFac", "error.cdeModFac");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypProg", "error.cdeTypProg");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeCompl", "error.cdeCompl");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "libProg", "error.libProg");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "datDbtProg", "error.datDbtProg");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "datFinProg", "error.datFinProg");

        if(TypeUtilisationPriam.FD06.equals(cdeTypUtil) || TypeUtilisationPriam.FD12.equals(cdeTypUtil)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypIde12", "error.cdeTypIde12");
        }

        if(TypeUtilisationPriam.FD06.equals(cdeTypUtil)){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typMt", "error.typMt");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbrDif", "error.nbrDif");
        }

        if(TypeUtilisationPriam.FD12.equals(cdeTypUtil)){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mt", "error.mt");
        }

    }
}
