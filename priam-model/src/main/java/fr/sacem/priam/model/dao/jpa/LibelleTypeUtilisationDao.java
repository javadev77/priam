package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.LibelleTypeUtilisation;
import fr.sacem.priam.model.domain.LibelleTypeUtilisationPK;
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
public interface LibelleTypeUtilisationDao extends JpaRepository<LibelleTypeUtilisation, LibelleTypeUtilisationPK> {
    
    @Cacheable("typeUtilisation")
    List<LibelleTypeUtilisation> findByLang(String lang);
    
    @Cacheable("typeUtilisationByCodeAndLang")
    @Query("SELECT lib " +
           "FROM LibelleTypeUtilisation AS lib " +
           "WHERE lib.code in (:codes) " +
           "AND  lib.lang = :lang")
    List<LibelleTypeUtilisation> findByCodeAndLang(@Param("codes") List<String> codes,@Param("lang") String lang);
}
