package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.catcms.JournalCatcms;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by embouazzar on 31/08/2018.
 */
public class JournalCatcmsDaoTest extends AbstractDaoTest {

    @Autowired
    JournalCatcmsDao journalCatcmsDao;

    private static final String TYPE_CMS_FR = "FR";
    private static final String TYPE_CMS_ANF = "ANF";

    @Test
    public void findByCriteriaTYPE_CMS() throws Exception {

        Page<JournalCatcms> result = journalCatcmsDao.findByCriteria(TYPE_CMS_FR, null, null,
                null, null, new PageRequest(0, 5));
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(10);

    }

}
