package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.saref.SareftrFamiltyputil;
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
public interface SareftrFamiltyputilDao extends JpaRepository<SareftrFamiltyputil, String> {
    //@Cacheable("familles")
    @Query("SELECT libFam FROM SareftrFamiltyputil libFam " +
		   "WHERE (libFam.dateDebut is null OR libFam.dateDebut <= CURRENT_DATE) " +
		   "AND (libFam.dateFin is null OR libFam.dateFin >= CURRENT_DATE) " +
		   "AND libFam.code IN (:familles)")
    List<SareftrFamiltyputil> findByFamilles(@Param("familles") List<String> familles);
    
    /*@Cacheable("familles")
    @Query("SELECT libFam FROM SareftrFamiltyputil libFam " +
		   "WHERE (libFam.sareftrFamiltyputil.dateDebut is not null AND libFam.sareftrFamiltyputil.dateDebut <= CURRENT_DATE) " +
		   "AND (libFam.sareftrFamiltyputil.dateFin is null OR libFam.sareftrFamiltyputil.dateFin >= CURRENT_DATE)" +
		   "AND libFam.lang = :lang " +
		   "AND libFam.code IN (familles)")
    List<SareftrFamiltyputil> findByFamilles(@Param("lang") String lang, List<FamillePriam> familles);*/
}
