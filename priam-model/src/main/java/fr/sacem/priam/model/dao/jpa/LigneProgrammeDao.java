package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.LignePreprep;
import fr.sacem.priam.model.domain.LigneProgramme;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
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
public interface LigneProgrammeDao extends JpaRepository<LigneProgramme, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM LigneProgramme lp WHERE lp.fichier.id = :fichierId")
    void deleteAllByFichierId(@Param("fichierId") Long fileId);

    List<LigneProgramme> findByFichierId(Long fileId);


    @Transactional
    @Query(value="SELECT l " +
            "FROM LigneProgramme l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg ")
    Page<LigneProgramme> findLigneProgrammeByProgrammeId(@Param("numProg") String numProg, Pageable pageable);
    
    @Transactional
    @Query(value="SELECT l " +
                     "FROM LigneProgramme l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg ")
    List<LigneProgramme> findLigneProgrammeByNumProg(@Param("numProg") String numProg);

    @Transactional
    @Query(value="SELECT new fr.sacem.priam.model.domain.dto.SelectionDto("+
                    "ligneProgramme.ide12, " +
                    "ligneProgramme.titreOeuvre, " +
                    "ligneProgramme.roleParticipant1, " +
                    "ligneProgramme.nomParticipant1, " +
                    "ligneProgramme.ajout, " +
                    "sum(ligneProgramme.durDif), " +
                    "sum(ligneProgramme.nbrDif), " +
                    "ligneProgramme.selection, " +
                    "CONCAT(ligneProgramme.cdeUtil, CASE WHEN lu.libAbrgUtil is null THEN '' ELSE ' - ' END, COALESCE(lu.libAbrgUtil,''))) " +
            "FROM LigneProgramme ligneProgramme join ligneProgramme.fichier  f, " +
            "LibelleUtilisateur lu "+
            "WHERE ligneProgramme.fichier = f.id " +
            "AND lu.cdeUtil = ligneProgramme.cdeUtil "+
            "AND f.programme.numProg = :numProg " +
            "AND (ligneProgramme.ide12 = :ide12 OR :ide12 IS NULL) " +
            "AND (ligneProgramme.ajout = :ajout OR :ajout IS NULL) " +
            "AND (ligneProgramme.selection = :selection OR :selection IS NULL) " +
            "AND (ligneProgramme.titreOeuvre = :titre OR :titre IS NULL) " +
            "AND (ligneProgramme.cdeUtil = :utilisateur OR :utilisateur IS NULL) " +
            "AND (ligneProgramme.oeuvreManuel IS NULL) " +
            "GROUP BY ligneProgramme.ide12, " +
                "ligneProgramme.cdeUtil")
    Page<SelectionDto> findLigneProgrammeByCriteria(@Param("numProg") String numProg,
                                      @Param("utilisateur") String utilisateur,
                                      @Param("ide12") Long ide12,
                                      @Param("titre") String titre,
                                      @Param("ajout") String ajout,
                                      @Param("selection") Boolean selection,Pageable pageable);
    
    @Transactional
    @Query(value="SELECT  new fr.sacem.priam.model.domain.LignePreprep("+
                     "ligneProgramme.cdeCisac, " +
                     "ligneProgramme.cdeUtil, " +
                     "ligneProgramme.cdeGreDif, " +
                     "ligneProgramme.cdeModDif, " +
                     "ligneProgramme.cdeTypIde12, " +
                     "ligneProgramme.ide12, " +
                     "sum(ligneProgramme.durDif), " +
                     "sum(ligneProgramme.nbrDif), " +
                     "sum(ligneProgramme.mt), " +
                     "ligneProgramme.ctna, " +
                     "ligneProgramme.paramCoefHor, " +
                     "ligneProgramme.durDifCtna, " +
                     "ligneProgramme.cdeLng, " +
                     "ligneProgramme.indDoubSsTit, " +
                     "ligneProgramme.tax) " +
                     
                     "FROM LigneProgramme ligneProgramme inner join ligneProgramme.fichier  f, " +
                     "LibelleUtilisateur lu  "+
                     "WHERE lu.cdeUtil = ligneProgramme.cdeUtil " +
                     "AND f.programme.numProg = :numProg " +
                     "AND ligneProgramme.selection = true " +
                     "AND ligneProgramme.oeuvreManuel IS NULL " +
                     "GROUP BY ligneProgramme.ide12, " +
                     "ligneProgramme.cdeUtil ")
    List<LignePreprep> findLigneProgrammeSelectionnesForFelix(@Param("numProg") String numProg);

    @Transactional(readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct  new fr.sacem.priam.model.domain.dto.KeyValueDto(l.ide12) " +
                    "FROM " +
                    "LigneProgramme l " +
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
                    "LigneProgramme l " +
                    "WHERE " +
                    "l.fichier in (" +
                    " select f.id from Fichier f where f.programme.numProg like :programme" +
                    ")" +
                    "AND UPPER(l.titreOeuvre) like %:titre% " +
                    "ORDER BY l.titreOeuvre")
    List<KeyValueDto> findTitresByProgramme(@Param("titre") String titre, @Param("programme") String programme);

    @Transactional(readOnly = true)
    @Query(value =
            "SELECT distinct CONCAT(lu.cdeUtil, CASE WHEN lu.libAbrgUtil is null THEN '' ELSE ' - ' END, COALESCE(lu.libAbrgUtil,'')) " +
                    "FROM LigneProgramme ligneProgramme join ligneProgramme.fichier  f , " +
                    "LibelleUtilisateur lu "+
                    "WHERE ligneProgramme.fichier = f.id " +
                    "AND lu.cdeUtil = ligneProgramme.cdeUtil "+
                    "AND f.programme.numProg = :programme " +
                    "ORDER BY ligneProgramme.cdeUtil")
    List<String> findUtilisateursByProgramme(@Param("programme") String programme);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME p " +
            "INNER JOIN " +
            "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set " +
            "  p.selection=?2 " +
            "where " +
            "  f.NUMPROG = ?1")
    void updateSelectionByNumProgramme(@Param("numProg") String numProg, @Param("selection") boolean selection);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME p " +
            "INNER JOIN " +
            "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set " +
            "  p.selection=1 " +
            "where " +
            "  f.NUMPROG = ?1" +
            " AND p.ide12 <> ?2 " +
            " AND p.cdeUtil not like ?3")
    void updateSelectionByNumProgrammeExcept(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME p " +
            "INNER JOIN " +
            "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set " +
            "  p.selection=1 " +
            "where " +
            "  f.NUMPROG = ?1" +
            " AND p.ide12 = ?2" +
            " AND p.cdeUtil like ?3")
    void updateSelectionByNumProgramme(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE FROM " +
            "PRIAM_LIGNE_PROGRAMME p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "WHERE  "+
            "f.NUMPROG = ?1 " +
            "AND p.ide12 = ?2 " +
            "AND p.cdeUtil = ?3 AND p.ajout = 'Manuel' ")
    void deleteLigneProgrammeByIde12AndNumProg(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil")String cdeUtil);
    
    
    @Query(value="SELECT l " +
                     "FROM LigneProgramme l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.ide12 = :ide12 " +
                     "AND l.cdeUtil = :cdeUtil " +
                     "AND l.oeuvreManuel IS NULL " +
                     "AND l.ajout = 'Automatique' ")
    List<LigneProgramme> findOeuvresAutoByIde12AndCdeUtil(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);
    
    @Query(value="SELECT l " +
                     "FROM LigneProgramme l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.ide12 = :ide12 " +
                     "AND l.cdeUtil = :cdeUtil " +
                     "AND l.oeuvreManuel IS NULL " +
                     "AND l.ajout = 'Manuel' ")
    LigneProgramme findOeuvreManuelByIde12AndCdeUtil(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);
    
    @Query(value="SELECT l " +
                     "FROM LigneProgramme l " +
                     "WHERE l.oeuvreManuel.id = :idOeuvreManuel  " +
                     "AND l.ajout = 'Automatique' ")
    List<LigneProgramme> findOeuvresAutoByIdOeuvreManuel(@Param("idOeuvreManuel")Long idOeuvreManuel);
}
