package fr.sacem.priam.services.cms;

import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by benmerzoukah on 11/12/2017.
 */
public interface LigneProgrammeCMSService {
    Map<String,Object> calculerCompteurs(String numProg, String statut);

    @Transactional
    void ajouterOeuvreManuel(LigneProgrammeCMS input);

    @Transactional
    boolean isEligible(Long ide12, String typeCMS);
}
