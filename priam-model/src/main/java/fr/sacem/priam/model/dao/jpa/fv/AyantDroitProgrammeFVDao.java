package fr.sacem.priam.model.dao.jpa.fv;

import fr.sacem.priam.model.domain.dto.AyantDroitDto;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.fv.AyantDroitProgramme;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AyantDroitProgrammeFVDao extends JpaRepository<AyantDroitProgramme, Long> {

   @Transactional(value="transactionManager",  readOnly = true)
    @Query(value="SELECT new fr.sacem.priam.model.domain.dto.AyantDroitDto("+
            "ayantDroitProgramme.ide12, " +
            "ayantDroitProgramme.titre, " +
            "ayantDroitProgramme.coad, " +
            "ayantDroitProgramme.role, " +
            "ayantDroitProgramme.participant, " +
            "ayantDroitProgramme.points) "+
            "FROM AyantDroitProgramme ayantDroitProgramme " +
            "WHERE ayantDroitProgramme.programme.numProg =:numProg " +
            "AND (ayantDroitProgramme.ide12 = :ide12 OR :ide12 IS NULL) " +
            "AND (ayantDroitProgramme.titre like %:titre% OR :titre IS NULL) " +
            "AND (ayantDroitProgramme.coad = :coad OR :coad IS NULL) " +
            "AND (ayantDroitProgramme.participant like %:participant% OR :participant IS NULL)")
    Page<AyantDroitDto> findCoadByCriteria(@Param("numProg") String numProg,
                                                     @Param("ide12") Long ide12,
                                                     @Param("titre") String titre,
                                                     @Param("coad") Long coad,
                                                     @Param("participant") String participant, Pageable pageable);



    @Query(value =
            "SELECT " +
                    " distinct  new fr.sacem.priam.model.domain.dto.KeyValueDto(adp.coad) " +
                    "FROM " +
                    "AyantDroitProgramme adp " +
                    "WHERE adp.programme.numProg =:numProg " +
                    "AND CONCAT(adp.coad,'') like %:coad% " +
                    "ORDER BY adp.coad")
    List<KeyValueDto> findCoadByNumProg(@Param("coad") Long coad, @Param("numProg") String numProg);

   @Transactional(value="transactionManager",  readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct new fr.sacem.priam.model.domain.dto.KeyValueDto(adp.participant) " +
                    "FROM  " +
                    "AyantDroitProgramme adp " +
                    "WHERE adp.programme.numProg =:programme " +
                    "AND UPPER(adp.participant) like %:participant% " +
                    "ORDER BY adp.participant")
    List<KeyValueDto> findParticipantByNumProg(@Param("participant") String participant, @Param("programme") String programme);

   @Transactional(value="transactionManager",  readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct  new fr.sacem.priam.model.domain.dto.KeyValueDto(adp.ide12) " +
                    "FROM " +
                    "AyantDroitProgramme adp " +
                    "WHERE adp.programme.numProg =:programme " +
                    "AND CONCAT(adp.ide12,'') like %:query% " +
                    "ORDER BY adp.ide12")
    List<KeyValueDto> findIDE12sByProgramme(@Param("query") Long query, @Param("programme") String programme);

   @Transactional(value="transactionManager",  readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct new fr.sacem.priam.model.domain.dto.KeyValueDto(adp.titre) " +
                    "FROM " +
                    "AyantDroitProgramme adp " +
                    "WHERE adp.programme.numProg =:programme " +
                    "AND UPPER(adp.titre) like %:titre% " +
                    "ORDER BY adp.titre")
    List<KeyValueDto> findTitresByProgramme(@Param("titre") String titre, @Param("programme") String programme);

   @Transactional(value="transactionManager",  readOnly = true)
    @Query(value="SELECT sum(ayantDroitProgramme.points) as points "+
            "FROM AyantDroitProgramme ayantDroitProgramme " +
            "WHERE ayantDroitProgramme.programme.numProg =:numProg " +
            "AND (ayantDroitProgramme.ide12 = :ide12 OR :ide12 IS NULL) " +
            "AND (ayantDroitProgramme.titre like %:titre% OR :titre IS NULL) " +
            "AND (ayantDroitProgramme.coad = :coad OR :coad IS NULL) " +
            "AND (ayantDroitProgramme.participant like %:participant% OR :participant IS NULL) ")
    Double calculerPointsByCriteria(@Param("numProg") String numProg,
                                    @Param("ide12") Long ide12,
                                    @Param("titre") String titre,
                                    @Param("coad") Long coad,
                                    @Param("participant") String participant);

   @Transactional(value="transactionManager",  readOnly = true)
    @Query(value="SELECT new fr.sacem.priam.model.domain.dto.AyantDroitDto("+
        "ayantDroitProgramme.coad, " +
        "ayantDroitProgramme.participant, " +
        "sum(ayantDroitProgramme.points)) "+
        "FROM AyantDroitProgramme ayantDroitProgramme " +
        "WHERE ayantDroitProgramme.programme.numProg =:numProg " +
        "AND (ayantDroitProgramme.coad = :coad OR :coad IS NULL) " +
        "AND (ayantDroitProgramme.participant like %:participant% OR :participant IS NULL) " +
        "GROUP BY ayantDroitProgramme.coad")
    Page<AyantDroitDto> findByCumulCoad(@Param("numProg") String numProg, @Param("coad") Long coad, @Param("participant") String participant, Pageable pageable);


    @Transactional(value="transactionManager",  readOnly = true)
    @Query(nativeQuery = true, value="SELECT sum(temp.points) " +
        "FROM (SELECT sum(ayantDroitProgramme.points) as points "+
        "FROM priam_app.AYANT_DROIT_PROGRAMME_VIEW ayantDroitProgramme " +
        "WHERE ayantDroitProgramme.NUMPROG =:numProg " +
        "AND (ayantDroitProgramme.COAD = :coad OR :coad IS NULL) " +
        "AND (ayantDroitProgramme.participant like %:participant% OR :participant IS NULL) " +
        "GROUP BY ayantDroitProgramme.COAD) as temp")
    Double calculerPointsByCumulCoad(@Param("numProg") String numProg,
                                     @Param("coad") Long coad,
                                     @Param("participant") String participant);

}
