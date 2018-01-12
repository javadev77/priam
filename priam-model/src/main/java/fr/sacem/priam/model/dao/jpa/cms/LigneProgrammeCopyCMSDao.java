package fr.sacem.priam.model.dao.jpa.cms;

import fr.sacem.priam.model.domain.cms.LigneProgrammeCopyCMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 11/01/2018.
 */
@Transactional(readOnly = true)
public interface LigneProgrammeCopyCMSDao extends JpaRepository<LigneProgrammeCopyCMS, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM LigneProgrammeCopyCMS lp WHERE lp.fichier.id = :fichierId")
    void deleteAllCopyByFichierId(@Param("fichierId") Long fileId);
}
