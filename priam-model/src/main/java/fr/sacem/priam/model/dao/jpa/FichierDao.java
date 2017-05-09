package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Fichier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Lazy
public interface FichierDao extends JpaRepository<Fichier, Long> {
    
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT f FROM Fichier AS f " +
            "ORDER BY f.nom ASC")
   Page<Fichier> findAll(Pageable pageable);
}
