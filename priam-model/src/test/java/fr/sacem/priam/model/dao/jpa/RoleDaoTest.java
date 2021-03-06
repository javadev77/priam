package fr.sacem.priam.model.dao.jpa;

import edu.emory.mathcs.backport.java.util.Arrays;
import fr.sacem.priam.common.constants.RoleType;
import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by embouazzar on 30/08/2017.
 */

@Transactional(value="transactionManager")
public class RoleDaoTest extends AbstractDaoTest {

    @Autowired
    private RoleDao roleDao;

    private static final RoleType DEFAULTTYPE = RoleType.ADM;

    @Test
    public void should_return_all_roles(){
        List<Role> allRoles = roleDao.findAll();

        assertThat(allRoles).isNotNull();
        assertThat(allRoles).extracting("type").contains(RoleType.ADM, RoleType.GST, RoleType.INV);
    }
    
    @Test
    public void should_return_roles_ADM() {
        Role roleADM = roleDao.findByType(RoleType.ADM);
        
        assertThat(roleADM).isNotNull();
        assertThat(roleADM.getType()).isNotNull().isEqualTo(RoleType.ADM);
    }
    
    @Test
    public void should_find_by_external_ID() {
        List<Role> byExternalIdIn = roleDao.findByExternalIdIn(Arrays.asList(new String[]{"ADM", "GST"}));
    
        assertThat(byExternalIdIn).isNotNull().extracting("type").containsExactly(RoleType.ADM, RoleType.GST);
    }
}
