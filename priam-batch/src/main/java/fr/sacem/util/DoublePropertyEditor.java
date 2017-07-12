package fr.sacem.util;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by fandis on 09/05/2017.
 */
public class DoublePropertyEditor extends PropertyEditorSupport {
    public DoublePropertyEditor() {
    }

    @Override
    public void setAsText(String text) {
        if (StringUtils.hasText(text)) {
            NumberFormat nf = NumberFormat.getInstance();
            try {
                setValue(nf.parse(text).doubleValue());

            } catch (ParseException e) {
                System.out.println(e.getMessage());
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
