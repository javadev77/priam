package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.catcms.StatistiqueCatcms;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by embouazzar on 01/10/2018.
 */
public class StatistiquesCatcmsDaoTest extends AbstractDaoTest {

    @Autowired
    StatistiquesCatcmsDao statistiquesCatcmsDao;

    @Test
    public void findAll() throws Exception {

        Page<StatistiqueCatcms> result = statistiquesCatcmsDao.findAll(new PageRequest(0, 5));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(4);
    }
}
