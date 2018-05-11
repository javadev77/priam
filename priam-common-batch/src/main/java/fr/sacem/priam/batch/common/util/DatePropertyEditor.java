package fr.sacem.priam.batch.common.util;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by fandis on 09/05/2017.
 */
public class DatePropertyEditor extends PropertyEditorSupport {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public DatePropertyEditor() {
    }

    @Override
    public void setAsText(String text) throws DateTimeParseException {
        if (StringUtils.hasText(text)) {

            setValue(LocalDateTime.parse(text, formatter));
        } else {
            setValue(null);
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        LocalDateTime value = (LocalDateTime) getValue();
        return (value != null ? value.toString() : "");
    }
}
