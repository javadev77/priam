package fr.sacem.priam.common.util.csv;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;


public class DoubleDeserializer extends JsonDeserializer<Double> {

    public DoubleDeserializer() {
    }

    @Override
    public Double deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

        String value = jp.getValueAsString();

        if ( value == null ) return null ;

        try {
            DecimalFormat decimalFormat = new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(Locale.FRANCE)) ;
            Number n = decimalFormat.parse(value) ;
            return n.doubleValue();

        } catch (ParseException e) {
            return null ;
        }
    }

}
