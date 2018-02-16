package fr.sacem.priam.model.dao;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * Created by benmerzoukah on 11/01/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
@ActiveProfiles("test")
public abstract class AbstractDaoTest extends AbstractTestExecutionListener {
}
