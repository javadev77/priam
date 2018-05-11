package fr.sacem.priam.batch.catcms.rdo.util.validator;

import fr.sacem.priam.batch.catcms.rdo.domain.CatalogueRdoCsv;
import fr.sacem.priam.batch.common.util.valdiator.importPenef.CommonValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CatalogueRdoValidator extends CommonValidator implements Validator {

    public static final int IDE12_LENGTH_MAX = 12;
    public static final int IDE12_LENGTH_MIN = 6;
    public static final String FORMAT_DATE_VALID = "yyyy-MM-dd";

    @Override
    public boolean supports(Class<?> aClass) {
        return CatalogueRdoCsv.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");
        String ide12 = String.valueOf(((CatalogueRdoCsv) o).getIde12());
        String dateCatal = String.valueOf(((CatalogueRdoCsv) o).getDateCatal());

        if(ide12 != null && !ide12.isEmpty() && (ide12.length() < IDE12_LENGTH_MIN || ide12.length() > IDE12_LENGTH_MAX)) {
            errors.rejectValue("ide12", "format.error.ide12");
        }

        /*if(dateCatal != null && !dateCatal.isEmpty() && !isValidFormat(FORMAT_DATE_VALID, dateCatal)){
            errors.rejectValue("dateCatal", "format.error.dateCatal");
        }*/
    }

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }
}
