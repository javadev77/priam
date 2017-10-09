package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.saref.SareftjLibtyputil;
import fr.sacem.priam.model.domain.saref.SareftjLibtyputilPK;
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
public interface SareftjLibtyputilDao extends JpaRepository<SareftjLibtyputil, SareftjLibtyputilPK> {
    
    @Cacheable("typeUtilisation")
    @Query("SELECT lib FROM SareftjLibtyputil lib " +
            "WHERE(lib.sareftrTyputil.dateDebut is null OR lib.sareftrTyputil.dateDebut <= CURRENT_DATE) " +
            "AND (lib.sareftrTyputil.dateFin is null OR lib.sareftrTyputil.dateFin >= CURRENT_DATE)" +
            "AND lib.lang = :lang " +
            "AND lib.code IN (:codes) ")
    List<SareftjLibtyputil> findByLang(@Param("lang") String lang, @Param("codes") List<String> codes);
    
    @Cacheable("typeUtilisationByCodeAndLang")
    @Query("SELECT lib " +
           "FROM SareftjLibtyputil AS lib " +
           "WHERE (lib.code is null or lib.code in (:codes)) " +
           "AND (lib.sareftrTyputil.dateDebut is null OR lib.sareftrTyputil.dateDebut <= CURRENT_DATE) " +
           "AND (lib.sareftrTyputil.dateFin is null OR lib.sareftrTyputil.dateFin >= CURRENT_DATE) " +
           "AND  lib.lang = :lang")
    List<SareftjLibtyputil> findByCodeAndLang(@Param("codes") List<String> codes,@Param("lang") String lang);
}
