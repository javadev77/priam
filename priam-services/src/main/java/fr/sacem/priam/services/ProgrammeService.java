package fr.sacem.priam.services;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.*;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeCPDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.domain.saref.SareftrFamiltyputil;
import fr.sacem.priam.model.domain.saref.SareftrRion;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;
import fr.sacem.priam.model.util.MapperConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by benmerzoukah on 07/06/2017.
 */
@Component
public class ProgrammeService {

	private static final String SOMME = "SOMME";
	public static final int SELECTION = 1;
	public static final String AUTOMATIQUE = "Automatique";
	public static final String MANUEL = "Manuel";

	@Autowired
	ProgrammeViewDao programmeViewDao;
	
	@Autowired
	ProgrammeCPDao programmeCPDao;
	
	@Autowired
	FichierDao fichierDao;
	
	@Autowired
	ParamAppliDao paramAppliDao;
	
	@Autowired
	ProgrammeSequnceDao programmeSequnceDao;
	
	@Autowired
	FichierFelixDao fichierFelixDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(ProgrammeService.class);
	
	@Transactional
	public Page<ProgrammeDto> findProgrammeByCriteria(ProgrammeCriteria criteria, Pageable pageable) {
		
		Page<ProgrammeDto> pageProgramme = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(),
			criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getSareftrFamiltyputil(),
			criteria.getTypeUtilisation(), criteria.getSareftrRionTheorique(), criteria.getSareftrRionPaiement(), criteria.getTypeRepart(),
			pageable);
		
		return pageProgramme;
	}
	
	@Transactional
	public Programme addProgramme(ProgrammeDto programmeDto) {
		MapperConfiguration mapperConfiguration = new MapperConfiguration();
		ParamAppli paramAppli = paramAppliDao.getParam("ANNEE_SEQ_PROGRAMME");
		ProgrammeSequence programmeSequence = new ProgrammeSequence();
		ProgrammeKey programmeKey = new ProgrammeKey();
		LocalDate today = LocalDate.now();
		String year = String.valueOf(today.getYear()).substring(2, 4);
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
		programmeSequnceDao.save(programmeSequence);
		programmeDto.setNumProg(programmeKey.getAnnee() + formated_max_value_to_add);
		// if(Long.getLong(paramAppli.getVal())<today.getYear()){
		//update la sequence
		//appdate la table paramAppli
		// }
		//ajouter une ligne dans la table ProgrammeSequence
		//recuperer la derniere ligne ajouter dans la tavle Programme Sequence
		//construire le Id du programme
		Programme mappedProgramme = mapperConfiguration.convertProgrammeDtoToProgramme(programmeDto);
	    
	    	mappedProgramme.setUsercre(programmeDto.getUsercre());
		mappedProgramme.setDateCreation(new Date());
		mappedProgramme.setStatut(StatutProgramme.CREE);
		
		mappedProgramme = programmeCPDao.save(mappedProgramme);
		
		return mappedProgramme;
	}
	
	@Transactional
	public List<Programme> serachProgrammeByNom(String nom) {
		List<Programme> resultat = new ArrayList<>();
		if (StringUtils.isNotEmpty(nom)) {
			Programme programmeSearcher = new Programme();
			programmeSearcher.setNom(nom);
			Example example = Example.of(programmeSearcher);
			resultat = programmeCPDao.findAll(example);
		} else {
			LOG.info("Nom programme vide ou null");
		}
		return resultat;
	}
	
	@Transactional
	public Programme updateProgramme(ProgrammeDto programmeDto) {
		Programme programme = programmeCPDao.findOne(programmeDto.getNumProg());
		
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

		return programmeCPDao.save(programme);
		
	}
	
	@Transactional
	public Programme abandonnerProgramme(ProgrammeDto programmeDto) {
		Programme programme = programmeCPDao.findOne(programmeDto.getNumProg());
		programme.setStatut(StatutProgramme.ABANDONNE);
		
		return programmeCPDao.saveAndFlush(programme);
	}
	
	@Transactional
	public void toutDeaffecter(String numProg, String user) {
		LOG.info("Debut :Deaffecter les fichiers lies au programme (" + numProg + ")");
		fichierDao.clearSelectedFichiers(numProg, Status.CHARGEMENT_OK);
		Programme programme = programmeCPDao.findOne(numProg);
		programme.setStatut(StatutProgramme.CREE);
		programme.setUsermaj(user);
		programme.setDatmaj(new Date());
		
		programmeCPDao.saveAndFlush(programme);
		LOG.info("Fin :Deaffecter les fichiers lies au programme (" + numProg + ")");
	}

	@Transactional
	public List<String> findAllNomProgByCriteria() {
		return programmeViewDao.findAllNomProgByCriteria();
	}

	@Transactional
	public Programme validerProgramme(ProgrammeDto programmeDto) {
		Programme programme = programmeCPDao.findOne(programmeDto.getNumProg());
		programme.setStatut(StatutProgramme.VALIDE);
		programme.setUserValidation(programmeDto.getUserValidation());
		programme.setDateValidation(new Date());

		return programmeCPDao.saveAndFlush(programme);
	}

	@Transactional
	public Programme invaliderProgramme(ProgrammeDto programmeDto) {
	    FichierFelix ff = fichierFelixDao.findByNumprog(programmeDto.getNumProg());
	    if(ff != null) {
		  fichierFelixDao.delete(ff.getId());
		  fichierFelixDao.flush();
	    }
	    
	    Programme programme = programmeCPDao.findOne(programmeDto.getNumProg());
	    programme.setStatut(StatutProgramme.EN_COURS);
	    programme.setUserValidation(null);
	    programme.setDateValidation(null);
		
		
	    return programmeCPDao.saveAndFlush(programme);
	}

	public Map<String, Long> getDurDifProgramme(String numProg, String statut){

		Map<String, Long> result = new HashMap<>();
		result.put(AUTOMATIQUE, 0L);
		result.put(MANUEL, 0L);
		result.put(SOMME, 0L);

		Programme programme = programmeCPDao.findOne(numProg);

		Integer selection = SELECTION;

		List<Object> prog = programmeCPDao.compterOuvres(numProg, selection);

		for (Object indicateur : prog) {
			Object[] indObjects = (Object[]) indicateur;
			result.put((String) indObjects[1], ((BigInteger) indObjects[0]).longValue());
		}

		if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(programme.getTypeUtilisation().getCode())) {
			result.put(SOMME, programmeCPDao.calculerQuantiteOeuvres(numProg, selection));
		} else if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(programme.getTypeUtilisation().getCode())) {
			result.put(SOMME, programmeCPDao.calculerDureeOeuvres(numProg, selection));
		}

    		return result;
	}

	@Transactional
	public Programme updateStatutProgrammeToAffecte(ProgrammeDto programmeDTO) {
		Programme programme = programmeCPDao.findOne(programmeDTO.getNumProg());
		programme.setStatut(StatutProgramme.AFFECTE);
		programme.setUseraffect(programmeDTO.getUseraffecte());
		programme.setDataffect(new Date());
		return programmeCPDao.saveAndFlush(programme);
	}
}
