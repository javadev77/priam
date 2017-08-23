package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.LignePreprep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by benmerzoukah on 21/08/2017.
 */
public interface LignePreprepDao extends JpaRepository<LignePreprep, Long> {
    
    @Transactional(readOnly = true)
    @Query(value="SELECT lp " +
			   "FROM LignePreprep lp "+
			   "WHERE lp.numProg = :numProg " +
			   "ORDER BY lp.cdeCisac, " +
			   "lp.cdeTer, " +
			   "lp.rionEffet, " +
			   "lp.cdeFamilTypUtil, " +
			   "lp.cdeTypUtil, " +
			   "lp.cdeUtil, " +
			   "lp.cdeModFac, " +
			   "lp.cdeTypProg, " +
			   "lp.cdeCompl, " +
			   "lp.datDbtProg, " +
			   "lp.datFinProg")
    List<LignePreprep> findByNumProg(@Param("numProg") String numProg);
}
