package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.*;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.domain.saref.SareftrFamiltyputil;
import fr.sacem.priam.model.domain.saref.SareftrRion;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;
import fr.sacem.priam.model.util.MapperConfiguration;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.journal.annotation.LogEtatProgramme;
import fr.sacem.priam.services.journal.annotation.LogProgramme;
import fr.sacem.priam.services.journal.annotation.TypeLog;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by benmerzoukah on 07/06/2017.
 */
@Component
public class ProgrammeService {

	public static final int SELECTION = 1;

	@Autowired
	ProgrammeViewDao programmeViewDao;
	
	@Autowired
	ProgrammeDao programmeDao;
	
	@Autowired
	FichierDao fichierDao;
	
	@Autowired
	ParamAppliDao paramAppliDao;
	
	@Autowired
	ProgrammeSequnceDao programmeSequnceDao;
	
	@Autowired
	FichierFelixDao fichierFelixDao;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private final ReentrantLock lock = new ReentrantLock();
	
	private static final Logger LOG = LoggerFactory.getLogger(ProgrammeService.class);
	
	@Transactional
	public Page<ProgrammeDto> findProgrammeByCriteria(ProgrammeCriteria criteria, Pageable pageable) {
		
		Page<ProgrammeDto> pageProgramme = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(),
			criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getSareftrFamiltyputil(),
			criteria.getTypeUtilisation(), criteria.getSareftrRionTheorique(), criteria.getSareftrRionPaiement(), criteria.getTypeRepart(),
			pageable);
		
		return pageProgramme;
	}

    @LogProgramme(event = TypeLog.CREATION)
	public Programme addProgramme(ProgrammeDto programmeDto, UserDTO userDTO) {
		MapperConfiguration mapperConfiguration = new MapperConfiguration();
		ParamAppli paramAppli = paramAppliDao.getParam("ANNEE_SEQ_PROGRAMME");
		ProgrammeSequence programmeSequence = new ProgrammeSequence();
		ProgrammeKey programmeKey = new ProgrammeKey();
		LocalDate today = LocalDate.now();
		String year = String.valueOf(today.getYear()).substring(2, 4);
		//locke

		Programme mappedProgramme = null;
		lock.lock();
		TransactionStatus ts = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			String max_value = programmeSequnceDao.getLastElement(year);


			if (Long.valueOf(year) > Long.valueOf(paramAppli.getVal())) {
				max_value = "0";
				paramAppli.setVal(year);
				paramAppliDao.saveAndFlush(paramAppli);
			}
			if (max_value == null)
				max_value = "0";
			Long max_value_to_add = Long.valueOf(max_value) + SELECTION;
			String formated_max_value_to_add = StringUtils.leftPad(max_value_to_add.toString(), 4, "0");
			programmeKey.setAnnee(year);
			programmeKey.setCodeSequence(max_value_to_add);
			programmeSequence.setProgrammeKey(programmeKey);
			programmeSequnceDao.saveAndFlush(programmeSequence);
			programmeDto.setNumProg(programmeKey.getAnnee() + formated_max_value_to_add);

			mappedProgramme = mapperConfiguration.convertProgrammeDtoToProgramme(programmeDto);

			mappedProgramme.setUsercre(programmeDto.getUsercre());
			mappedProgramme.setDateCreation(new Date());
			mappedProgramme.setStatut(StatutProgramme.CREE);

			mappedProgramme = programmeDao.saveAndFlush(mappedProgramme);

			transactionManager.commit(ts);

		} catch (Exception e) {
			transactionManager.rollback(ts);
		} finally {
			lock.unlock();

		}



		// if(Long.getLong(paramAppli.getVal())<today.getYear()){
		//update la sequence
		//appdate la table paramAppli
		// }
		//ajouter une ligne dans la table ProgrammeSequence
		//recuperer la derniere ligne ajouter dans la tavle Programme Sequence
		//construire le Id du programme

		
		return mappedProgramme;
	}
	
	@Transactional("transactionManager")
	public List<Programme> serachProgrammeByNom(String nom) {
		List<Programme> resultat = new ArrayList<>();
		if (StringUtils.isNotEmpty(nom)) {
			Programme programmeSearcher = new Programme();
			programmeSearcher.setNom(nom);
			Example example = Example.of(programmeSearcher);
			resultat = programmeDao.findAll(example);
		} else {
			LOG.info("Nom programme vide ou null");
		}
		return resultat;
	}



	@Transactional
	@LogProgramme(event = TypeLog.MODIFICATION)
	public Programme updateProgramme(ProgrammeDto programmeDto, UserDTO userDTO) {
		Programme programme = programmeDao.findOne(programmeDto.getNumProg());
		
		programme.setNom(programmeDto.getNom());
		
		SareftrFamiltyputil sareftrFamiltyputil = new SareftrFamiltyputil();
		sareftrFamiltyputil.setCode(programmeDto.getFamille());
		programme.setFamille(sareftrFamiltyputil);
		
		SareftrTyputil sareftrTyputil = new SareftrTyputil();
		sareftrTyputil.setCode(programmeDto.getTypeUtilisation());
		sareftrTyputil.setCodeFamille(programmeDto.getFamille());
		programme.setTypeUtilisation(sareftrTyputil);
		
		SareftrRion sareftrRion = new SareftrRion();
		sareftrRion.setRion(programmeDto.getRionTheorique());
		programme.setRionTheorique(sareftrRion);
		
		programme.setTypeRepart(programmeDto.getTypeRepart());
		programme.setDatmaj(new Date());
		programme.setUsermaj(programmeDto.getUsermaj());

		programme.setDateDbtPrg(programmeDto.getDateDbtPrg());
		programme.setDateFinPrg(programmeDto.getDateFinPrg());
		programme.setCdeTer(programmeDto.getCdeTer());

		return programmeDao.save(programme);
		
	}
	
	@Transactional
    @LogProgramme(event = TypeLog.SUPPRESSION)
	public Programme abandonnerProgramme(ProgrammeDto programmeDto, UserDTO userDTO) {
		Programme programme = programmeDao.findOne(programmeDto.getNumProg());
		programme.setStatut(StatutProgramme.ABANDONNE);
		
		return programmeDao.saveAndFlush(programme);
	}
	
	@Transactional
	public void toutDeaffecter(String numProg, String user) {
		LOG.info("Debut :Deaffecter les fichiers lies au programme (" + numProg + ")");
		fichierDao.clearSelectedFichiers(numProg, Status.CHARGEMENT_OK);
		Programme programme = programmeDao.findOne(numProg);
		programme.setStatut(StatutProgramme.CREE);
		programme.setUsermaj(user);
		programme.setDatmaj(new Date());
		
		programmeDao.saveAndFlush(programme);
		LOG.info("Fin :Deaffecter les fichiers lies au programme (" + numProg + ")");
	}

	@Transactional
	public List<String> findAllNomProgByCriteria() {
		return programmeViewDao.findAllNomProgByCriteria();
	}

	@Transactional
	@LogEtatProgramme(event = TypeLog.VALIDATION)
	public Programme validerProgramme(ProgrammeDto programmeDto, UserDTO userDTO) {
		Programme programme = programmeDao.findOne(programmeDto.getNumProg());
		programme.setStatut(StatutProgramme.VALIDE);
		programme.setUserValidation(programmeDto.getUserValidation());
		programme.setDateValidation(new Date());

		return programmeDao.saveAndFlush(programme);
	}

	@Transactional
	@LogEtatProgramme(event = TypeLog.INVALIDATION)
	public Programme invaliderProgramme(ProgrammeDto programmeDto, UserDTO userDTO) {
	    FichierFelix ff = fichierFelixDao.findByNumprog(programmeDto.getNumProg());
	    if(ff != null) {
		  fichierFelixDao.delete(ff.getId());
		  fichierFelixDao.flush();
	    }
	    
	    Programme programme = programmeDao.findOne(programmeDto.getNumProg());
	    programme.setStatut(StatutProgramme.EN_COURS);
	    programme.setUserValidation(null);
	    programme.setDateValidation(null);
		
		
	    return programmeDao.saveAndFlush(programme);
	}

	@Transactional
	public Programme enregistrerSelection(ProgrammeDto programmeDto) {
		FichierFelix ff = fichierFelixDao.findByNumprog(programmeDto.getNumProg());
		if(ff != null) {
			fichierFelixDao.delete(ff.getId());
			fichierFelixDao.flush();
		}

		Programme programme = programmeDao.findOne(programmeDto.getNumProg());
		programme.setStatut(StatutProgramme.EN_COURS);
		programme.setUserValidation(null);
		programme.setDateValidation(null);


		return programmeDao.saveAndFlush(programme);
	}

	@Transactional
	@LogEtatProgramme(event = TypeLog.ANNULATION)
	public Programme updateStatutProgrammeToAffecte(ProgrammeDto programmeDTO, UserDTO userDTO) {
		Programme programme = programmeDao.findOne(programmeDTO.getNumProg());
		programme.setStatut(StatutProgramme.AFFECTE);
		programme.setUseraffect(userDTO.getUserId());
		programme.setDataffect(new Date());
		return programmeDao.saveAndFlush(programme);
	}

	@Transactional
	public void majStatutToMisEnRepartition(ProgrammeDto programmeDTO, UserDTO userDTO) {
		Programme prog = programmeDao.findOne(programmeDTO.getNumProg());
		prog.setStatut(programmeDTO.getStatut());
		prog.setUsermaj(programmeDTO.getUsermaj());

		programmeDao.saveAndFlush(prog);
	}
}
