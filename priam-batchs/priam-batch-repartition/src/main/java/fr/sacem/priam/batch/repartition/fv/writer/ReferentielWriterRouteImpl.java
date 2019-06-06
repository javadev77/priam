package fr.sacem.priam.batch.repartition.fv.writer;

import fr.sacem.priam.batch.repartition.fv.domain.ReferentielParticipation;
import org.springframework.batch.support.annotation.Classifier;

public class ReferentielWriterRouteImpl {

    @Classifier
    public String classify(ReferentielParticipation referentielParticipation) {

        if(referentielParticipation.getPresent()){
            return "UPDATE";
        } else {
            return "INSERT";
        }
    }

}
