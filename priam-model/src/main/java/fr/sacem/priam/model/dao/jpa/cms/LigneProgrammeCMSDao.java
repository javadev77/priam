package fr.sacem.priam.model.dao.jpa.cms;

import fr.sacem.priam.model.domain.LignePreprep;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionCMSDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by benmerzoukah on 29/05/2017.
 */
@Transactional(readOnly = true)
public interface LigneProgrammeCMSDao extends JpaRepository<LigneProgrammeCMS, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM LigneProgrammeCMS lp WHERE lp.fichier.id = :fichierId")
    void deleteAllByFichierId(@Param("fichierId") Long fileId);

    List<LigneProgrammeCMS> findByFichierId(Long fileId);


    @Transactional
    @Query(value="SELECT l " +
            "FROM LigneProgrammeCMS l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg ")
    Page<LigneProgrammeCMS> findLigneProgrammeByProgrammeId(@Param("numProg") String numProg, Pageable pageable);

    @Transactional
    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCMS l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg ")
    List<LigneProgrammeCMS> findLigneProgrammeByNumProg(@Param("numProg") String numProg);

    @Transactional
    @Query(value="SELECT new fr.sacem.priam.model.domain.dto.SelectionCMSDto("+
                    "ligneProgramme.ide12, " +
                    "ligneProgramme.titreOeuvre, " +
                    "ligneProgramme.roleParticipant1, " +
                    "ligneProgramme.nomParticipant1, " +
                    "ligneProgramme.ajout, " +
                    "ligneProgramme.selectionEnCours, " +
                    "sum(ligneProgramme.mt)) " +
            "FROM LigneProgrammeCMS ligneProgramme join ligneProgramme.fichier  f " +
            "WHERE ligneProgramme.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND (ligneProgramme.ide12 = :ide12 OR :ide12 IS NULL) " +
            "AND (ligneProgramme.ajout = :ajout OR :ajout IS NULL) " +
            "AND (ligneProgramme.selectionEnCours = :selectionEnCours OR :selectionEnCours IS NULL) " +
            "AND (ligneProgramme.titreOeuvre = :titre OR :titre IS NULL) " +
            "AND (ligneProgramme.oeuvreManuel IS NULL) " +
            "GROUP BY ligneProgramme.ide12 ")
    Page<SelectionCMSDto> findLigneProgrammeByCriteria(@Param("numProg") String numProg,
                                                       @Param("ide12") Long ide12,
                                                       @Param("titre") String titre,
                                                       @Param("ajout") String ajout,
                                                       @Param("selectionEnCours") Boolean selectionEnCours, Pageable pageable);

    @Transactional
    @Query(value="SELECT  new fr.sacem.priam.model.domain.LignePreprep("+
                     "ligneProgramme.cdeCisac, " +
                     "prog.cdeTer, " +
                     "prog.rionTheorique.rion, " +
                     "prog.famille.code, " +
                     "prog.numProg, " +
                     "ligneProgramme.cdeUtil, " +
                     "'FORFAI', " +
                     "prog.typeUtilisation.code, " +
                     "'PRINC', " +
                     "'SANS', " +
                     "prog.nom, " +
                     "prog.dateDbtPrg, " +
                     "prog.dateFinPrg, " +
                     "ligneProgramme.cdeGreDif, " +
                     "ligneProgramme.cdeModDif, " +
                     "ligneProgramme.cdeTypIde12, " +
                     "ligneProgramme.ide12, " +
                     "sum(ligneProgramme.durDif), " +
                     "(CASE WHEN prog.typeUtilisation.code = 'CPRIVSONRD' THEN 1L  ELSE sum(ligneProgramme.nbrDif) END),  " +
                     "0.0d , " +
                     "ligneProgramme.ctna, " +
                     "ligneProgramme.paramCoefHor, " +
                     "ligneProgramme.durDifCtna, " +
                     "ligneProgramme.cdeLng, " +
                     "ligneProgramme.indDoubSsTit, " +
                     "ligneProgramme.tax) " +
                     "FROM LigneProgrammeCMS ligneProgramme inner join ligneProgramme.fichier  f " +
                     "inner join f.programme prog " +
                     "WHERE prog.numProg = :numProg " +
                     "AND ligneProgramme.selection = true " +
                     "AND ligneProgramme.oeuvreManuel IS NULL " +
                     "GROUP BY ligneProgramme.ide12, " +
                     "ligneProgramme.cdeUtil " +
                     "ORDER BY ligneProgramme.cdeCisac ASC , prog.cdeTer ASC, prog.rionTheorique.rion ASC, " +
                     "prog.famille.code ASC, prog.typeUtilisation.code ASC, ligneProgramme.cdeUtil ASC, prog.dateDbtPrg ASC, prog.dateFinPrg ASC ")
    List<LignePreprep> findLigneProgrammeSelectionnesForFelix(@Param("numProg") String numProg);

    @Transactional(readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct  new fr.sacem.priam.model.domain.dto.KeyValueDto(l.ide12) " +
                    "FROM " +
                    "LigneProgrammeCMS l " +
                    "WHERE " +
                    "l.fichier in (" +
                    " select f.id from Fichier f where f.programme.numProg like :programme" +
                    ")" +
                    "AND CONCAT(l.ide12,'') like %:query% " +
                    "ORDER BY l.ide12")
    List<KeyValueDto> findIDE12sByProgramme(@Param("query") Long query, @Param("programme") String programme);


    @Transactional(readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct new fr.sacem.priam.model.domain.dto.KeyValueDto(l.titreOeuvre) " +
                    "FROM " +
                    "LigneProgrammeCMS l " +
                    "WHERE " +
                    "l.fichier in (" +
                    " select f.id from Fichier f where f.programme.numProg like :programme" +
                    ")" +
                    "AND UPPER(l.titreOeuvre) like %:titre% " +
                    "ORDER BY l.titreOeuvre")
    List<KeyValueDto> findTitresByProgramme(@Param("titre") String titre, @Param("programme") String programme);

    @Transactional(readOnly = true)
    @Query(value =
               "SELECT distinct ligneProgramme.libelleUtilisateur " +
                   "FROM LigneProgrammeCMS ligneProgramme inner join ligneProgramme.fichier as f "+
                   "WHERE f.programme.numProg = :programme " +
                   "ORDER BY ligneProgramme.libelleUtilisateur")
    List<String> findUtilisateursByProgramme(@Param("programme") String programme);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME_CP p " +
            "INNER JOIN " +
            "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set " +
            "  p.SEL_EN_COURS =?2 " +
            "where " +
            "  f.NUMPROG = ?1")
    void updateSelectionTemporaireByNumProgramme(@Param("numProg") String numProg, @Param("selectionEnCours") boolean selectionEnCours);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME_CMS p " +
            "INNER JOIN " +
            "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set " +
            "  p.SEL_EN_COURS=1 " +
            "where " +
            "  f.NUMPROG = ?1" +
            " AND p.ide12 <> ?2 " +
            " AND p.cdeUtil not like ?3")
    void updateSelectionByNumProgrammeExcept(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME_CMS p " +
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

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE FROM " +
            "PRIAM_LIGNE_PROGRAMME_CMS p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "WHERE  "+
            "f.NUMPROG = ?1 " +
            "AND p.ide12 = ?2 " +
            "AND p.ajout = 'Manuel' ")
    void deleteLigneProgrammeByIde12AndNumProg(@Param("numProg") String numProg,
                                               @Param("ide12") Long ide12);


    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCMS l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.ide12 = :ide12 " +
                     "AND l.cdeUtil = :cdeUtil " +
                     "AND l.oeuvreManuel IS NULL " +
                     "AND l.ajout = 'Automatique' ")
    List<LigneProgrammeCMS> findOeuvresAutoByIde12AndCdeUtil(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);

    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCMS l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.ide12 = :ide12 " +
                     "AND l.oeuvreManuel IS NULL " +
                     "AND l.ajout = 'Manuel' ")
    LigneProgrammeCMS findOeuvreManuelByIde12(@Param("numProg") String numProg, @Param("ide12") Long ide12);

    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCMS l " +
                     "WHERE l.oeuvreManuel.id = :idOeuvreManuel  " +
                     "AND l.ajout = 'Automatique' ")
    List<LigneProgrammeCMS> findOeuvresAutoByIdOeuvreManuel(@Param("idOeuvreManuel") Long idOeuvreManuel);
    
    
    
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update " +
             "PRIAM_LIGNE_PROGRAMME_CMS p " +
              "INNER JOIN " +
                "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                "set  p.selection=?2 " +
               "WHERE  "+
               "f.NUMPROG = ?1 AND p.SEL_EN_COURS=?2")
    void updateSelection(@Param("numProg") String numProg, @Param("selection") boolean value);
    
    
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update " +
                                           "PRIAM_LIGNE_PROGRAMME_CMS p " +
                                           "INNER JOIN " +
                                           "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                                           "set  p.SEL_EN_COURS=?2 " +
                                           "WHERE  "+
                                           "f.NUMPROG = ?1 AND p.selection=?2")
    void updateSelectionTemporaire(@Param("numProg") String numProg, @Param("selection") boolean value);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE p.* FROM " +
                                           "PRIAM_LIGNE_PROGRAMME_CMS p " +
                                           "INNER JOIN " +
                                           "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                                           "WHERE  "+
                                           "f.NUMPROG = ?1 " +
                                           "AND p.selection=?2 " +
                                           "AND p.ajout = 'Manuel' ")
    void deleteOeuvresManuels(@Param("numProg") String numProg, @Param("selection") boolean value);
    
    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCMS l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.selection = :selection " +
                     "AND l.ajout = 'Manuel' ")
    List<LigneProgrammeCMS> findOeuvresManuelsEnCoursEdition(@Param("numProg") String numProg, @Param("selection") boolean value);
    
    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCMS l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.ajout = 'Manuel' ")
    List<LigneProgrammeCMS> findAllOeuvresManuelsByNumProg(@Param("numProg") String numProg);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE p.* FROM " +
                                           "PRIAM_LIGNE_PROGRAMME_CMS p " +
                                           "INNER JOIN " +
                                           "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                                           "WHERE  "+
                                           "f.NUMPROG = ?1 " +
                                           "AND p.ajout = 'Manuel' ")
    void deleteAllOeuvres(@Param("numProg") String numProg);
    
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value="update " +
                                         "  PRIAM_LIGNE_PROGRAMME_CMS p " +
                                         "INNER JOIN " +
                                         "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                                         "set " +
                                         "  p.selection =?2 " +
                                         "where " +
                                         "  f.NUMPROG = ?1")
    void deselectAllByNumProgramme(@Param("numProg") String numProg, @Param("selection") boolean selection);
    
    
    
    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCMS l inner join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.ide12 = :ide12 " +
                     "AND l.cdeUtil = :cdeUtil " +
                     "AND l.oeuvreManuel IS NULL ")
    LigneProgrammeCMS findByIde12AndCdeUtil(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);


    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value =
            "SELECT " +
                    "count(points), ajout" +
                    " from ( " +
                    "       SELECT " +
                    "           count(l.mt) points, l.ide12, l.ajout " +
                    "       FROM " +
                    "           PRIAM_LIGNE_PROGRAMME_CMS l " +
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

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value =
            "SELECT sum(points) from ( SELECT " +
                    "sum(l.mt) points, l.ide12 " +
                    "FROM " +
                    "PRIAM_LIGNE_PROGRAMME_CMS l inner join PRIAM_FICHIER as f " +
                    "on l.ID_FICHIER=f.ID " +
                    "WHERE " +
                    "f.numProg = ?1 " +
                    "AND (?2 IS NULL OR l.SEL_EN_COURS = ?2) " +
                    "AND l.idOeuvreManuel IS NULL " +
                    "GROUP BY l.ide12) result ")
    Double calculerPointsMontantOeuvres(@Param("numProg") String numProg, @Param("selection") Integer selection);
}
