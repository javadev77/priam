package fr.sacem.priam.services;

import fr.sacem.priam.model.domain.criteria.AyantDroitCriteria;
import fr.sacem.priam.model.domain.dto.AyantDroitDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AyantDroitService {

    Page<? extends AyantDroitDto> findAyantDroitByCriteria(AyantDroitCriteria criteria, Pageable pageable){
        return null;
    }

}
