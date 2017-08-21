package fr.sacem.priam.common.util.csv;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zhe on 24/03/2015.
 */
public class DateSerializer extends JsonSerializer<Date>{

    private final SimpleDateFormat dateFormat ;

    public DateSerializer(String format) {
        dateFormat = new SimpleDateFormat(format) ;
    }

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        if(value == null)
            jgen.writeString("");
        else
            jgen.writeString(dateFormat.format(value));
    }
}
