package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.ProgrammeSequnceDao;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 28/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class, ConfigurationTest.class})
public class ProgrammeServiceTest {
	
	@Autowired
	ProgrammeService programmeService;
	
	@Autowired
	ProgrammeSequnceDao programmeSequnceDao;
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void findProgrammeByCriteria() throws Exception {
		
		//Page<ProgrammeDto> programmeByCriteria = programmeService.findProgrammeByCriteria();
	}
	
	@Test
	@Transactional
	public void addProgramme() throws Exception {
		ProgrammeDto programmeDto = new ProgrammeDto();
		programmeDto.setNom("Test-PR01");
		programmeDto.setRionTheorique(619);
		programmeDto.setFamille("COPIEPRIV");
		
		String lastElement = programmeSequnceDao.getLastElement("17");
		Integer lastSeq = StringUtils.isNotEmpty(lastElement) ?  Integer.valueOf(lastElement) : 0;
		
		Programme programme = programmeService.addProgramme(programmeDto);
		
		assertThat(programme.getNumProg()).isEqualTo("PR17" + StringUtils.leftPad(String.valueOf(lastSeq + 1), 4, "0"));
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
		programmeDto.setNumProg("PR170001");
		programmeDto.setNom("Programme-PR170001");
		programmeDto.setRionTheorique(619);
		programmeDto.setFamille("COPIEPRIV");
		
		Programme programme = programmeService.updateProgramme(programmeDto);
		
		assertThat(programme).isNotNull();
		assertThat(programme.getNom()).isEqualTo("Programme-PR170001");
		assertThat(programme.getRionTheorique().getRion()).isEqualTo(619);
		assertThat(programme.getFamille().getCode()).isEqualTo("COPIEPRIV");
	}
	
	@Test
	@Transactional
	public void abandonnerProgramme() throws Exception {
		ProgrammeDto programmeDto = new ProgrammeDto();
		programmeDto.setNumProg("PR170001");
		
		Programme programme = programmeService.abandonnerProgramme(programmeDto);
		
		assertThat(programme).isNotNull();
		assertThat(programme.getStatut()).isEqualTo(StatutProgramme.ABANDONNE);
	}
	
}