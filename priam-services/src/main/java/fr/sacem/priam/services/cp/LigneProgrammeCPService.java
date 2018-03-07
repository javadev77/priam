package fr.sacem.priam.services.cp;

import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by benmerzoukah on 11/12/2017.
 */
public interface LigneProgrammeCPService {

    @Transactional
    void ajouterOeuvreManuel(LigneProgrammeCP input);

    Map<String, Long> getDurDifProgramme(String numProg, String statut);

    List<String> getUtilisateursByProgramme(String programme);


    @Transactional
    void modifierDurOrNbrDifTemporaire(String numProg, Set<Map<String, String>> idLingesProgrammes, boolean isSelected);
}
