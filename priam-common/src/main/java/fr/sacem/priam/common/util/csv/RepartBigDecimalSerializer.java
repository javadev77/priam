package fr.sacem.priam.common.util.csv;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by benmerzoukah on 18/08/2017.
 */
public class RepartBigDecimalSerializer  extends JsonSerializer<Number> {
    
    @Override
    public void serialize(Number value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
	  DecimalFormat decimalFormat = new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(Locale.FRANCE));
	  jgen.writeString(decimalFormat.format(value) + "00");
    }
}
