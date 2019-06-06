package fr.sacem.priam.batch.fv.repartition.writer;

import fr.sacem.priam.batch.fv.repartition.dao.ReferentielJdbcDao;
import fr.sacem.priam.batch.fv.repartition.domain.ReferentielParticipation;
import org.springframework.batch.support.annotation.Classifier;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReferentielWriterRouteImpl {

    @Autowired
    ReferentielJdbcDao referentielJdbcDao;

    @Classifier
    public String classify(ReferentielParticipation referentielParticipation) {
        List<ReferentielParticipation> listReferentiel =
                referentielJdbcDao.getReferentielByIde12AndCdeTypUtil(referentielParticipation.getIde12(),
                        referentielParticipation.getCdeTypUtil());

        if(listReferentiel.isEmpty()){
            return "INSERT";
        } else {
            return "UPDATE";
        }
    }
}
