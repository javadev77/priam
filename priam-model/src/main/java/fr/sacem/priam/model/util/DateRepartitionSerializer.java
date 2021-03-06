package fr.sacem.priam.model.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by benmerzoukah on 09/05/2017.
 */
public class DateRepartitionSerializer extends StdSerializer<Date> {
  
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    public DateRepartitionSerializer() {
        this(null);
    }
    
    public DateRepartitionSerializer(Class t) {
        super(t);
    }
    
    @Override
    public void serialize (Date value, JsonGenerator gen, SerializerProvider arg2) throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }
}
