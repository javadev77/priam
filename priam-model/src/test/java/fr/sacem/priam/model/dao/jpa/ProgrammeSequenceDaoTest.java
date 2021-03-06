package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.ProgrammeKey;
import fr.sacem.priam.model.domain.ProgrammeSequence;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by fandis on 22/06/2017.
 */
public class ProgrammeSequenceDaoTest extends AbstractDaoTest {

    @Autowired
    private ProgrammeSequnceDao programmeSequnceDao;



    @Test
    @Transactional(value="transactionManager")
    public  void add_programme_sequence(){
        ProgrammeSequence programmeSequence =new ProgrammeSequence();
        programmeSequence.setProgrammeKey(new ProgrammeKey("2017",3l));
        ProgrammeSequence programmeSequence1 =programmeSequnceDao.save(programmeSequence);
        assertThat(programmeSequence1).isNotNull();
        assertThat(programmeSequence1.getProgrammeKey().getAnnee()).isEqualTo("2017");
    }
    @Test
    @Transactional(value="transactionManager")
    public void should_return_all_programme_sequence() {

        List<ProgrammeSequence> all = programmeSequnceDao.findAll();

        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all.size()).isEqualTo(2);
    }
    @Test
    @Transactional(value="transactionManager")
    public void get_max_for_year(){
        ProgrammeSequence programmeSequence1 =new ProgrammeSequence();
        programmeSequence1.setProgrammeKey(new ProgrammeKey("2020",3l));
        programmeSequnceDao.save(programmeSequence1);
        ProgrammeSequence programmeSequence2 =new ProgrammeSequence();
        programmeSequence2.setProgrammeKey(new ProgrammeKey("2020",4l));
        programmeSequnceDao.save(programmeSequence2);
        ProgrammeSequence programmeSequence3 =new ProgrammeSequence();
        programmeSequence3.setProgrammeKey(new ProgrammeKey("2020",8l));
        programmeSequnceDao.save(programmeSequence3);
        String max=programmeSequnceDao.getLastElement("2020");
        assertThat(max).isEqualTo("8");

    }
}
