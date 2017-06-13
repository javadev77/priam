package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 07/06/2017.
 */
@Component
public class ProgrammeService {
    
    @Autowired
    ProgrammeViewDao programmeViewDao;
    
    @Transactional
    public Page<ProgrammeDto> findProgrammeByCriteria(ProgrammeCriteria criteria, Pageable pageable) {
        
        Page<ProgrammeDto> pageProgramme = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(),
                criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getFamille(),
                criteria.getTypeUtilisation(), criteria.getRionTheorique(), criteria.getRionPaiement(), criteria.getTypeRepart(),
                pageable);
        
        return pageProgramme;
    }
}
