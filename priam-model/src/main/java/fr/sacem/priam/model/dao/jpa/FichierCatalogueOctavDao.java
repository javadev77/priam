package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.FichierCatalogueOctav;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 04/01/2018.
 */
public interface FichierCatalogueOctavDao extends JpaRepository<FichierCatalogueOctav, Long> {

    @Transactional(readOnly = true, value = "transactionManager")
    @Query(value="SELECT f " +
            "FROM FichierCatalogueOctav f "+
            "ORDER BY f.dateDebutChargt DESC ")
    Page<FichierCatalogueOctav> findLastFichierOctav(Pageable pageable);
}
