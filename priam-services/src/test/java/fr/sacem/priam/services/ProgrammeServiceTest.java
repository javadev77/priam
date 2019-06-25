package fr.sacem.priam.services;

import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeSequnceDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.api.LigneProgrammeService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 28/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class, ConfigurationTest.class})
@ActiveProfiles("test")
public class ProgrammeServiceTest {

	public static final String NUM_PROG = "170001";
	public static final String AFFECTE = "AFFECTE";

	@Autowired
	ProgrammeService programmeService;

	@Qualifier("ligneProgrammeCPService")
	@Autowired
	LigneProgrammeService ligneProgrammeService;


	@Autowired
	ProgrammeSequnceDao programmeSequnceDao;
	
	@Autowired
	ProgrammeDao programmeDao;
	
	@Autowired
	FichierDao fichierDao;
	
	private static final Pageable pageable = new PageRequest(0, 5);
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void findProgrammeByCriteria_num_prog() throws Exception {
		
		ProgrammeCriteria criteria = new ProgrammeCriteria();
		criteria.setNumProg(NUM_PROG);

		criteria.setSareftrFamiltyputil(Lists.newArrayList("COPIEPRIV"));
		criteria.setTypeUtilisation(Lists.newArrayList("CPRIVSONPH"));
		
		Page<ProgrammeDto> programmeByCriteria = programmeService.findProgrammeByCriteria(criteria, pageable);
		
		assertThat(programmeByCriteria).isNotNull().isNotEmpty();
		assertThat(programmeByCriteria.getSize()).isEqualTo(pageable.getPageSize());
		assertThat(programmeByCriteria.getContent()).isNotEmpty().extracting("numProg").contains(NUM_PROG);
	}
	
	@Test
	@Transactional
	public void addProgramme() throws Exception {
		ProgrammeDto programmeDto = new ProgrammeDto();
		UserDTO userDTO = new UserDTO();
		programmeDto.setNom("Test-PR01");
		programmeDto.setRionTheorique(619);
		programmeDto.setFamille("COPIEPRIV");

		String year = String.valueOf(LocalDate.now().getYear()).substring(2,4);
		String lastElement = programmeSequnceDao.getLastElement(year);
		Integer lastSeq = StringUtils.isNotEmpty(lastElement) ?  Integer.valueOf(lastElement) : 0;
		
		Programme programme = programmeService.addProgramme(programmeDto, userDTO);
		assertThat(programme.getNumProg()).isEqualTo(year  + StringUtils.leftPad(String.valueOf(lastSeq + 1), 4, "0"));
		assertThat(programme.getNom()).isEqualTo("Test-PR01");
		assertThat(programme.getRionTheorique().getRion()).isEqualTo(619);
		assertThat(programme.getFamille().getCode()).isEqualTo("COPIEPRIV");
	}
	
	@Test
	@Transactional
	public void serachProgrammeByNom() throws Exception {
		
		List<Programme> programmes = programmeService.serachProgrammeByNom("Programme 01");
		
		assertThat(programmes).isNotNull().isNotEmpty();
		programmes.forEach( programme -> assertThat(programme.getNom()).isEqualTo("Programme 01"));
		
		
	}
	
	@Test
	@Transactional
	public void updateProgramme() throws Exception {
		ProgrammeDto programmeDto = new ProgrammeDto();
		UserDTO userDTO = new UserDTO();
		programmeDto.setNumProg(NUM_PROG);
		programmeDto.setNom("Programme-170001");
		programmeDto.setRionTheorique(619);
		programmeDto.setFamille("COPIEPRIV");
		
		Programme programme = programmeService.updateProgramme(programmeDto, userDTO);
		
		assertThat(programme).isNotNull();
		assertThat(programme.getNom()).isEqualTo("Programme-170001");
		assertThat(programme.getRionTheorique().getRion()).isEqualTo(619);
		assertThat(programme.getFamille().getCode()).isEqualTo("COPIEPRIV");
	}
	
	@Test
	@Transactional
	public void abandonnerProgramme() throws Exception {
		ProgrammeDto programmeDto = new ProgrammeDto();
		UserDTO userDTO = new UserDTO();
		programmeDto.setNumProg(NUM_PROG);
		
		Programme programme = programmeService.abandonnerProgramme(programmeDto, userDTO);
		
		assertThat(programme).isNotNull();
		assertThat(programme.getStatut()).isEqualTo(StatutProgramme.ABANDONNE);
	}
	
	
	@Test
	@Transactional
	public void test_tout_desaffecter() throws Exception {
		String pr170001 = NUM_PROG;
		List<Fichier> fichiersAffectes = fichierDao.findFichiersByIdProgramme(pr170001, Status.AFFECTE);
		programmeService.toutDeaffecter(pr170001, "USER_ID_TEST");
		
		Programme programme = programmeDao.findOne(pr170001);
		
		assertThat(programme).isNotNull();
		assertThat(programme.getStatut()).isEqualTo(StatutProgramme.CREE);
		
		
		
		List<Fichier> expectedFichiersOK = fichierDao.findAll(Lists.transform(fichiersAffectes, Fichier::getId));
		assertThat(expectedFichiersOK).isNotEmpty();
		assertThat(expectedFichiersOK).extracting("statut").contains(Status.CHARGEMENT_OK);
	}
	
	@Test
	@Transactional
	public void test_findAllNomProgByCriteria() throws Exception {
		
		List<String> allNomProgByCriteria = programmeService.findAllNomProgByCriteria();
		
		assertThat(allNomProgByCriteria).isNotNull();
	}

	@Test
	@Transactional
	public void validerProgramme() {
		ProgrammeDto programmeDto = new ProgrammeDto();
		UserDTO userDTO = new UserDTO();
		programmeDto.setNumProg(NUM_PROG);
		Programme programme = programmeService.validerProgramme(programmeDto, userDTO);
		assertThat(programme).isNotNull();
		assertThat(programme.getStatut()).isEqualTo(StatutProgramme.VALIDE);
	}

	@Test
	@Transactional
	public void invaliderProgramme() {
		ProgrammeDto programmeDto = new ProgrammeDto();
		UserDTO userDTO = new UserDTO();
		programmeDto.setNumProg(NUM_PROG);
		Programme programme = programmeService.invaliderProgramme(programmeDto, userDTO);
		assertThat(programme).isNotNull();
		assertThat(programme.getStatut()).isEqualTo(StatutProgramme.EN_COURS);
	}

	@Test
	public void getDurDifProgramme(){
		Map<String, Long> durDifProgramme = ((LigneProgrammeCPServiceImpl)ligneProgrammeService).getDurDifProgramme(NUM_PROG, AFFECTE);
		assertThat(durDifProgramme).isNotEmpty();
		assertThat(durDifProgramme.size()).isEqualTo(5);
	}

	@Test
	@Transactional
	public void updateStatutProgrammeToAffecte() {
		ProgrammeDto programmeDto = new ProgrammeDto();
		UserDTO userDTO = new UserDTO();
		programmeDto.setNumProg(NUM_PROG);
		Programme programme = programmeService.updateStatutProgrammeToAffecte(programmeDto, userDTO);
		assertThat(programme).isNotNull();
		assertThat(programme.getStatut()).isEqualTo(StatutProgramme.AFFECTE);
	}
	
}