package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.LibelleUtilisateur;
import fr.sacem.priam.model.domain.SareftjLibUtilPK;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by benmerzoukah on 06/09/2017.
 */
public interface SarefLibelleUtilisateurDao extends JpaRepository<LibelleUtilisateur, SareftjLibUtilPK> {
    
    @Cacheable("sarefLibelleUtilisateur")
    @Query("SELECT libUtil FROM LibelleUtilisateur libUtil " +
		   "WHERE libUtil.cdeLng = :lang")
    List<LibelleUtilisateur> findByLang(@Param("lang") String lang);
}
