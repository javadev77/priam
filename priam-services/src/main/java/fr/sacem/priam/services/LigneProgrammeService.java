package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.domain.LigneProgramme;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.AutocompleteDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.domain.dto.Test;
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
    public List<AutocompleteDto> getListIDE12ByProgramme(Long ide12, String programme) {
		return ligneProgrammeDao.findIDE12sByProgramme(ide12, programme);
    }

    @Transactional
    public List<AutocompleteDto> getTitresByProgramme(String titre, String programme) {
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


}
