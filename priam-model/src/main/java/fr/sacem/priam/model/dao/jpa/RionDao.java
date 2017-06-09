package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Rion;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Lazy
@Transactional(readOnly = true)
public interface RionDao extends JpaRepository<Rion, Integer> {
    
    @Cacheable("rions")
    @Query("SELECT r FROM Rion r " +
            "WHERE r.rion >= :rion")
    List<Rion> findAfterRion(@Param("rion") Integer rion);
    
}
