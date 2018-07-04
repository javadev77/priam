package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.FichierFelix;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 22/09/2017.
 */
@Lazy
public interface FichierFelixDao extends JpaRepository<FichierFelix, Long> {
    
    @Transactional(readOnly = true)
    @Query(value = "SELECT f FROM FichierFelix f where f.numProg = :numProg")
    FichierFelix findByNumprog(@Param("numProg") String numProg);
}
