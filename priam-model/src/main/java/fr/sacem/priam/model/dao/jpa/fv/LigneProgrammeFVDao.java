package fr.sacem.priam.model.dao.jpa.fv;

import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionCMSDto;
import fr.sacem.priam.model.domain.fv.LigneProgrammeFV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//

public interface LigneProgrammeFVDao extends JpaRepository<LigneProgrammeFV, Long> {

    @Transactional(readOnly = true, value = "transactionManager")
    @Query(value =
            "SELECT " +
                    " distinct  new fr.sacem.priam.model.domain.dto.KeyValueDto(l.ide12) " +
                    "FROM " +
                    "LigneProgrammeFV l " +
                    "WHERE " +
                    "l.fichier in (" +
                    " select f.id from Fichier f where f.programme.numProg like :programme" +
                    ")" +
                    "AND CONCAT(l.ide12,'') like %:query% " +
                    "ORDER BY l.ide12")
    List<KeyValueDto> findIDE12sByProgramme(@Param("query") Long query, @Param("programme") String programme);

    @Transactional(readOnly = true, value = "transactionManager")
    @Query(value =
            "SELECT " +
                    " distinct new fr.sacem.priam.model.domain.dto.KeyValueDto(l.titreOeuvre) " +
                    "FROM " +
                    "LigneProgrammeFV l " +
                    "WHERE " +
                    "l.fichier in (" +
                    " select f.id from Fichier f where f.programme.numProg like :programme" +
                    ")" +
                    "AND UPPER(l.titreOeuvre) like %:titre% " +
                    "ORDER BY l.titreOeuvre")
    List<KeyValueDto> findTitresByProgramme(@Param("titre") String titre, @Param("programme") String programme);

    @Transactional(readOnly = true, value = "transactionManager")
    @Query(value="SELECT new fr.sacem.priam.model.domain.dto.SelectionCMSDto("+
            "ligneProgramme.ide12, " +
            "ligneProgramme.titreOeuvre, " +
            "ligneProgramme.roleParticipant1, " +
            "ligneProgramme.nomParticipant1, " +
            "ligneProgramme.ajout, " +
            "ligneProgramme.selectionEnCours, " +
            "(CASE WHEN f.programme.typeUtilisation.code = 'FD06' THEN ligneProgramme.mtEdit WHEN f.programme.typeUtilisation.code = 'FD12' THEN ligneProgramme.nbrDifEdit ELSE 0 END)) "+
            "FROM LigneProgrammeFV ligneProgramme join ligneProgramme.fichier  f " +
            "WHERE ligneProgramme.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND (ligneProgramme.ide12 = :ide12 OR :ide12 IS NULL) " +
            "AND (ligneProgramme.ajout = :ajout OR :ajout IS NULL) " +
            "AND (ligneProgramme.selectionEnCours = :selectionEnCours OR :selectionEnCours IS NULL) " +
            "AND (ligneProgramme.titreOeuvre = :titre OR :titre IS NULL) " +
            "AND (ligneProgramme.oeuvreManuel IS NULL) ")
    Page<SelectionCMSDto> findLigneProgrammeByCriteria(@Param("numProg") String numProg,
                                                       @Param("ide12") Long ide12,
                                                       @Param("titre") String titre,
                                                       @Param("ajout") String ajout,
                                                       @Param("selectionEnCours") Boolean selectionEnCours, Pageable pageable);

    @Transactional(readOnly = true, value = "transactionManager")
    @Query(nativeQuery = true, value =
            "SELECT " +
                    "count(points), ajout" +
                    " from ( " +
                    "       SELECT " +
                    "(CASE WHEN l.cdeTypUtil = 'FD06' THEN count(l.mt)  WHEN l.cdeTypUtil = 'FD12' THEN count(l.nbrDif) ELSE 0 END) points, " +
                    "          l.ide12, l.ajout " +
                    "       FROM " +
                    "           PRIAM_LIGNE_PROGRAMME_FV l " +
                    "       inner join " +
                    "           PRIAM_FICHIER as f on l.ID_FICHIER=f.ID " +
                    "       WHERE " +
                    "           f.numProg = ?1 " +
                    "AND (?2 IS NULL OR l.SEL_EN_COURS = ?2) " +
                    "AND l.idOeuvreManuel IS NULL " +
                    "       GROUP BY " +
                    "           l.ide12, l.ajout" +
                    "       ) result " +
                    "GROUP BY ajout")
    List<Object> compterOuvres(@Param("numProg") String numProg, @Param("selection") Integer selection);

    @Transactional(readOnly = true, value = "transactionManager")
    @Query(nativeQuery = true, value =
            "SELECT sum(points) from ( SELECT " +
                    "(CASE WHEN l.cdeTypUtil = 'FD06' THEN sum(l.mtEdit) WHEN l.cdeTypUtil = 'FD12' THEN sum(l.nbrDifEdit) ELSE 0 END) points, " +
                    "l.ide12 " +
                    "FROM " +
                    "PRIAM_LIGNE_PROGRAMME_FV l inner join PRIAM_FICHIER as f " +
                    "on l.ID_FICHIER=f.ID " +
                    "WHERE " +
                    "f.numProg = ?1 " +
                    "AND (?2 IS NULL OR l.SEL_EN_COURS = ?2) " +
                    "AND l.idOeuvreManuel IS NULL " +
                    "GROUP BY l.ide12) result ")
    Double calculerPointsMontantOeuvres(@Param("numProg") String numProg, @Param("selection") Integer selection);

    @Modifying(clearAutomatically = true)
    @Transactional(value="transactionManager")
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME_FV p " +
            "INNER JOIN " +
            "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set " +
            "  p.SEL_EN_COURS=?3 " +
            "where " +
            "  f.NUMPROG = ?1" +
            " AND p.ide12 = ?2")
    void updateSelectionTemporaireByNumProgramme(@Param("numProg") String numProg,
                                                 @Param("ide12") Long ide12,
                                                 @Param("select") int select);

    @Modifying(clearAutomatically = true)
    @Transactional(value="transactionManager")
    @Query(nativeQuery = true, value = "update " +
            "PRIAM_LIGNE_PROGRAMME_FV p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set  p.selection=?2 " +
            "WHERE  "+
            "f.NUMPROG = ?1 AND p.SEL_EN_COURS=?2")
    void updateSelection(@Param("numProg") String numProg, @Param("selection") boolean value);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update " +
            "PRIAM_LIGNE_PROGRAMME_FV p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set  p.mt=p.mtEdit " +
            "WHERE  "+
            "f.NUMPROG = ?1 " +
            "AND p.SEL_EN_COURS=1 " +
            "AND p.idOeuvreManuel is NULL " +
            "AND p.ajout='MANUEL' OR p.ajout='CORRIGE' ")
    void updatePointsMt(String numProg);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update " +
            "PRIAM_LIGNE_PROGRAMME_FV p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set  p.nbrDif=p.nbrDifEdit " +
            "WHERE  "+
            "f.NUMPROG = ?1 " +
            "AND p.SEL_EN_COURS=1 " +
            "AND p.idOeuvreManuel is NULL " +
            "AND p.ajout='MANUEL' OR p.ajout='CORRIGE' ")
    void updatePoints(String numProg);

    @Query(value="SELECT l " +
            "FROM LigneProgrammeFV l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND l.ide12 = :ide12 " +
            "AND l.oeuvreManuel IS NULL " +
            "AND l.ajout = 'AUTOMATIQUE' ")
    List<LigneProgrammeFV> findOeuvresAutoByIde12AndCdeUtil(@Param("numProg") String numProg, @Param("ide12") Long ide12);

    @Query(value="SELECT l " +
            "FROM LigneProgrammeFV l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND l.ide12 = :ide12 " +
            "AND l.oeuvreManuel IS NULL " +
            "AND l.ajout = 'CORRIGE' ")
    LigneProgrammeFV findOeuvreCorrigeByIde12(@Param("numProg") String numProg, @Param("ide12") Long ide12);

    @Query(value="SELECT l " +
            "FROM LigneProgrammeFV l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND l.ide12 = :ide12 " +
            "AND l.oeuvreManuel IS NOT NULL " +
            "AND l.ajout = 'AUTOMATIQUE' ")
    List<LigneProgrammeFV> findOeuvresAutoLinkCorrigeByIde12AndCdeUtil(@Param("numProg") String numProg, @Param("ide12") Long ide12);

    @Query(value="SELECT l " +
            "FROM LigneProgrammeFV l " +
            "WHERE l.oeuvreManuel.id = :idOeuvreManuel  " +
            "AND l.ajout = 'AUTOMATIQUE' ")
    List<LigneProgrammeFV> findOeuvresAutoByIdOeuvreManuel(@Param("idOeuvreManuel") Long idOeuvreManuel);

    @Query(value="SELECT l " +
            "FROM LigneProgrammeFV l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND l.ide12 = :ide12 " +
            "AND l.oeuvreManuel IS NULL " +
            "AND l.ajout = 'MANUEL' ")
    LigneProgrammeFV findOeuvreManuelByIde12AndCdeUtil(@Param("numProg")String numProg, @Param("ide12") Long ide12);

    @Modifying(clearAutomatically = true)
    @Transactional(value="transactionManager")
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME_FV p " +
            "INNER JOIN " +
            "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set " +
            "  p.mtEdit=?3 "+
            "where " +
            "  f.NUMPROG = ?1 " +
            " AND p.ide12 = ?2 ")
    void updatePointsMtTemporaireByNumProgramme(String numProg, Long ide12, Double mtEdit);

    @Modifying(clearAutomatically = true)
    @Transactional(value="transactionManager")
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME_FV p " +
            "INNER JOIN " +
            "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set " +
            "  p.nbrDifEdit=?3 "+
            "where " +
            "  f.NUMPROG = ?1 " +
            " AND p.ide12 = ?2 ")
    void updatePointsTemporaireByNumProgramme(String numProg, Long ide12, Long nbrDifEdit);

    @Query(value="SELECT l " +
            "FROM LigneProgrammeFV l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND l.ide12 = :ide12 " +
            "AND l.oeuvreManuel IS NULL " +
            "AND l.ajout = 'MANUEL' ")
    LigneProgrammeFV findOeuvreManuelByIde12(@Param("numProg") String numProg, @Param("ide12") Long ide12);

    @Query(value="SELECT l " +
            "FROM LigneProgrammeFV l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND l.selection = 0 " +
            "AND l.ajout = 'CORRIGE' ")
    List<LigneProgrammeFV> findOeuvresManuelsEnCoursEdition(@Param("numProg") String numProg);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update " +
            "PRIAM_LIGNE_PROGRAMME_FV p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set  p.SEL_EN_COURS=?2 " +
            "WHERE  "+
            "f.NUMPROG = ?1 AND p.selection=?2")
    void updateSelectionTemporaire(@Param("numProg") String numProg, @Param("selection") boolean value);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update " +
            "PRIAM_LIGNE_PROGRAMME_FV p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set  p.mtEdit=p.mt " +
            "WHERE  "+
            "f.NUMPROG = ?1 AND p.selection=?2")
    void updatePointsMtTemporaire(String numProg, Boolean aTrue);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update " +
            "PRIAM_LIGNE_PROGRAMME_FV p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set  p.nbrDifEdit=p.nbrDif " +
            "WHERE  "+
            "f.NUMPROG = ?1 AND p.selection=?2")
    void updateNbrDifTemporaire(String numProg, Boolean aFalse);

    @Query(value="SELECT l " +
            "FROM LigneProgrammeFV l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND l.ajout = 'MANUEL' " +
            "OR l.ajout = 'CORRIGE' ")
    List<LigneProgrammeFV> findAllOeuvresManuelsByNumProg(@Param("numProg") String numProg);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME_FV p " +
            "INNER JOIN " +
            "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set " +
            "  p.SEL_EN_COURS =?2 " +
            "where " +
            "  f.NUMPROG = ?1")
    void updateSelectionTemporaireByNumProgramme(@Param("numProg") String numProg, @Param("selectionEnCours") boolean selectionEnCours);


    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value="update " +
        "  PRIAM_LIGNE_PROGRAMME_FV p " +
        "INNER JOIN " +
        "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
        "set " +
        "  p.selection =?2 " +
        "where " +
        "  f.NUMPROG = ?1")
    void deselectAllByNumProgramme(@Param("numProg") String numProg, @Param("selection") boolean selection);

    @Transactional(value="transactionManager")
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM LigneProgrammeFV lp WHERE lp.fichier.id = :fichierId")
    void deleteAllByFichierId(@Param("fichierId") Long fileId);

    @Transactional(readOnly = true, value = "transactionManager")
    @Query(nativeQuery = true, value = "SELECT COUNT(*) AS NB_LIGNES FROM EXPORT_FV_VIEW FV " +
            "WHERE FV.numProg=:numProg ")
    Long countNbLignesForExport(@Param("numProg") Long numProg);

    @Query(value = "SELECT fv from LigneProgrammeFV fv where fv.ide12 =:ide12 and fv.fichier.id =:idFichier ")
    LigneProgrammeFV findOeuvreByIde12(@Param("ide12") Long ide12, @Param("idFichier") Long idFichier);

    @Query(value="SELECT ligneProgramme.ide12 " +
            "FROM LigneProgrammeFV ligneProgramme inner join ligneProgramme.fichier  f " +
            "WHERE ligneProgramme.fichier.id = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND (ligneProgramme.selectionEnCours = :selectionEnCours OR :selectionEnCours IS NULL) " +
            "AND (ligneProgramme.selection = :selection OR :selection IS NULL) " +
            "AND (ligneProgramme.oeuvreManuel IS NULL) ")
    List<Long> findLigneProgrammePreselected(@Param("numProg") String numProg,
                                             @Param("selectionEnCours") Boolean selectionEnCours,
                                             @Param("selection") Boolean selection);
}

