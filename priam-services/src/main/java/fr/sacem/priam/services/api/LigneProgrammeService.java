package fr.sacem.priam.services.api;

import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by benmerzoukah on 07/12/2017.
 */
public interface LigneProgrammeService {

    List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme);

    List<KeyValueDto> getTitresByProgramme(String titre, String programme);

    Page<? extends SelectionDto> findLigneProgrammeByCriteria(LigneProgrammeCriteria criteria, Pageable pageable);

    void selectLigneProgramme(String numProg, Set<Map<String, String>> idLingesProgrammes);

    void supprimerLigneProgramme(String numProg, Long ide12, SelectionDto selectedLigneProgramme);

    void deselectLigneProgramme(String numProg, Set<Map<String, String>> unselected);

    void enregistrerEdition(String numProg);

    @Transactional
    void enregistrerEdition(ValdierSelectionProgrammeInput input, UserDTO userDTO);

    void annulerEdition(String numProg, String utilisateur);

    void annulerSelection(String numProg, String utilisateur);

}
