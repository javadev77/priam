package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.saref.SareftjLibfamiltyputil;
import fr.sacem.priam.model.domain.saref.SareftjLibfamiltyputilPK;
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
public interface LibelleFamilleDao extends JpaRepository<SareftjLibfamiltyputil, SareftjLibfamiltyputilPK> {
    
    //@Cacheable("libelle_familles")
    @Query("SELECT libFam FROM SareftjLibfamiltyputil libFam " +
            "WHERE (libFam.sareftrFamiltyputil.dateDebut is null OR " +
               "DATE_FORMAT(libFam.sareftrFamiltyputil.dateDebut, '%Y-%d-%m') = '0000-00-00' OR "+
               "libFam.sareftrFamiltyputil.dateDebut <= CURRENT_DATE) " +
            "AND (libFam.sareftrFamiltyputil.dateFin is null OR " +
                "DATE_FORMAT(libFam.sareftrFamiltyputil.dateFin, '%Y-%d-%m') = '0000-00-00' OR " +
               "libFam.sareftrFamiltyputil.dateFin >= CURRENT_DATE) " +
            "AND libFam.lang = :lang " +
            "AND libFam.code IN (:familles)")
    List<SareftjLibfamiltyputil> findByLang(@Param("lang") String lang, @Param("familles") List<String> familles);
    
}
