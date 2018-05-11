package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.domain.catcms.CatalogueRdo;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 03/05/2018.
 */
@Lazy
@Transactional
public interface CatalogueRdoDao extends JpaRepository<CatalogueRdo, Long> {

    @Query(value = "SELECT cat FROM CatalogueRdo cat")
    Page<CatalogueRdo> findByCriteria(Pageable pageable);
}
