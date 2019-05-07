package fr.sacem.priam.model.dao.jpa.cp;

import fr.sacem.priam.model.domain.cp.LigneProgrammeCopyCP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 11/01/2018.
 */
@Transactional(readOnly = true, value = "transactionManager")
public interface LigneProgrammeCopyCPDao extends JpaRepository<LigneProgrammeCopyCP, Long> {

    @Transactional("transactionManager")
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM LigneProgrammeCopyCP lp WHERE lp.fichier.id = :fichierId")
    void deleteAllCopyByFichierId(@Param("fichierId") Long fileId);
}
