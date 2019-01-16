package fr.sacem.priam.services.fv;


import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.fv.LigneProgrammeFV;
import fr.sacem.priam.security.model.UserDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

public interface LigneProgrammeFVService {
    void deleteOeuvreManuel(LigneProgrammeFV oeuvreManuelFound);

    Map<String,Object> calculerCompteurs(String numProg, String statut);

    @Transactional
    void ajouterOeuvreManuel(LigneProgrammeFV input, UserDTO userDTO);

    LigneProgrammeFV createOeuvreManuel(LigneProgrammeFV input, Programme programme);

    void modifierPointsTemporaire(String numProg, Set<Map<String, String>> unselected, Boolean aFalse, UserDTO userDTO);
}
