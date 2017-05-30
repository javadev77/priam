package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.LigneProgramme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by benmerzoukah on 29/05/2017.
 */
@Transactional(readOnly = true)
public interface LigneProgrammeDao extends JpaRepository<LigneProgramme, Long> {
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM LigneProgramme lp WHERE lp.fichierId = :fichierId")
    void deleteAllByFichierId(@Param("fichierId") Long fileId);
    
    List<LigneProgramme> findByFichierId(Long fileId);
}
