package fr.sacem.priam.batch.fv.serviceimport.processor;

import static fr.sacem.priam.batch.common.fv.util.CategorieFondsEnum.CAT_04;
import static fr.sacem.priam.batch.common.fv.util.CategorieFondsEnum.getValue;
import fr.sacem.priam.batch.common.util.valdiator.importPenef.CommonValidator;
import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
@Component
public class ImportFvValidator extends CommonValidator implements Validator  {
    public static final int IDE12_LENGTH_MAX = 12;
    public static final int IDE12_LENGTH_MIN = 6;

    @Override
    public boolean supports(final Class<?> aClass) {
        return ExportCsvDto.class.equals(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        ExportCsvDto exportCsvDto = (ExportCsvDto) o;

        //Regle de validation des données pour les Fonds 01, 02, 05, 07

        //Programme
        rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtil", "error.cdeFamilTypUtil");
        rejectIfEmptyOrWhitespace(errors, "cdeTypUtil", "error.cdeTypUtil");
        rejectIfEmptyOrWhitespace(errors, "numProg", "error.numProg");
        //Oeuvre
        rejectIfEmptyOrWhitespace(errors, "rionEffet", "error.cdeTypIde12");

        String cdeTypUtil = exportCsvDto.getCdeTypUtil();
        if(cdeTypUtil != null && !cdeTypUtil.isEmpty() && CAT_04.equals(getValue(cdeTypUtil))) {
            //Oeuvre
            rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");

            String ide12 = exportCsvDto.getIde12();
            if (ide12 != null && !ide12.isEmpty() && (ide12.length() < IDE12_LENGTH_MIN || ide12.length() > IDE12_LENGTH_MAX)) {
                errors.rejectValue("ide12", "format.error.ide12");
            }
            rejectIfEmptyOrWhitespace(errors, "cdeTypIde12", "error.cdeTypIde12");

            //Oeuvre Ctnu
            rejectIfEmptyOrWhitespace(errors, "ide12RepCoad", "error.ide12RepCoad");
            rejectIfEmptyOrWhitespace(errors, "cdeTypIde12RepCoad", "error.cdeTypIde12RepCoad");
            rejectIfEmptyOrWhitespace(errors, "datsitu", "error.datsitu");
            rejectIfEmptyOrWhitespace(errors, "datconslt", "error.datconslt");

            //Ayant Droit
            rejectIfEmptyOrWhitespace(errors, "coad", "error.coad");
            rejectIfEmptyOrWhitespace(errors, "numPers", "error.numPers");
            rejectIfEmptyOrWhitespace(errors, "numCatal", "error.numCatal");
            rejectIfEmptyOrWhitespace(errors, "idSteAd", "error.idSteAd");
            rejectIfEmptyOrWhitespace(errors, "rolAd", "error.rolAd");
            rejectIfEmptyOrWhitespace(errors, "typeDroit", "error.typeDroit");
            rejectIfEmptyOrWhitespace(errors, "cleAd", "error.cleAd");
            rejectIfEmptyOrWhitespace(errors, "cdeTypProtect", "error.cdeTypProtect");
            rejectIfEmptyOrWhitespace(errors, "coadOriEdtr", "error.coadOriEdtr");
            rejectIfEmptyOrWhitespace(errors, "idSteOriEdtr", "error.idSteOriEdtr");
            rejectIfEmptyOrWhitespace(errors, "points", "error.points");

        }






    }
}
