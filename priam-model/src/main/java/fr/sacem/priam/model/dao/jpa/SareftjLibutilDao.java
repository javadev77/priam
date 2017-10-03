package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.saref.SareftjLibutil;
import fr.sacem.priam.model.domain.saref.SareftjLibUtilPK;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by benmerzoukah on 06/09/2017.
 */
public interface SareftjLibutilDao extends JpaRepository<SareftjLibutil, SareftjLibUtilPK> {
    
    @Cacheable("sarefLibelleUtilisateur")
    @Query("SELECT libUtil FROM SareftjLibutil libUtil " +
		   "WHERE libUtil.cdeLng = :lang")
    List<SareftjLibutil> findByLang(@Param("lang") String lang);
}
