package fr.sacem.util;

import fr.sacem.util.valdiator.importPenef.LigneProgrammeCPSpringValidator;
import org.apache.commons.validator.routines.DoubleValidator;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by fandis on 09/05/2017.
 */
public class DoublePropertyEditor extends PropertyEditorSupport {

    public DoublePropertyEditor() {
    }

    @Override
    public void setAsText(String text) {
        if (StringUtils.hasText(text)) {
           /* Double value = DoubleValidator.getInstance().validate(text, Locale.FRANCE);
            if(value == null) {
                setValue(LigneProgrammeCPSpringValidator.ERROR_DECIMAL_BINDING + text);
            } else {
                setValue(value);
            }*/

            NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);
            try {
                setValue(nf.parse(text).doubleValue());

            } catch (ParseException e) {
                setValue(LigneProgrammeCPSpringValidator.ERROR_DECIMAL_BINDING + text);
            }
        } else {
            setValue(null);
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        Double value = (Double) getValue();
        return (value != null ? value.toString() : "");
    }

}
