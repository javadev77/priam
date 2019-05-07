package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.saref.SareftrFamiltyputil;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Lazy
@Transactional(value="transactionManager", readOnly = true)
public interface SareftrFamiltyputilDao extends JpaRepository<SareftrFamiltyputil, String> {
    @Query("SELECT libFam FROM SareftrFamiltyputil libFam " +
		   "WHERE (libFam.dateDebut is null OR libFam.dateDebut <= CURRENT_DATE) " +
		   "AND (libFam.dateFin is null OR libFam.dateFin >= CURRENT_DATE) " +
		   "AND libFam.code IN (:familles)")
    List<SareftrFamiltyputil> findByFamilles(@Param("familles") List<String> familles);
    

    @Cacheable("familles")
    @Query("SELECT libFam FROM SareftrFamiltyputil libFam ")
    List<SareftrFamiltyputil> findAll();
}
