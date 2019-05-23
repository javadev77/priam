package fr.sacem.priam.batch.fv.participation.fonds.processor;

import fr.sacem.priam.batch.fv.participation.fonds.domain.ReferentielParticipationFonds;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ReferentielProcessor implements ItemProcessor<ReferentielParticipationFonds, ReferentielParticipationFonds> {

    public static final int IDE12_LENGTH_MAX = 12;
    public static final int IDE12_LENGTH_MIN = 6;

    private Set<ReferentielParticipationFonds> referentielParticipationFondsSet = new HashSet<>();

    @Override
    public ReferentielParticipationFonds process(ReferentielParticipationFonds referentielParticipationFonds) throws Exception {

        Optional<ReferentielParticipationFonds> optionalReferentiel = Optional.ofNullable(referentielParticipationFonds);
        if(optionalReferentiel.isPresent()
                && referentielParticipationFonds.getStatut() >= 0
                && !referentielParticipationFondsSet.contains(referentielParticipationFonds)){
                    if(checkIde12(String.valueOf(referentielParticipationFonds.getIde12()))){
                        referentielParticipationFondsSet.add(referentielParticipationFonds);
                        return referentielParticipationFonds;
                    }
        }
        return null;
    }

    private Boolean checkIde12(String ide12){
        if (ide12 != null && !ide12.isEmpty() && (ide12.length() < IDE12_LENGTH_MIN || ide12.length() > IDE12_LENGTH_MAX)) {
            return false;
        }
        return true;
    }
}
