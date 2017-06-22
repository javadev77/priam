package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.Famille;
import fr.sacem.priam.model.domain.ProgrammeKey;
import fr.sacem.priam.model.domain.ProgrammeSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by fandis on 22/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
public class ProgrammeSequenceDaoTest {

    @Autowired
    private ProgrammeSequnceDao programmeSequnceDao;



    @Test
    @Transactional
    public  void add_programme_sequence(){
        ProgrammeSequence programmeSequence =new ProgrammeSequence();
        programmeSequence.setProgrammeKey(new ProgrammeKey("FR","2017"));
        ProgrammeSequence programmeSequence1 =programmeSequnceDao.save(programmeSequence);
        assertThat(programmeSequence1).isNotNull();
        assertThat(programmeSequence.getProgrammeKey().getPrefix()).isEqualTo("FR");
    }
    @Test
    @Transactional
    public void should_return_all_programme_sequence() {

        List<ProgrammeSequence> all = programmeSequnceDao.findAll();

        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all.size()).isEqualTo(2);
    }
}
