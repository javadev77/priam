package fr.sacem.priam.model.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import fr.sacem.priam.model.domain.TypeDroit;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class TypeDroitDeserializer extends JsonDeserializer {


    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        String stringValue = jsonParser.getValueAsString();

        if ( stringValue == null ) return null ;

        return TypeDroit.getEnumValue(stringValue);
    }


}
