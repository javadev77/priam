package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.domain.catcms.ParticipantsCatcms;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by benmerzoukah on 25/06/2018.
 */
@Lazy
@Transactional("transactionManager")
public interface ParticipantsCatcmsDao extends JpaRepository<ParticipantsCatcms, Long> {

    @Query(value = "SELECT part FROM ParticipantsCatcms part " +
            "WHERE part.ide12=:ide12 AND part.typeCMS=:typeCMS ")
    List<ParticipantsCatcms> findParticipantsCatcmsByIde12AndTypeCMS(@Param("ide12") Long ide12,
                                                                            @Param("typeCMS") String typeCMS);
}
