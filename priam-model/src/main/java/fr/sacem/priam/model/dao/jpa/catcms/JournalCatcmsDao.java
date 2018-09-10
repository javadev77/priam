package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.domain.catcms.JournalCatcms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by embouazzar on 09/08/2018.
 */
@Transactional
public interface JournalCatcmsDao extends JpaRepository<JournalCatcms, Long>{
    @Query(value = "SELECT j FROM JournalCatcms j WHERE " +
            "j.typeCMS = :typeCMS " +
            "AND (CONCAT(j.ide12, '') LIKE %:ide12% OR :ide12 IS NULL)" +
            "AND (j.evenement = :typeEvenement or :typeEvenement IS NULL)" +
            "AND (j.date >= :periodeDebutEvenement or :periodeDebutEvenement IS NULL)" +
            "AND (j.date <= :periodeFinEvenement or :periodeFinEvenement IS NULL) ")
    Page<JournalCatcms> findByCriteria(@Param("typeCMS") String typeCMS,
                                       @Param("ide12") String ide12,
                                       @Param("typeEvenement") String typeEvenement,
                                       @Param("periodeDebutEvenement") Date periodeDebutEvenement,
                                       @Param("periodeFinEvenement") Date periodeFinEvenement, Pageable pageable);
}
