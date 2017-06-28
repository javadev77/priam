package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 28/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class, ConfigurationTest.class})
public class ProgrammeServiceTest {
	
	@Autowired
	ProgrammeService programmeService;
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void findProgrammeByCriteria() throws Exception {
	}
	
	@Test
	public void addProgramme() throws Exception {
	}
	
	@Test
	public void serachProgrammeByNom() throws Exception {
	}
	
	@Test
	@Transactional
	public void updateProgramme() throws Exception {
		ProgrammeDto programmeDto = new ProgrammeDto();
		programmeDto.setNumProg("PR170001");
		programmeDto.setNom("Programme-PR170001");
		programmeDto.setRionTheorique(619);
		
		Programme programme = programmeService.updateProgramme(programmeDto);
		
		assertThat(programme).isNotNull();
		assertThat(programme.getNom()).isEqualTo("Programme-PR170001");
		assertThat(programme.getRionTheorique().getRion()).isEqualTo(619);
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