package fr.sacem.priam.batch.catcms.rdo.util;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDatePropertyEditor extends PropertyEditorSupport {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public LocalDatePropertyEditor() {
    }

    @Override
    public void setAsText(String text) throws DateTimeParseException {
        if (StringUtils.hasText(text)) {

            setValue(LocalDate.parse(text, formatter));
        } else {
            setValue(null);
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        LocalDate value = (LocalDate) getValue();
        return (value != null ? value.toString() : "");
    }
}
