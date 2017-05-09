package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Repository
public interface FichierDao extends JpaRepository<Fichier, Long> {
    
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT f FROM Fichier AS f " +
            "ORDER BY f.nom ASC")
    List<Fichier> rechercher();
}
