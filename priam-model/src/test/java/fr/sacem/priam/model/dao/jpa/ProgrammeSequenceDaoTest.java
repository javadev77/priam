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
        programmeSequence.setProgrammeKey(new ProgrammeKey("FR","2017",3l));
        ProgrammeSequence programmeSequence1 =programmeSequnceDao.save(programmeSequence);
        assertThat(programmeSequence1).isNotNull();
        assertThat(programmeSequence1.getProgrammeKey().getPrefix()).isEqualTo("FR");
    }
    @Test
    @Transactional
    public void should_return_all_programme_sequence() {

        List<ProgrammeSequence> all = programmeSequnceDao.findAll();

        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all.size()).isEqualTo(2);
    }
    @Test
    @Transactional
    public void get_max_for_year(){
        ProgrammeSequence programmeSequence1 =new ProgrammeSequence();
        programmeSequence1.setProgrammeKey(new ProgrammeKey("FR","2020",3l));
        programmeSequnceDao.save(programmeSequence1);
        ProgrammeSequence programmeSequence2 =new ProgrammeSequence();
        programmeSequence2.setProgrammeKey(new ProgrammeKey("FR","2020",4l));
        programmeSequnceDao.save(programmeSequence2);
        ProgrammeSequence programmeSequence3 =new ProgrammeSequence();
        programmeSequence3.setProgrammeKey(new ProgrammeKey("FR","2020",8l));
        programmeSequnceDao.save(programmeSequence3);
        String max=programmeSequnceDao.getLastElement("2020");
        assertThat(max).isEqualTo("8");

    }
}
