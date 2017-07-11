package fr.sacem.priam.services;

import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeSequnceDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	@Autowired
	ProgrammeDao programmeDao;
	
	@Autowired
	FichierDao fichierDao;
	
	private static final Pageable pageable = new Pageable() {
		
		@Override
		public int getPageNumber() {
			return 0;
		}
		
		@Override
		public int getPageSize() {
			return 5;
		}
		
		@Override
		public int getOffset() {
			return 0;
		}
		
		@Override
		public Sort getSort() {
			return null;
		}
		
		@Override
		public Pageable next() {
			return null;
		}
		
		@Override
		public Pageable previousOrFirst() {
			return null;
		}
		
		@Override
		public Pageable first() {
			return null;
		}
		
		@Override
		public boolean hasPrevious() {
			return false;
		}
	};
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void findProgrammeByCriteria_num_prog() throws Exception {
		
		ProgrammeCriteria criteria = new ProgrammeCriteria();
		criteria.setNumProg("PR170001");
		
		Page<ProgrammeDto> programmeByCriteria = programmeService.findProgrammeByCriteria(criteria, pageable);
		
		assertThat(programmeByCriteria).isNotNull().isNotEmpty();
		assertThat(programmeByCriteria.getSize()).isEqualTo(pageable.getPageSize());
		assertThat(programmeByCriteria.getContent()).isNotEmpty().extracting("numProg").contains("PR170001");
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
	
	
	@Test
	@Transactional
	public void test_tout_desaffecter() throws Exception {
		String pr170001 = "PR170001";
		List<Fichier> fichiersAffectes = fichierDao.findFichiersByIdProgramme(pr170001, Status.AFFECTE);
		programmeService.toutDeaffecter(pr170001);
		
		Programme programme = programmeDao.findOne(pr170001);
		
		assertThat(programme).isNotNull();
		assertThat(programme.getStatut()).isEqualTo(StatutProgramme.CREE);
		
		
		Fichier probe = new Fichier();
		probe.setStatut(Status.CHARGEMENT_OK);
		Example<Fichier> of = Example.of(probe);
		
		List<Fichier> expectedFichiersOK = fichierDao.findAll(Lists.transform(fichiersAffectes, Fichier::getId));
		assertThat(expectedFichiersOK).isNotEmpty();
		assertThat(expectedFichiersOK).extracting("statut").contains(Status.CHARGEMENT_OK);
	}
	
}