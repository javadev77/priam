package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.LibelleFamille;
import fr.sacem.priam.model.domain.LibelleFamillePK;
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
public interface LibelleFamilleDao extends JpaRepository<LibelleFamille, LibelleFamillePK> {
    
    @Cacheable("familles")
    @Query("SELECT libFam FROM LibelleFamille libFam " +
            "WHERE libFam.famille.dateDebut is not null " +
            "AND (libFam.famille.dateFin is null OR libFam.famille.dateFin >= CURRENT_DATE)" +
            "AND libFam.lang = :lang")
    List<LibelleFamille> findByLang(@Param("lang") String lang);
    
}
