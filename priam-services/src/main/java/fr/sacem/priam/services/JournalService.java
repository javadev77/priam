package fr.sacem.priam.services;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.JournalJdbcDao;
import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCMSDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import fr.sacem.priam.services.journal.annotation.TypeLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	private static final String OEUVRE_SELECTIONNEE = "Sélectionnée";
	private static final String OEUVRE_DESELECTIONNEE = "Désélectionnée";


	@Autowired
	JournalDao journalDao;

	@Autowired
	private ProgrammeDao programmeDao;

	@Autowired
	LigneProgrammeCPDao ligneProgrammeCPDao;

	@Autowired
	private LigneProgrammeCMSDao ligneProgrammeCMSDao;

	@Autowired
	private JournalJdbcDao journalJdbcDao;


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

	//@Transactional
	public void logJournalOeuvre(ValdierSelectionProgrammeInput selectionProgrammeInput, UserDTO userDTO, TypeLog typeLog) {
		String numProg = selectionProgrammeInput.getNumProg();

		Programme programme = programmeDao.findByNumProg(numProg);

		List<Long> listLigneProgrammePreselectionnees;
		List<Long> listLigneProgrammePredeselectionnees;
		List<Long> listLigneProgrammePreselectionneesCMS;
		List<Long> listLigneProgrammePredeselectionneesCMS;

		List<Journal> journaux = new ArrayList<>();

		if(selectionProgrammeInput.isFromSelection() && programme.getFamille().getCode().equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCodeFamille())) {
			listLigneProgrammePreselectionnees = ligneProgrammeCPDao.findLigneProgrammePreselected(numProg,  true, false);
			listLigneProgrammePredeselectionnees = ligneProgrammeCPDao.findLigneProgrammePreselected(numProg, false, true);

			for (Long ide12 : listLigneProgrammePreselectionnees) {
				Journal journal = new Journal();
				journal.setEvenement(typeLog.getEvenement());
				journal.setNumProg(selectionProgrammeInput.getNumProg());
				journal.setIde12(ide12);
				journal.setDate(new Date());
				journal.setUtilisateur(userDTO.getUserId());

				List<SituationAvant> situationAvantList = new ArrayList<>();
				List<SituationApres> situationApresList = new ArrayList<>();

				SituationAvant situationAvant = new SituationAvant();
				SituationApres situationApres = new SituationApres();

				situationAvant.setSituation(OEUVRE_DESELECTIONNEE);
				situationApres.setSituation(OEUVRE_SELECTIONNEE);

				situationAvantList.add(situationAvant);
				situationApresList.add(situationApres);

				journal.setListSituationAvant(situationAvantList);
				journal.setListSituationApres(situationApresList);

				//journalDao.save(journal);
				journaux.add(journal);
			}


			for (Long ide12 : listLigneProgrammePredeselectionnees) {
				Journal journal = new Journal();
				journal.setEvenement(TypeLog.DESELECTION.getEvenement());
				journal.setNumProg(selectionProgrammeInput.getNumProg());
				journal.setIde12(ide12);
				journal.setDate(new Date());
				journal.setUtilisateur(userDTO.getUserId());

				List<SituationAvant> situationAvantList = new ArrayList<>();
				List<SituationApres> situationApresList = new ArrayList<>();

				SituationAvant situationAvant = new SituationAvant();
				SituationApres situationApres = new SituationApres();

				situationAvant.setSituation(OEUVRE_SELECTIONNEE);
				situationApres.setSituation(OEUVRE_DESELECTIONNEE);

				situationAvantList.add(situationAvant);
				situationApresList.add(situationApres);

				journal.setListSituationAvant(situationAvantList);
				journal.setListSituationApres(situationApresList);

				//journalDao.save(journal);
				journaux.add(journal);
			}

		} else if(selectionProgrammeInput.isFromSelection() && programme.getFamille().getCode().equals(TypeUtilisationEnum.CMS_FRA.getCodeFamille())){
			listLigneProgrammePreselectionneesCMS = ligneProgrammeCMSDao.findLigneProgrammePreselected(numProg, true, false);
			listLigneProgrammePredeselectionneesCMS = ligneProgrammeCMSDao.findLigneProgrammePreselected(numProg, false, true);

			for (Long ide12 : listLigneProgrammePreselectionneesCMS) {
				Journal journal = new Journal();
				journal.setEvenement(typeLog.getEvenement());
				journal.setNumProg(selectionProgrammeInput.getNumProg());
				journal.setIde12(ide12);
				journal.setDate(new Date());
				journal.setUtilisateur(userDTO.getUserId());

				List<SituationAvant> situationAvantList = new ArrayList<>();
				List<SituationApres> situationApresList = new ArrayList<>();

				SituationAvant situationAvant = new SituationAvant();
				SituationApres situationApres = new SituationApres();

				situationAvant.setSituation(OEUVRE_DESELECTIONNEE);
				situationApres.setSituation(OEUVRE_SELECTIONNEE);

				situationAvantList.add(situationAvant);
				situationApresList.add(situationApres);

				journal.setListSituationAvant(situationAvantList);
				journal.setListSituationApres(situationApresList);

				//journalDao.save(journal);
				journaux.add(journal);
			}


			for (Long ide12 : listLigneProgrammePredeselectionneesCMS) {
				Journal journal = new Journal();
				journal.setEvenement(TypeLog.DESELECTION.getEvenement());
				journal.setNumProg(selectionProgrammeInput.getNumProg());
				journal.setIde12(ide12);
				journal.setDate(new Date());
				journal.setUtilisateur(userDTO.getUserId());

				List<SituationAvant> situationAvantList = new ArrayList<>();
				List<SituationApres> situationApresList = new ArrayList<>();

				SituationAvant situationAvant = new SituationAvant();
				SituationApres situationApres = new SituationApres();

				situationAvant.setSituation(OEUVRE_SELECTIONNEE);
				situationApres.setSituation(OEUVRE_DESELECTIONNEE);

				situationAvantList.add(situationAvant);
				situationApresList.add(situationApres);

				journal.setListSituationAvant(situationAvantList);
				journal.setListSituationApres(situationApresList);

				//journalDao.save(journal);
				journaux.add(journal);
			}
		}


		journalJdbcDao.bathInsertJournal(journaux);
	}
}
