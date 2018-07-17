package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by benmerzoukah on 03/05/2018.
 */
@Lazy
@Transactional
public interface CatalogueCmsDao extends JpaRepository<CatalogueCms, Long> {

    /*@Query(value = "SELECT cat FROM CatalogueCms cat")
    Page<CatalogueCms> findByCriteria(Pageable pageable);*/

    @Query(value = "SELECT DISTINCT cat FROM CatalogueCms cat " +
    //@Query(value = "SELECT DISTINCT cat FROM CatalogueCms cat " +
            "where cat.typeCMS= :typeCMS " +
            "AND (CONCAT(cat.ide12, '') LIKE %:ide12% OR :ide12 IS NULL) " +
            "AND (cat.titre LIKE %:titre% OR :titre IS NULL) " +
            "AND (cat.participants LIKE %:participant% OR :participant IS NULL) " +
            "AND (cat.dateEntree >= :periodeEntreeDateDebut or :periodeEntreeDateDebut IS NULL)" +
            "AND (cat.dateEntree <= :periodeEntreeDateFin or :periodeEntreeDateFin IS NULL) " +
            "AND (cat.dateRenouvellement >= :periodeRenouvellementDateDebut or :periodeRenouvellementDateDebut IS NULL) " +
            "AND (cat.dateRenouvellement <= :periodeRenouvellementDateFin or :periodeRenouvellementDateFin IS NULL) " +
            "AND (cat.dateSortie >= :periodeSortieDateDebut or :periodeSortieDateDebut IS NULL) " +
            "AND (cat.dateSortie <= :periodeSortieDateFin or :periodeSortieDateFin IS NULL) ")
           // "GROUP BY cat.ide12, cat.typeCMS ")
    Page<CatalogueCms> findByCriteriaWithNonEligible(@Param("typeCMS") String typeCMS,
                                                     @Param("ide12") String ide12,
                                                     @Param("titre") String titre,
                                                     @Param("participant") String participant,
                                                     @Param("periodeEntreeDateDebut") Date periodeEntreeDateDebut,
                                                     @Param("periodeEntreeDateFin") Date periodeEntreeDateFin,
                                                     @Param("periodeRenouvellementDateDebut") Date periodeRenouvellementDateDebut,
                                                     @Param("periodeRenouvellementDateFin") Date periodeRenouvellementDateFin,
                                                     @Param("periodeSortieDateDebut") Date periodeSortieDateDebut,
                                                     @Param("periodeSortieDateFin") Date periodeSortieDateFin, Pageable pageable);


    @Query(value = "SELECT DISTINCT cat FROM CatalogueCms cat " +
    // @Query(value = "SELECT DISTINCT cat FROM CatalogueCms cat " +
            "where cat.typeCMS= :typeCMS " +
            "AND (CONCAT(cat.ide12, '') LIKE %:ide12% OR :ide12 IS NULL) " +
            "AND (cat.titre LIKE %:titre% OR :titre IS NULL) " +
            "AND (cat.participants LIKE %:participant% OR :participant IS NULL) " +
            "AND (cat.dateEntree >= :periodeEntreeDateDebut or :periodeEntreeDateDebut IS NULL)" +
            "AND (cat.dateEntree <= :periodeEntreeDateFin or :periodeEntreeDateFin IS NULL) " +
            "AND (cat.dateRenouvellement >= :periodeRenouvellementDateDebut or :periodeRenouvellementDateDebut IS NULL) " +
            "AND (cat.dateRenouvellement <= :periodeRenouvellementDateFin or :periodeRenouvellementDateFin IS NULL) " +
            "AND (cat.dateSortie IS NULL OR cat.dateSortie >= :periodeSortieDateFin) " )
            //"GROUP BY cat.ide12, cat.typeCMS ")
    Page<CatalogueCms> findByCriteriaWithoutNonEligible(@Param("typeCMS") String typeCMS,
                                                        @Param("ide12") String ide12,
                                                        @Param("titre") String titre,
                                                        @Param("participant") String participant,
                                                        @Param("periodeEntreeDateDebut") Date periodeEntreeDateDebut,
                                                        @Param("periodeEntreeDateFin") Date periodeEntreeDateFin,
                                                        @Param("periodeRenouvellementDateDebut") Date periodeRenouvellementDateDebut,
                                                        @Param("periodeRenouvellementDateFin") Date periodeRenouvellementDateFin,
                                                        @Param("periodeSortieDateFin") Date periodeSortieDateFin, Pageable pageable);

    @Transactional
    @Query(value ="Select c from CatalogueCms c where c.ide12 = :ide12 and c.typeCMS = :typeCMS")
    CatalogueCms findByIde12AndTypeCMS (@Param("ide12") Long ide12,@Param("typeCMS") String typeCMS);

    @Transactional
    @Query(value ="Select c from CatalogueCms c where c.ide12 = :ide12 and c.typeCMS = :typeCMS and (c.dateSortie is null or c.dateSortie >= current_date)")
    CatalogueCms findOeuvreExistanteCatalogueByIde12AndTypeCMS (@Param("ide12") Long ide12,@Param("typeCMS") String typeCMS);


}
