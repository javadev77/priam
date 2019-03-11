package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.saref.SareftrSte;
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
@Transactional(readOnly = true)
public interface SareftrSteDao extends JpaRepository<SareftrSte, Long> {

    @Cacheable("sareftrSteAll")
    @Query("SELECT s FROM SareftrSte s ")
    List<SareftrSte> findAll();

}
