package fr.sacem.priam.model.fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.JPAConfigurationTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JPAConfigurationTest.class})
@Transactional
public class FichierDaoTest {


}
