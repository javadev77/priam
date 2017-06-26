package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.ParamAppli;
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
public class ParamAppliDaoTest {

    @Autowired
    private ParamAppliDao paramAppliDao;



    @Test
    @Transactional
    public  void add_param_appli(){
        ParamAppli paramAppli =new ParamAppli();
        paramAppli.setCdeParam("ANNEE_SEQ_PROGRAMME");
        paramAppli.setLibStat("mon message");
        paramAppli.setVal("2018");
        ParamAppli paramAppli1 = paramAppliDao.save(paramAppli);
        assertThat(paramAppli1).isNotNull();
        assertThat(paramAppli1.getVal()).isEqualTo("2018");
    }
    @Test
    @Transactional
    public void should_return_all_param_appli() {

        List<ParamAppli> all = paramAppliDao.findAll();

        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all.size()).isEqualTo(1);
    }
}
