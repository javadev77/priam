package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.*;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.dto.JournalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by monfleurm on 15/03/2018.
 */
@Component
public class JournalService {


	@Autowired
	JournalDao journalDao;
	

	@Transactional
	public Page<Journal> findAllEvents(Pageable pageable) {
		
		Page<Journal> pageJournal = journalDao.findAll(pageable);

		return pageJournal;
	}

	/*public Page<Journal> findByNumProg(String numProg, Pageable pageable) {
		Page<Journal> pageJournal = journalDao.findByNumProg(numProg, pageable);

		return pageJournal;
	}*/

	@Transactional
	public Page<Journal> findJournalByNumProg(String numProg, Pageable pageable){
			Page<Journal> pageJournalEvent = journalDao.findJournalByNumProg(numProg, pageable);
		return pageJournalEvent;
	}
}
