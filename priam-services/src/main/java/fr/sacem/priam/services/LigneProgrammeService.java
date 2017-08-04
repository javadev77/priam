package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by jbelwidane on 25/07/2017.
 */
@Component
public class LigneProgrammeService {
	
	@Autowired
    private LigneProgrammeDao ligneProgrammeDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(LigneProgrammeService.class);

	@Transactional
    public List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme) {
		return ligneProgrammeDao.findIDE12sByProgramme(ide12, programme);
    }

    @Transactional
    public List<KeyValueDto> getTitresByProgramme(String titre, String programme) {
        return ligneProgrammeDao.findTitresByProgramme(titre, programme);
    }

    @Transactional
    public List<String> getUtilisateursByProgramme(String programme) {
        return ligneProgrammeDao.findUtilisateursByProgramme(programme);
    }

    @Transactional
    public Page<SelectionDto> findLigneProgrammeByCriteria(LigneProgrammeCriteria criteria,Pageable pageable) {
	    return ligneProgrammeDao.findLigneProgrammeByCriteria(criteria.getNumProg(),
                criteria.getUtilisateur(),
				criteria.getIde12(),
                criteria.getTitre(),
                criteria.getAjout(),
                criteria.getSelection(),pageable);
    }

    @Transactional
    public void selectAll(String numProg) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, true);
    }

    @Transactional
    public void selectLigneProgramme(String numProg, Set<Long> idLingesProgrammes) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, false);
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, idLingesProgrammes);
    }

    @Transactional
    public void selectAllLigneProgrammeExcept(String numProg, Set<Long> idLingesProgrammes) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, false);
        ligneProgrammeDao.updateSelectionByNumProgrammeExcept(numProg, idLingesProgrammes);
    }

    @Transactional
    public void deselectAll(String numProg) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, false);
    }


}
