package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.cp.FichierCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeCPDao;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.dto.FileDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 28/06/2017.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class, ConfigurationTest.class})
public class FichierServiceTest {
	
	@Autowired
	FichierService fichierService;
	
	@Autowired
	FichierCPDao fichierCPDao;
	
	@Autowired
	ProgrammeCPDao programmeCPDao;
	
	@Test
	@Transactional
	public void deleteDonneesFichiers() throws Exception {
		FileDto file = fichierCPDao.findById(2L);
		fichierService.deleteDonneesFichiers(file.getId());
		
		FileDto expected = fichierCPDao.findById(2L);
		
		assertThat(expected.getStatut()).isEqualTo(Status.ABANDONNE);
	
	}
	
	@Test
	@Transactional
	@Ignore
	public void majFichiersAffectesAuProgramme() throws Exception {
		
		fichierService.majFichiersAffectesAuProgramme("170001", Collections.emptyList(), "GUEST");
		
		Programme pr170001 = programmeCPDao.findOne("170001");
		
		assertThat(pr170001).isNotNull();
		assertThat(pr170001.getStatut()).isEqualTo(StatutProgramme.CREE);
		
		
	}
	
}