package fr.sacem.priam.model.util;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import fr.sacem.priam.model.domain.TypeDroit;

import java.io.IOException;


public class TypeDroitSerializer extends StdSerializer<TypeDroit> {

    public TypeDroitSerializer() {
        this(null);
    }

    protected TypeDroitSerializer(Class<TypeDroit> t) {
        super(t);
    }

    @Override
    public void serialize(TypeDroit typeDroit, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(typeDroit.getValue());
    }
}
