package fr.sacem.priam.model.dao.jpa.fv;

import fr.sacem.priam.model.domain.fv.EnrichissementLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EnrichissementLogDao extends JpaRepository<EnrichissementLog, Long> {

    @Transactional(value="transactionManager",  readOnly =true)
    @Query(value="SELECT e from EnrichissementLog AS e WHERE e.fichier.id =:idFichier")
    Page<EnrichissementLog> getEnrichissementLog(@Param("idFichier") Long idFichier, Pageable pageable);

}
