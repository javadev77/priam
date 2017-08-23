package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.LibelleTypeUtilisation;
import fr.sacem.priam.model.domain.Territoire;
import fr.sacem.priam.model.domain.TerritoirePK;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by belwidanej on 21/08/2017.
 */
@Lazy
@Transactional(readOnly = true)
public interface TerritoireDao extends JpaRepository<Territoire, TerritoirePK> {

    @Cacheable("territoire")
    @Query("SELECT territoire FROM Territoire territoire " +
            "WHERE territoire.cdeLng = :lang " +
            "ORDER BY territoire.cdePaysIso4N")
    List<Territoire> findByLang(@Param("lang") String lang);

    @Cacheable("territoireByCodeAndLang")
    @Query("SELECT territoire FROM Territoire territoire " +
            "WHERE territoire.cdePaysIso4N in (:codes) "+
            "AND territoire.cdeLng = :lang")
    List<Territoire> findByCodeAndLang(@Param("codes") List<Integer> codes, @Param("lang") String lang);
}
