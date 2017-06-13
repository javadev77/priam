package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Rion;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fandis on 12/06/2017.
 */
@Lazy
@Transactional(readOnly = true)
public interface RionDao extends JpaRepository<Rion, Integer> {

    @Cacheable("rions")
    @Query("SELECT rion FROM PRIAM_RION rion "+
            "WHERE  (rion.datcre is null OR rion.datcre >= CURRENT_DATE)" +
            "AND (rion.datglmt is null OR rion.datglmt >= CURRENT_DATE)")
    List<Rion> getAllRions();
}


