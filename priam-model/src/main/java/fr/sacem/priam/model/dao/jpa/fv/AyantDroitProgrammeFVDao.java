package fr.sacem.priam.model.dao.jpa.fv;

import fr.sacem.priam.model.domain.dto.AyantDroitDto;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionCMSDto;
import fr.sacem.priam.model.domain.fv.AyantDroitProgramme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AyantDroitProgrammeFVDao extends JpaRepository<AyantDroitProgramme, Long> {

    @Transactional(readOnly = true)
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
            "AND (ayantDroitProgramme.participant like %:participant% OR :participant IS NULL) ")
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

    @Transactional(readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct new fr.sacem.priam.model.domain.dto.KeyValueDto(adp.participant) " +
                    "FROM  " +
                    "AyantDroitProgramme adp " +
                    "WHERE adp.programme.numProg =:programme " +
                    "AND UPPER(adp.participant) like %:participant% " +
                    "ORDER BY adp.participant")
    List<KeyValueDto> findParticipantByNumProg(@Param("participant") String participant, @Param("programme") String programme);

    @Transactional(readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct  new fr.sacem.priam.model.domain.dto.KeyValueDto(adp.ide12) " +
                    "FROM " +
                    "AyantDroitProgramme adp " +
                    "WHERE adp.programme.numProg =:programme " +
                    "AND CONCAT(adp.ide12,'') like %:query% " +
                    "ORDER BY adp.ide12")
    List<KeyValueDto> findIDE12sByProgramme(@Param("query") Long query, @Param("programme") String programme);

    @Transactional(readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct new fr.sacem.priam.model.domain.dto.KeyValueDto(adp.titre) " +
                    "FROM " +
                    "AyantDroitProgramme adp " +
                    "WHERE adp.programme.numProg =:programme " +
                    "AND UPPER(adp.titre) like %:titre% " +
                    "ORDER BY adp.titre")
    List<KeyValueDto> findTitresByProgramme(@Param("titre") String titre, @Param("programme") String programme);

}
