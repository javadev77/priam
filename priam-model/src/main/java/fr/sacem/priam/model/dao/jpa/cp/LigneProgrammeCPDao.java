package fr.sacem.priam.model.dao.jpa.cp;

import fr.sacem.priam.model.domain.LignePreprep;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
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
public interface LigneProgrammeCPDao extends JpaRepository<LigneProgrammeCP, Long> {


    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM LigneProgrammeCP lp WHERE lp.fichier.id = :fichierId")
    void deleteAllByFichierId(@Param("fichierId") Long fileId);

    List<LigneProgrammeCP> findByFichierId(Long fileId);



    @Query(value="SELECT l " +
            "FROM LigneProgrammeCP l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg ")
    Page<LigneProgrammeCP> findLigneProgrammeByProgrammeId(@Param("numProg") String numProg, Pageable pageable);
    

    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCP l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg ")
    List<LigneProgrammeCP> findLigneProgrammeByNumProg(@Param("numProg") String numProg);

   @Query(value="SELECT new fr.sacem.priam.model.domain.dto.SelectionDto("+
                    "ligneProgramme.ide12, " +
                    "ligneProgramme.titreOeuvre, " +
                    "ligneProgramme.roleParticipant1, " +
                    "ligneProgramme.nomParticipant1, " +
                    "ligneProgramme.ajout, " +
                    "ligneProgramme.durDifEdit, " + //"sum(ligneProgramme.durDifEdit), " +
                    "ligneProgramme.nbrDifEdit, " + //"sum(ligneProgramme.nbrDifEdit), " +
                    "ligneProgramme.selectionEnCours, " +
                    "ligneProgramme.libelleUtilisateur, " +
                    "ligneProgramme.cdeUtil) " +
                    //"CONCAT(ligneProgramme.cdeUtil, CASE WHEN lu.libAbrgUtil is null THEN '' ELSE ' - ' END, COALESCE(lu.libAbrgUtil,''))) " +
            "FROM LigneProgrammeCP ligneProgramme " +
            "inner join ligneProgramme.fichier  f " +
            //"SareftjLibutil lu "+
            "WHERE ligneProgramme.fichier = f.id " +
            //"AND lu.cdeUtil = ligneProgramme.cdeUtil "+
            "AND f.programme.numProg = :numProg " +
            "AND (ligneProgramme.ide12 = :ide12 OR :ide12 IS NULL) " +
            "AND (ligneProgramme.ajout = :ajout OR :ajout IS NULL) " +
            "AND (ligneProgramme.selectionEnCours = :selectionEnCours OR :selectionEnCours IS NULL) " +
            "AND (ligneProgramme.titreOeuvre = :titre OR :titre IS NULL) " +
            "AND (ligneProgramme.cdeUtil = :utilisateur OR :utilisateur IS NULL) " +
            "AND (ligneProgramme.oeuvreManuel IS NULL) ")// +
            //"GROUP BY ligneProgramme.ide12, ligneProgramme.cdeUtil")

    Page<SelectionDto> findLigneProgrammeByCriteria(@Param("numProg") String numProg,
                                      @Param("utilisateur") String utilisateur,
                                      @Param("ide12") Long ide12,
                                      @Param("titre") String titre,
                                      @Param("ajout") String ajout,
                                      @Param("selectionEnCours") Boolean selectionEnCours,Pageable pageable);


    @Query(value="SELECT new fr.sacem.priam.model.domain.dto.SelectionDto("+
            "ligneProgramme.ide12, " +
            "ligneProgramme.titreOeuvre, " +
            "ligneProgramme.roleParticipant1, " +
            "ligneProgramme.nomParticipant1, " +
            "ligneProgramme.ajout, " +
            "sum(ligneProgramme.durDifEdit), " +
            "sum(ligneProgramme.nbrDifEdit), " +
            "ligneProgramme.selection, " +
            "ligneProgramme.libelleUtilisateur, " +
            "ligneProgramme.cdeUtil) " +
            //"CONCAT(ligneProgramme.cdeUtil, CASE WHEN lu.libAbrgUtil is null THEN '' ELSE ' - ' END, COALESCE(lu.libAbrgUtil,''))) " +
            "FROM LigneProgrammeCP ligneProgramme join ligneProgramme.fichier  f " +
            //"SareftjLibutil lu "+
            "WHERE ligneProgramme.fichier = f.id " +
            //"AND lu.cdeUtil = ligneProgramme.cdeUtil "+
            "AND f.programme.numProg = :numProg " +
            "AND (ligneProgramme.ide12 = :ide12 OR :ide12 IS NULL) " +
            "AND (ligneProgramme.ajout = :ajout OR :ajout IS NULL) " +
            "AND (ligneProgramme.selection = :selection OR :selection IS NULL) " +
            "AND (ligneProgramme.titreOeuvre = :titre OR :titre IS NULL) " +
            "AND (ligneProgramme.cdeUtil = :utilisateur OR :utilisateur IS NULL) " +
            "AND (ligneProgramme.oeuvreManuel IS NULL) " +
            "GROUP BY ligneProgramme.ide12, " +
            "ligneProgramme.cdeUtil")
    List<SelectionDto> findLigneProgrammeSelected(@Param("numProg") String numProg,
                                                    @Param("utilisateur") String utilisateur,
                                                    @Param("ide12") Long ide12,
                                                    @Param("titre") String titre,
                                                    @Param("ajout") String ajout,
                                                    @Param("selection") Boolean selection);



    @Query(value="SELECT ligneProgramme.ide12 " +
            "FROM LigneProgrammeCP ligneProgramme inner join ligneProgramme.fichier  f " +
            "WHERE ligneProgramme.fichier.id = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND (ligneProgramme.selectionEnCours = :selectionEnCours OR :selectionEnCours IS NULL) " +
            "AND (ligneProgramme.selection = :selection OR :selection IS NULL) " +
            "AND (ligneProgramme.oeuvreManuel IS NULL) ")
    List<Long> findLigneProgrammePreselected(@Param("numProg") String numProg,
                                                  @Param("selectionEnCours") Boolean selectionEnCours,
                                                  @Param("selection") Boolean selection);
    
    @Transactional(readOnly = true, value = "transactionManager")
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
                     "FROM LigneProgrammeCP ligneProgramme inner join ligneProgramme.fichier  f " +
                     "inner join f.programme prog " +
                     "WHERE prog.numProg = :numProg " +
                     "AND ligneProgramme.selection = true " +
                     "AND ligneProgramme.oeuvreManuel IS NULL " +
                     "GROUP BY ligneProgramme.ide12, " +
                     "ligneProgramme.cdeUtil " +
                     "ORDER BY ligneProgramme.cdeCisac ASC , prog.cdeTer ASC, prog.rionTheorique.rion ASC, " +
                     "prog.famille.code ASC, prog.typeUtilisation.code ASC, ligneProgramme.cdeUtil ASC, prog.dateDbtPrg ASC, prog.dateFinPrg ASC ")
    List<LignePreprep> findLigneProgrammeSelectionnesForFelix(@Param("numProg") String numProg);

    @Transactional(readOnly = true, value = "transactionManager")
    @Query(value =
            "SELECT " +
                    " distinct  new fr.sacem.priam.model.domain.dto.KeyValueDto(l.ide12) " +
                    "FROM " +
                    "LigneProgrammeCP l " +
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
                    "LigneProgrammeCP l " +
                    "WHERE " +
                    "l.fichier in (" +
                    " select f.id from Fichier f where f.programme.numProg like :programme" +
                    ")" +
                    "AND UPPER(l.titreOeuvre) like %:titre% " +
                    "ORDER BY l.titreOeuvre")
    List<KeyValueDto> findTitresByProgramme(@Param("titre") String titre, @Param("programme") String programme);

    @Transactional(readOnly = true, value = "transactionManager")
    @Query(value =
               "SELECT distinct ligneProgramme.libelleUtilisateur " +
                   "FROM LigneProgrammeCP ligneProgramme inner join ligneProgramme.fichier as f "+
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
    @Transactional("transactionManager")
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME_CP p " +
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
            "  PRIAM_LIGNE_PROGRAMME_CP p " +
            "INNER JOIN " +
            "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set " +
            "  p.SEL_EN_COURS=?4 "+
            "where " +
            "  f.NUMPROG = ?1 " +
            " AND p.ide12 = ?2 " +
            " AND p.cdeUtil = ?3 ")
    void updateSelectionTemporaireByNumProgramme(@Param("numProg") String numProg,
                                                 @Param("ide12") Long ide12,
                                                 @Param("cdeUtil") String cdeUtil,
                                                 @Param("sel") int select);


    @Transactional("transactionManager")
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE FROM " +
            "PRIAM_LIGNE_PROGRAMME_CP p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "WHERE  "+
            "f.NUMPROG = ?1 " +
            "AND p.ide12 = ?2 " +
            "AND p.cdeUtil = ?3 AND p.ajout = 'MANUEL' ")
    void deleteLigneProgrammeByIde12AndNumProg(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil")String cdeUtil);
    
    
    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCP l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.ide12 = :ide12 " +
                     "AND l.cdeUtil = :cdeUtil " +
                     "AND l.oeuvreManuel IS NULL " +
                     "AND l.ajout = 'AUTOMATIQUE' ")
    List<LigneProgrammeCP> findOeuvresAutoByIde12AndCdeUtil(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);

    @Query(value="SELECT l " +
            "FROM LigneProgrammeCP l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND l.ide12 = :ide12 " +
            "AND l.cdeUtil = :cdeUtil " +
            "AND l.oeuvreManuel IS NOT NULL " +
            "AND l.ajout = 'AUTOMATIQUE' ")
    List<LigneProgrammeCP> findOeuvresAutoLinkCorrigeByIde12AndCdeUtil(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);
    
    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCP l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.ide12 = :ide12 " +
                     "AND l.cdeUtil = :cdeUtil " +
                     "AND l.oeuvreManuel IS NULL " +
                     "AND l.ajout = 'MANUEL' ")
    LigneProgrammeCP findOeuvreManuelByIde12AndCdeUtil(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);

    @Query(value="SELECT l " +
            "FROM LigneProgrammeCP l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND l.ide12 = :ide12 " +
            "AND l.cdeUtil = :cdeUtil " +
            "AND l.oeuvreManuel IS NULL " +
            "AND l.ajout = 'CORRIGE' ")
    LigneProgrammeCP findOeuvreCorrigeByIde12AndCdeUtil(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);
    
    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCP l " +
                     "WHERE l.oeuvreManuel.id = :idOeuvreManuel  " +
                     "AND l.ajout = 'AUTOMATIQUE' ")
    List<LigneProgrammeCP> findOeuvresAutoByIdOeuvreManuel(@Param("idOeuvreManuel")Long idOeuvreManuel);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update " +
             "PRIAM_LIGNE_PROGRAMME_CP p " +
              "INNER JOIN " +
                "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                "set  p.selection=?2 " +
               "WHERE  "+
               "f.NUMPROG = ?1 " +
               "AND p.SEL_EN_COURS=?2 " +
               "and p.selection <> p.SEL_EN_COURS " +
               "AND p.idOeuvreManuel is NULL ")
    void updateSelection(@Param("numProg") String numProg, @Param("selection") boolean value);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update " +
            "PRIAM_LIGNE_PROGRAMME_CP p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set  p.durDif=p.durDifEdit " +
            "WHERE  "+
            "f.NUMPROG = ?1 " +
            "AND p.SEL_EN_COURS=1 " +
            "AND p.idOeuvreManuel is NULL " +
            "AND p.ajout='MANUEL' OR p.ajout='CORRIGE' " +
            "AND p.nbrDif <> p.nbrDifEdit")
    void updateDurDif(@Param("numProg") String numProg);

    
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update " +
                                           "PRIAM_LIGNE_PROGRAMME_CP p " +
                                           "INNER JOIN " +
                                           "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                                           "set  p.SEL_EN_COURS=?2 " +
                                           "WHERE  "+
                                           "f.NUMPROG = ?1 AND p.selection=?2")
    void updateSelectionTemporaire(@Param("numProg") String numProg, @Param("selection") boolean value);


    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update " +
            "PRIAM_LIGNE_PROGRAMME_CP p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set  p.durDifEdit=p.durDif " +
            "WHERE  "+
            "f.NUMPROG = ?1 AND p.selection=?2")
    void updateDurDifTemporaire(@Param("numProg") String numProg, @Param("selection") boolean selection);

    @Transactional("transactionManager")
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE p.* FROM " +
                                           "PRIAM_LIGNE_PROGRAMME_CP p " +
                                           "INNER JOIN " +
                                           "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                                           "WHERE  "+
                                           "f.NUMPROG = ?1 " +
                                           "AND p.selection=?2 " +
                                           "AND p.ajout = 'MANUEL' ")
    void deleteOeuvresManuels(@Param("numProg") String numProg, @Param("selection") boolean value);
    
    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCP l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.selection = 0 " +
                     "AND l.ajout = 'CORRIGE' ")
    List<LigneProgrammeCP> findOeuvresManuelsEnCoursEdition(@Param("numProg") String numProg);
    
    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCP l join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.ajout = 'MANUEL' " +
                     "OR l.ajout = 'CORRIGE' ")
    List<LigneProgrammeCP> findAllOeuvresManuelsByNumProg(@Param("numProg") String numProg);
    
    @Transactional("transactionManager")
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE p.* FROM " +
                                           "PRIAM_LIGNE_PROGRAMME_CP p " +
                                           "INNER JOIN " +
                                           "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                                           "WHERE  "+
                                           "f.NUMPROG = ?1 " +
                                           "AND p.ajout = 'MANUEL' ")
    void deleteAllOeuvres(@Param("numProg") String numProg);
    
    
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value="update " +
                                         "  PRIAM_LIGNE_PROGRAMME_CP p " +
                                         "INNER JOIN " +
                                         "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                                         "set " +
                                         "  p.selection =?2 " +
                                         "where " +
                                         "  f.NUMPROG = ?1")
    void deselectAllByNumProgramme(@Param("numProg") String numProg, @Param("selection") boolean selection);
    
    
    
    @Query(value="SELECT l " +
                     "FROM LigneProgrammeCP l inner join l.fichier as f "+
                     "WHERE l.fichier = f.id " +
                     "AND f.programme.numProg = :numProg " +
                     "AND l.ide12 = :ide12 " +
                     "AND l.cdeUtil = :cdeUtil " +
                     "AND l.oeuvreManuel IS NULL ")
    LigneProgrammeCP findByIde12AndCdeUtil(@Param("numProg") String numProg, @Param("ide12") Long ide12, @Param("cdeUtil") String cdeUtil);


    @Transactional(readOnly = true, value = "transactionManager")
    @Query(nativeQuery = true, value ="SELECT count(lp.SEL_EN_COURS) " +
                    "FROM PRIAM_LIGNE_PROGRAMME_CP lp " +
                    "INNER JOIN PRIAM_FICHIER as f on f.ID=lp.ID_FICHIER AND f.NUMPROG=?1 "+
                    "WHERE lp.SEL_EN_COURS=1 AND lp.ajout=?2")
    Long compterOuvres(@Param("numProg") String numProg, @Param("etat") String etat);

    @Transactional(readOnly = true, value = "transactionManager")
    @Query(nativeQuery = true, value =
            "SELECT sum(l.durDifEdit) duree " +
                    "FROM " +
                    "PRIAM_LIGNE_PROGRAMME_CP l inner join PRIAM_FICHIER as f " +
                    "on l.ID_FICHIER=f.ID " +
                    "WHERE " +
                    "f.numProg = ?1 " +
                    "AND l.SEL_EN_COURS = 1 " +
                    "AND l.idOeuvreManuel IS NULL ")
    Long calculerDureeOeuvres(@Param("numProg") String numProg);

    @Transactional(readOnly = true, value = "transactionManager")
    @Query(nativeQuery = true, value =
    "SELECT sum(ajout) "+
    "FROM(SELECT lp.nbrDifEdit as ajout "+
            "FROM PRIAM_LIGNE_PROGRAMME_CP lp "+
            "INNER JOIN PRIAM_FICHIER as f on f.ID=lp.ID_FICHIER AND f.NUMPROG=?1 "+
            "WHERE lp.SEL_EN_COURS=1 AND lp.idOeuvreManuel is null "+
    ") as temp")
    Long calculerQuantiteOeuvres(@Param("numProg") String numProg);


    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value="update " +
            "  PRIAM_LIGNE_PROGRAMME_CP p " +
            "INNER JOIN " +
            "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set " +
            "  p.nbrDifEdit=?4, p.SEL_EN_COURS=?5 "+
            "where " +
            "  f.NUMPROG = ?1 " +
            " AND p.ide12 = ?2 " +
            " AND p.cdeUtil = ?3 ")
    void updateNbrDifTemporaireByNumProgramme(@Param("numProg") String numProg,
                                              @Param("ide12") Long ide12,
                                              @Param("cdeUtil") String cdeUtil,
                                              @Param("durDif") Long nbrDifEdit,
                                              @Param("selectionEnCours") Boolean selectionEnCours);



  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value="update " +
          "  PRIAM_LIGNE_PROGRAMME_CP p " +
          "INNER JOIN " +
          "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
          "set " +
          "  p.durDifEdit=?4, p.SEL_EN_COURS=?5 "+
          "where " +
          "  f.NUMPROG = ?1 " +
          " AND p.ide12 = ?2 " +
          " AND p.cdeUtil = ?3 ")
  void updateDurDifTemporaireByNumProgramme(@Param("numProg") String numProg,
                                            @Param("ide12") Long ide12,
                                            @Param("cdeUtil") String cdeUtil,
                                            @Param("durDif") Long durDifEdit,
                                            @Param("selectionEnCours") Boolean selectionEnCours);


    @Modifying(clearAutomatically = true)
    @Transactional("transactionManager")
    @Query(nativeQuery = true, value = "update " +
            "PRIAM_LIGNE_PROGRAMME_CP p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set  p.nbrDif=p.nbrDifEdit " +
            "WHERE  "+
            "f.NUMPROG = ?1 " +
            "AND p.SEL_EN_COURS=1 " +
            "AND p.idOeuvreManuel is NULL " +
            "AND p.ajout='MANUEL' OR p.ajout='CORRIGE' ")
    void updateNbrDif(@Param("numProg") String numProg);

    @Modifying(clearAutomatically = true)
    @Transactional("transactionManager")
    @Query(nativeQuery = true, value = "update " +
            "PRIAM_LIGNE_PROGRAMME_CP p " +
            "INNER JOIN " +
            "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
            "set  p.nbrDifEdit=p.nbrDif " +
            "WHERE  "+
            "f.NUMPROG = ?1 AND p.SEL_EN_COURS=?2")
    void updateNbrDifTemporaire(@Param("numProg") String numProg, @Param("selection") boolean selection);


    @Query(value="SELECT l " +
            "FROM LigneProgrammeCP l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND l.selection <> l.selectionEnCours " +
            "AND l.oeuvreManuel is null ")
    List<LigneProgrammeCP> findLigneProgrammeSelectionChanged(@Param("numProg") String numProg);

    @Query(value="SELECT l.id " +
            "FROM LigneProgrammeCP l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg " +
            "AND l.nbrDif <> l.nbrDifEdit ")
    List<Long> findLigneProgrammeNbrDifChanged(@Param("numProg") String numProg);
}
