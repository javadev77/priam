package fr.sacem.priam.model.dao.jpa.cms;

import fr.sacem.priam.model.domain.cms.TraitementEligibiliteCMS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by benmerzoukah on 14/12/2017.
 */
public interface TraitementEligibiliteCMSDao extends JpaRepository<TraitementEligibiliteCMS, Long> {

    @Transactional(value="transactionManager")
    @Query(value="SELECT l " +
            "FROM TraitementEligibiliteCMS l join l.programme as p "+
            "WHERE p.numProg = :numProg " +
            "AND l.statutEligibilite = 'FIN_ELIGIBILITE' " +
            "ORDER BY l.dateFinTmt DESC ")
    Page<TraitementEligibiliteCMS> findFinishedTmtByNumProg(@Param("numProg") String numProg, Pageable pageable);
}
