package fr.sacem.priam.common.util.csv;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by benmerzouka copied from homer project on 24/03/2015.
 */
public class NumberSerializer extends JsonSerializer<Number>{
    @Override
    public void serialize(Number value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        DecimalFormat decimalFormat = new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(Locale.FRANCE)) ;

        if(value instanceof Double || value instanceof Float){
            jgen.writeString(decimalFormat.format(value));
        }else if( value instanceof BigDecimal){
            jgen.writeString(decimalFormat.format(value) + "00");
        }else {
            jgen.writeNumber((Integer) value);
        }
    }
}
