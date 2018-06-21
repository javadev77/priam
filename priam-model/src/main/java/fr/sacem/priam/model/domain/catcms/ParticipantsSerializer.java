package fr.sacem.priam.model.domain.catcms;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Created by benmerzoukah on 20/06/2018.
 */
public class ParticipantsSerializer extends StdSerializer<ParticipantsCatcms> {

    public ParticipantsSerializer() {
        this(null);
    }

    public ParticipantsSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(ParticipantsCatcms participantsCatcms, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {

        jgen.writeStartObject();

        jgen.writeStringField("role", participantsCatcms.getId().getRole());
        jgen.writeStringField("participant", participantsCatcms.getNomParticpant());


        jgen.writeEndObject();
    }
}
