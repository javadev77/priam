package fr.sacem.priam.services.fv;

import fr.sacem.priam.model.domain.criteria.AyantDroitCriteria;
import fr.sacem.priam.model.domain.dto.AyantDroitDto;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AyantDroitFVService {
    Page<? extends AyantDroitDto> findAyantDroitByCriteria(AyantDroitCriteria criteria, Pageable pageable);

    List<KeyValueDto> getListCoadByNumProg(Long coad, String numProg);

    List<KeyValueDto> getParticipantByNumProg(String query, String programme);

    List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme);

    List<KeyValueDto> getTitresByProgramme(String query, String programme);

    Double calculerPointsByCriteria(AyantDroitCriteria criteria);

    Page<? extends AyantDroitDto> findByCumulCoad(AyantDroitCriteria criteria, Pageable pageable);

    Double calculerPointsByCumulCoad(AyantDroitCriteria criteria);
}
