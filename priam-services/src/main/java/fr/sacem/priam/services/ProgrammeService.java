package fr.sacem.priam.services;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.jpa.ProgrammeDao;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by benmerzoukah on 07/06/2017.
 */
@Component
public class ProgrammeService {
    
    @Autowired
    ProgrammeDao programmeDao;
    
    public Page<ProgrammeDto> findProgrammeByCriteria(Pageable pageable) {
        Page<Object[]> pageProgramme = programmeDao.findAllProgrammeByCriteria(pageable);
        
        List<ProgrammeDto> programmeDtos = newArrayList();
    
        
        List<ProgrammeDto> dtos = Lists.newArrayList(Iterables.transform(pageProgramme, obj -> {
            Programme p = (Programme) obj[0];
            Long nbFichiersProg = (Long) obj[1];
    
            ProgrammeDto dto = new ProgrammeDto(p.getNumProg(), p.getNom(), p.getFamille().getCode(),
                    p.getTypeUtilisation().getCode(), p.getRionTheorique().getRion(),
                    p.getDateCreation(), p.getTypeRepart(), p.getStatut(),
                    p.getRionPaiement().getRion(), nbFichiersProg);
            
            return dto;
        }));
        
        return new PageImpl<>(dtos, pageable, pageProgramme.getTotalElements());
    }
}
