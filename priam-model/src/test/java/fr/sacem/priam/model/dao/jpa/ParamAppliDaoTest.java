package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.ParamAppli;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by fandis on 22/06/2017.
 */
public class ParamAppliDaoTest extends AbstractDaoTest {

    @Autowired
    private ParamAppliDao paramAppliDao;



    @Test
    @Transactional(value="transactionManager")
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
    @Transactional(value="transactionManager")
    public void should_return_all_param_appli() {

        List<ParamAppli> all = paramAppliDao.findAll();

        assertThat(all).isNotNull().isNotEmpty();
        assertThat(all.size()).isEqualTo(1);
    }
}
