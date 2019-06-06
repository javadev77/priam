package fr.sacem.priam.batch.repartition.service;

import fr.sacem.priam.batch.repartition.fv.dao.ReferentielJdbcDao;
import fr.sacem.priam.batch.repartition.fv.domain.ReferentielParticipation;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MajReferentielProcessor implements ItemProcessor<ReferentielParticipation, ReferentielParticipation> {

    @Autowired
    ReferentielJdbcDao referentielJdbcDao;

    @Override
    public ReferentielParticipation process(ReferentielParticipation referentielParticipation) throws Exception {

        List<ReferentielParticipation> listReferentiel =
                referentielJdbcDao.getReferentielByIde12AndCdeTypUtil(referentielParticipation.getIde12(),
                        referentielParticipation.getCdeTypUtil());

        if(listReferentiel.isEmpty()){
            referentielParticipation.setPresent(false);
        } else {
            referentielParticipation.setPresent(true);
            if(referentielParticipation.getRionPaiementMax() <= listReferentiel.stream().findFirst().get().getRionPaiementMax()){
                return null;
            }
        }

        return referentielParticipation;
    }
}
