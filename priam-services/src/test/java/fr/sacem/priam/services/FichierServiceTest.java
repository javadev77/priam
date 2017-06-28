package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.FileDto;
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
public class FichierServiceTest {
	
	@Autowired
	FichierService fichierService;
	
	@Autowired
	FichierDao fichierDao;
	
	@Test
	@Transactional
	public void deleteDonneesFichiers() throws Exception {
		FileDto file = fichierDao.findById(2L);
		fichierService.deleteDonneesFichiers(file.getId());
		
		FileDto expected = fichierDao.findById(2L);
		
		assertThat(expected.getStatut()).isEqualTo(Status.ABANDONNE);
	
	}
	
}