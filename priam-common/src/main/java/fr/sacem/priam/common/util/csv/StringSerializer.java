package fr.sacem.priam.common.util.csv;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * Created by benmerzoukah from Homer Project on 24/03/2015.
 */
public class StringSerializer extends JsonSerializer<String>{
    @Override
    public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        if (!StringUtils.isEmpty(value)) {
            jgen.writeString("=\"" + value + "\"");
        }
    }
}
