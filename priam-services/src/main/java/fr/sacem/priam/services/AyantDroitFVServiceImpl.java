package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.fv.AyantDroitProgrammeFVDao;
import fr.sacem.priam.model.domain.criteria.AyantDroitCriteria;
import fr.sacem.priam.model.domain.dto.AyantDroitDto;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.services.fv.AyantDroitFVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ayantDroitFVService")
public class AyantDroitFVServiceImpl implements AyantDroitFVService {

    @Autowired
    AyantDroitProgrammeFVDao ayantDroitProgrammeFVDao;

    @Override
    public Page<? extends AyantDroitDto> findAyantDroitByCriteria(AyantDroitCriteria criteria, Pageable pageable){
        return ayantDroitProgrammeFVDao.findCoadByCriteria(criteria.getNumProg(),
                criteria.getIde12(),
                criteria.getTitre(),
                criteria.getCoad(),
                criteria.getParticipant(), pageable);
    }

    @Override
    public List<KeyValueDto> getListCoadByNumProg(Long coad, String programme) {
        return ayantDroitProgrammeFVDao.findCoadByNumProg(coad, programme);
    }

    @Override
    public List<KeyValueDto> getParticipantByNumProg(String query, String programme) {
        return ayantDroitProgrammeFVDao.findParticipantByNumProg(query, programme);
    }

}
