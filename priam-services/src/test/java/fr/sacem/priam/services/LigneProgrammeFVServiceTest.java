package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionCMSDto;
import fr.sacem.priam.model.domain.fv.LigneProgrammeFV;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        LigneProgrammeFVServiceTest.class})
@ContextConfiguration(classes={JpaConfigurationTest.class, ConfigurationTest.class})
@ActiveProfiles("test")
@Transactional
public class LigneProgrammeFVServiceTest extends AbstractTestExecutionListener {

    private static final String NUM_PROG = "190001";
    private static final Long IDE12 = 2240093411L;
    public static final String CDE_UTIL = "XXX";
    public static final String CDE_CISAC_058 = "058";
    private static final String STRING_IDE12 = "2000163011";
    private static final String TITRE = "IMPRO JAZZ";
    private static final String STATUS = "AFFECTE";
    public static final String MANUEL = "MANUEL";
    public static final String AUTOMATIQUE = "AUTOMATIQUE";
    public static final String CORRIGE = "CORRIGE";
    private static final String SOMME = "SOMME";

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

    @Autowired
    LigneProgrammeFVServiceImpl ligneProgrammeFVService;

    @Test
    @Transactional
    public void getListIDE12ByProgramme() {
        List<KeyValueDto> listIDE12ByProgramme = ligneProgrammeFVService.getListIDE12ByProgramme(IDE12, NUM_PROG);
        assertThat(listIDE12ByProgramme).isNotNull().isNotEmpty();
        assertThat(listIDE12ByProgramme.stream().anyMatch(keyValue -> keyValue.getCode().toString().contains(IDE12.toString()))).isEqualTo(true);
    }

    @Test
    @Transactional
    public void getTitresByProgramme() {
        List<KeyValueDto> titresByProgramme = ligneProgrammeFVService.getTitresByProgramme(TITRE, NUM_PROG);
        assertThat(titresByProgramme).isNotNull().isNotNull();
        assertThat(titresByProgramme.stream().anyMatch(keyValueDto -> keyValueDto.getValue().toString().contains(TITRE))).isEqualTo(true);
    }

    @Test
    @Transactional
    public void findLigneProgrammeByCriteria() {
        LigneProgrammeCriteria criteria = new LigneProgrammeCriteria();
        criteria.setNumProg(NUM_PROG);
        criteria.setIde12(Long.valueOf(IDE12));
        Page<SelectionCMSDto> ligneProgrammeByCriteria = ligneProgrammeFVService.findLigneProgrammeByCriteria(criteria, pageable);
        assertThat(ligneProgrammeByCriteria).isNotNull();
    }

}
