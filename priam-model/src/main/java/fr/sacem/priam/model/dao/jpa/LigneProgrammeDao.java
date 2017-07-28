package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.LigneProgramme;
import fr.sacem.priam.model.domain.dto.AutocompleteDto;
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
            "AND f.programme.numProg = :numProg " +
            "AND (l.ide12 = :ide12 OR :ide12 IS NULL) " +
            "AND (l.ajout = :ajout OR :ajout IS NULL) " +
            "AND (l.selection = :selection OR :selection IS NULL) " +
            "AND (l.titreOeuvre = :titre OR :titre IS NULL) " +
            "AND (l.utilisateur = :utilisateur OR :utilisateur IS NULL) " +
            "")
    Page<LigneProgramme> findLigneProgrammeByCriteria(@Param("numProg") String numProg,
                                                      @Param("utilisateur") String utilisateur,
                                                      @Param("ide12") Long ide12,
                                                      @Param("titre") String titre,
                                                      @Param("ajout") String ajout,
                                                      @Param("selection") Boolean selection,
                                                      Pageable pageable);


    @Transactional(readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct  new fr.sacem.priam.model.domain.dto.AutocompleteDto(l.ide12) " +
            "FROM " +
                    "LigneProgramme l " +
            "WHERE " +
                    "l.fichier in (" +
                        " select f.id from Fichier f where f.programme.numProg like :programme" +
                    ")" +
             "AND CONCAT(l.ide12,'') like %:query% " +
             "ORDER BY l.ide12")
    List<AutocompleteDto> findIDE12sByProgramme(@Param("query") Long query, @Param("programme") String programme);


    @Transactional(readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct new fr.sacem.priam.model.domain.dto.AutocompleteDto(l.titreOeuvre) " +
                    "FROM " +
                    "LigneProgramme l " +
                    "WHERE " +
                    "l.fichier in (" +
                    " select f.id from Fichier f where f.programme.numProg like :programme" +
                    ")" +
                    "AND UPPER(l.titreOeuvre) like %:titre% " +
                    "ORDER BY l.titreOeuvre")
    List<AutocompleteDto> findTitresByProgramme(@Param("titre") String titre, @Param("programme") String programme);

    @Transactional(readOnly = true)
    @Query(value =
            "SELECT " +
                    " distinct l.utilisateur " +
                    "FROM " +
                    "LigneProgramme l " +
                    "WHERE " +
                    "l.fichier in (" +
                    " select f.id from Fichier f where f.programme.numProg like :programme" +
                    ") " +
                    "ORDER BY l.utilisateur")
    List<String> findUtilisateursByProgramme(@Param("programme") String programme);
}
