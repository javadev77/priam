package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.CatalogueOctav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CatalogueOctavDao extends JpaRepository<CatalogueOctav, Long>{

    @Transactional
    @Query(value ="Select c from CatalogueOctav c where c.ide12 = :ide12 and c.typeCMS = :typeCMS")
    CatalogueOctav findByIde12(@Param("ide12") Long ide12,@Param("typeCMS") String typeCMS);
}
