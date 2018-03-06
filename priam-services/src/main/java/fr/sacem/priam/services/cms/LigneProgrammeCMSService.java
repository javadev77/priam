package fr.sacem.priam.services.cms;

import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import fr.sacem.priam.services.journal.annotation.LogOeuvre;
import fr.sacem.priam.services.journal.annotation.TypeLog;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by benmerzoukah on 11/12/2017.
 */
public interface LigneProgrammeCMSService {
    @LogOeuvre(event = TypeLog.SUPPRESSIONOEUVRE)
    void deleteOeuvreManuel(LigneProgrammeCMS oeuvreManuelFound);

    Map<String,Object> calculerCompteurs(String numProg, String statut);

    @Transactional
    void ajouterOeuvreManuel(LigneProgrammeCMS input);

    LigneProgrammeCMS createOeuvreManuel(LigneProgrammeCMS input, Programme programme);

    @Transactional
    boolean isEligible(Long ide12, String typeCMS);
}
