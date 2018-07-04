package fr.sacem.priam.batch.participants.rep.processor;

import fr.sacem.priam.batch.participants.rep.domain.Participant;
import org.springframework.batch.item.ItemProcessor;

public class ParticipantFraStep3Processor implements ItemProcessor<Participant, Participant> {

    @Override
    public Participant process(Participant participant) throws Exception {
        return participant;
    }
}
