package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.common.constants.RoleType;
import fr.sacem.priam.model.domain.Role;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by embouazzar on 30/08/2017.
 */
@Lazy
public interface RoleDao extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role>{
    
    @Transactional(readOnly=true)
    Role findByType(RoleType type);

    @Transactional(readOnly = true)
    List<Role> findByExternalIdIn(List<String> externalIds);

}
