package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.common.constants.RoleRight;
import fr.sacem.priam.common.constants.RoleType;
import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by embouazzar on 30/08/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    private static final RoleType DEFAULTTYPE = RoleType.ADM;

    @Test
    public void should_return_all_roles(){
        List<Role> allRoles = roleDao.findAll();

        assertThat(allRoles).isNotNull();
        assertThat(allRoles).extracting("type").contains(RoleType.ADM, RoleType.GST, RoleType.INV);
    }

}
