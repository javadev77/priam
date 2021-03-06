package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.saref.SareftrGreOeuv;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by belwidanej on 21/08/2017.
 */
@Lazy
@Transactional(value="transactionManager", readOnly = true)
public interface SareftrGreOeuvDao extends JpaRepository<SareftrGreOeuv, String> {

    @Cacheable("SareftrGreOeuvAll")
    @Query("SELECT s FROM SareftrGreOeuv s ")
    List<SareftrGreOeuv> findAll();

}
