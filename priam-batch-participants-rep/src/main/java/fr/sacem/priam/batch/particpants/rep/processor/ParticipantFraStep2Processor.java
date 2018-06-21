package fr.sacem.priam.batch.particpants.rep.processor;

import fr.sacem.priam.batch.particpants.rep.domaine.Participant;
import org.springframework.batch.item.ItemProcessor;

public class ParticipantFraStep2Processor implements ItemProcessor<Participant, Participant> {
    @Override
    public Participant process(Participant participant) throws Exception {
        if(participant.getStatut()==0){
            return participant;
        }
        return null;
    }
}
