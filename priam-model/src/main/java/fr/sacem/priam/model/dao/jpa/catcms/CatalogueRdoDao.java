package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.domain.catcms.CatalogueRdo;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by benmerzoukah on 03/05/2018.
 */
@Lazy
@Transactional
public interface CatalogueRdoDao extends JpaRepository<CatalogueRdo, Long> {

    /*@Query(value = "SELECT cat FROM CatalogueRdo cat")
    Page<CatalogueRdo> findByCriteria(Pageable pageable);*/

    @Query(value = "SELECT cat FROM CatalogueRdo cat where cat.typeCMS= :typeCMS " +
            "AND (CONCAT(cat.ide12, '') LIKE %:ide12% OR :ide12 IS NULL) " +
            "AND (cat.titre LIKE %:titre% OR :titre IS NULL) " +
            "AND (cat.participant LIKE %:participant% OR :participant IS NULL) " +
            "AND (cat.dateEntree >= :periodeEntreeDateDebut or :periodeEntreeDateDebut IS NULL)" +
            "AND (cat.dateEntree <= :periodeEntreeDateFin or :periodeEntreeDateFin IS NULL) " +
            "AND (cat.dateRenouvellement >= :periodeRenouvellementDateDebut or :periodeRenouvellementDateDebut IS NULL) " +
            "AND (cat.dateRenouvellement <= :periodeRenouvellementDateFin or :periodeRenouvellementDateFin IS NULL) " +
            "AND (cat.dateSortie >= :periodeSortieDateDebut or :periodeSortieDateDebut IS NULL) " +
            "AND (cat.dateSortie <= :periodeSortieDateFin or :periodeSortieDateFin IS NULL) ")
    Page<CatalogueRdo> findByCriteriaWithNonEligible(@Param("typeCMS") String typeCMS,
                                      @Param("ide12") String ide12,
                                      @Param("titre") String titre,
                                      @Param("participant") String participant,
                                      @Param("periodeEntreeDateDebut") Date periodeEntreeDateDebut,
                                      @Param("periodeEntreeDateFin") Date periodeEntreeDateFin,
                                      @Param("periodeRenouvellementDateDebut") Date periodeRenouvellementDateDebut,
                                      @Param("periodeRenouvellementDateFin") Date periodeRenouvellementDateFin,
                                      @Param("periodeSortieDateDebut") Date periodeSortieDateDebut,
                                      @Param("periodeSortieDateFin") Date periodeSortieDateFin, Pageable pageable);


    @Query(value = "SELECT cat FROM CatalogueRdo cat where cat.typeCMS= :typeCMS " +
            "AND (CONCAT(cat.ide12, '') LIKE %:ide12% OR :ide12 IS NULL) " +
            "AND (cat.titre LIKE %:titre% OR :titre IS NULL) " +
            "AND (cat.participant LIKE %:participant% OR :participant IS NULL) " +
            "AND (cat.dateEntree >= :periodeEntreeDateDebut or :periodeEntreeDateDebut IS NULL)" +
            "AND (cat.dateEntree <= :periodeEntreeDateFin or :periodeEntreeDateFin IS NULL) " +
            "AND (cat.dateRenouvellement >= :periodeRenouvellementDateDebut or :periodeRenouvellementDateDebut IS NULL) " +
            "AND (cat.dateRenouvellement <= :periodeRenouvellementDateFin or :periodeRenouvellementDateFin IS NULL) " +
            "AND (cat.dateSortie IS NULL OR cat.dateSortie >= :periodeSortieDateFin) ")
    Page<CatalogueRdo> findByCriteriaWithoutNonEligible(@Param("typeCMS") String typeCMS,
                                                        @Param("ide12") String ide12,
                                                        @Param("titre") String titre,
                                                        @Param("participant") String participant,
                                                        @Param("periodeEntreeDateDebut") Date periodeEntreeDateDebut,
                                                        @Param("periodeEntreeDateFin") Date periodeEntreeDateFin,
                                                        @Param("periodeRenouvellementDateDebut") Date periodeRenouvellementDateDebut,
                                                        @Param("periodeRenouvellementDateFin") Date periodeRenouvellementDateFin,
                                                        @Param("periodeSortieDateFin") Date periodeSortieDateFin, Pageable pageable);


}
