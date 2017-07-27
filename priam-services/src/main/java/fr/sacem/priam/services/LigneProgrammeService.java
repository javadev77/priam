package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.domain.LigneProgramme;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.Ide12Dto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jbelwidane on 25/07/2017.
 */
@Component
public class LigneProgrammeService {
	
	@Autowired
    private LigneProgrammeDao ligneProgrammeDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(LigneProgrammeService.class);

	@Transactional
    public List<Ide12Dto> getListIDE12ByProgramme(Long ide12, String programme) {
		return ligneProgrammeDao.findAllIDE12ByProgramme(ide12, programme);
    }

    public Page<LigneProgramme> findLigneProgrammeByCriteria(LigneProgrammeCriteria criteria, Pageable pageable) {
		return ligneProgrammeDao.findLigneProgrammeByCriteria(criteria.getNumProg(),
                //criteria.getUtilisateur(),
				criteria.getIde12(),
                //criteria.getTitre(),
                //criteria.getAjout(),
                //criteria.getSelection(),
				pageable);
    }
}
