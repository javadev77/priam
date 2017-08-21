package fr.sacem.priam.common.util.csv;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Guillaume on 15/05/2015.
 */
public class DateDeserializer extends JsonDeserializer<Date> {

    private final SimpleDateFormat dateFormat ;

    public DateDeserializer(String format) {
        dateFormat = new SimpleDateFormat(format) ;
    }

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        String stringValue = jp.getValueAsString();

        if ( stringValue == null ) return null ;

        try {
            return dateFormat.parse(stringValue) ;
        } catch (ParseException e) {
            return null ;
        }
    }
}
