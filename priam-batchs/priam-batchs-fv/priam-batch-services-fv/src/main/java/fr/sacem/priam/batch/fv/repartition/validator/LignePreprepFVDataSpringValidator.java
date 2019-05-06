package fr.sacem.priam.batch.fv.repartition.validator;


import fr.sacem.priam.batch.fv.repartition.domain.LignePreprepFV;
import fr.sacem.priam.batch.fv.repartition.util.TypeRepartitionFVEnum;
import fr.sacem.priam.model.util.TypeUtilisationPriam;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static fr.sacem.priam.batch.fv.repartition.util.TypeRepartitionFVEnum.*;

@Component
public class LignePreprepFVDataSpringValidator implements Validator {

    public static final String CODE_TYPREPART_OEUVRE = "O";
    public static final String CODE_TYPREPART_AYANT_DROIT = "A";

    @Override
    public boolean supports(Class<?> aClass) {
        return LignePreprepFV.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        String cdeTypUtil = ((LignePreprepFV)o).getCdeTypUtil();
        String typRepart = ((LignePreprepFV)o).getTypRepart();
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

        /*if(TypeUtilisationPriam.FD06.getCode().equals(cdeTypUtil) || TypeUtilisationPriam.FD12.getCode().equals(cdeTypUtil)
            || TypeUtilisationPriam.FD01.getCode().equals(cdeTypUtil) || TypeUtilisationPriam.FD02.getCode().equals(cdeTypUtil)
                || TypeUtilisationPriam.FD05.getCode().equals(cdeTypUtil) || TypeUtilisationPriam.FD07.getCode().equals(cdeTypUtil)) {*/
        if(OEUVRE.getCode().equals(typRepart) || OEUVRE_AD.getCode().equals(typRepart)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypIde12", "error.cdeTypIde12");
        }

        if(TypeUtilisationPriam.FD06.getCode().equals(cdeTypUtil)){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typMt", "error.typMt");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mt", "error.mt");
        }


        if(TypeUtilisationPriam.FD12.getCode().equals(cdeTypUtil) || OEUVRE_AD.getCode().equals(typRepart)){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbrDif", "error.nbrDif");
        }

        /*if(!TypeUtilisationPriam.FD06.getCode().equals(cdeTypUtil) && !TypeUtilisationPriam.FD12.getCode().equals(cdeTypUtil)){*/
        if(AYANT_DROIT.getCode().equals(typRepart) || OEUVRE_AD.getCode().equals(typRepart)){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypDrtSacem", "error.cdeTypDrtSacem");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "coadPayer", "error.coadPayer");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idSteAd", "error.idSteAd");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rolAd", "error.rolAd");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numPers", "error.numPers");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numCatal", "error.numCatal");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "points", "error.points");
        }

        if (OEUVRE_AD.getCode().equals(typRepart)){

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "datSitu", "error.datSitu");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "datConslt", "error.datConslt");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durDif", "error.durDif");

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cleAd", "error.cleAd");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypProtec", "error.cdeTypProtec");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idSteAd", "error.idSteAd");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idSteOriEdtr", "error.idSteOriEdtr");
        }

    }
}
