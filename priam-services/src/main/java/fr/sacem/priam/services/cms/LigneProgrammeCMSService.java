package fr.sacem.priam.services.cms;

import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

/**
 * Created by benmerzoukah on 11/12/2017.
 */
public interface LigneProgrammeCMSService {
    void deleteOeuvreManuel(LigneProgrammeCMS oeuvreManuelFound);

    Map<String,Object> calculerCompteurs(String numProg, String statut);

    @Transactional
    void ajouterOeuvreManuel(LigneProgrammeCMS input);

    LigneProgrammeCMS createOeuvreManuel(LigneProgrammeCMS input, Programme programme);

    @Transactional
    boolean isEligible(Long ide12, String typeCMS);

    @Transactional
    void modifierPointsTemporaire(String numProg, Set<Map<String, String>> idLingesProgrammes, boolean isSelected);
}
