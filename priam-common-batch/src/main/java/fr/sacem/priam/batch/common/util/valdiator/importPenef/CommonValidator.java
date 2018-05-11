package fr.sacem.priam.batch.common.util.valdiator.importPenef;

import fr.sacem.priam.batch.common.domain.LigneProgramme;
import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.LongValidator;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import java.util.Locale;

/**
 * Created by benmerzoukah on 03/04/2018.
 */
public abstract class CommonValidator {

    protected void validateNumericFields(Errors errors, LigneProgramme o) {
        Long value = validateLongField(errors, o.getIde12(), "ide12");
        o.setIde12(String.valueOf(value));

        value = validateLongField(errors, o.getDurDif(), "durDif");
        o.setDurDif(String.valueOf(value));

        value = validateLongField(errors, o.getNbrDif(), "nbrDif");
        o.setNbrDif(String.valueOf(value));

        value = validateLongField(errors, o.getDurDifCtna(), "durDifCtna");
        o.setDurDifCtna(String.valueOf(value));


        Double tax = validateDoubleField(errors, o.getTax(), "tax");
        o.setTax(String.valueOf(tax));

        //Double mt = validateDoubleField(errors, String.o.getMt(), "mt");
    }

    protected Long validateLongField(Errors errors, String text, String field) {
        if (StringUtils.hasText(text)) {
            LongValidator longValidator = LongValidator.getInstance();
            Long value = longValidator.validate(text, Locale.FRANCE);
            if(value == null) {
                errors.rejectValue(field, "format.error."+field);
                value = 0L;
            }

            return value;
        }
        return 0L;
    }

    protected Double validateDoubleField(Errors errors, String text, String field) {
        if (StringUtils.hasText(text)) {
            DoubleValidator doubleValidator = DoubleValidator.getInstance();
            Double value = doubleValidator.validate(text, Locale.FRANCE);
            if(value == null) {
                errors.rejectValue(field, "format.error."+field);
                value = 0.0d;
            }

            return value;
        }
        return 0.0d;
    }
}
