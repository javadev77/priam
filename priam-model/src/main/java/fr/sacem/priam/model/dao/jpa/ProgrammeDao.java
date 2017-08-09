package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutProgramme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by fandis on 14/06/2017.
 */
public interface ProgrammeDao extends JpaRepository<Programme, String> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Programme p SET p.statut =:statusProgramme WHERE p.numProg = :numProg")
    void majProgrammeStatusToCree(@Param("numProg") String numProg, @Param("statusProgramme") StatutProgramme statusProgramme);


    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value =
            "SELECT " +
            "count(duree), ajout" +
            " from ( " +
            "       SELECT " +
            "           count(l.durDif) duree, l.ide12, l.ajout " +
            "       FROM " +
            "           PRIAM_LIGNE_PROGRAMME l " +
            "       inner join " +
            "           PRIAM_FICHIER as f on l.ID_FICHIER=f.ID " +
            "       WHERE " +
            "           f.numProg = ?1 " +
            "       AND " +
            "           l.selection = 1 " +
            "       GROUP BY " +
            "           l.ide12, l.ajout" +
            "       ) result " +
            "GROUP BY ajout")
    List<Object> compterOuvreSelectionnee(@Param("numProg") String numProg);

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value =
            "SELECT " +
                    "count(duree), ajout" +
                    " from ( " +
                    "       SELECT " +
                    "           count(l.durDif) duree, l.ide12, l.ajout " +
                    "       FROM " +
                    "           PRIAM_LIGNE_PROGRAMME l " +
                    "       inner join " +
                    "           PRIAM_FICHIER as f on l.ID_FICHIER=f.ID " +
                    "       WHERE " +
                    "           f.numProg = ?1 " +
                    "       GROUP BY " +
                    "           l.ide12, l.ajout" +
                    "       ) result " +
                    "GROUP BY ajout")
    List<Object> compterToutLesOeuvre(@Param("numProg") String numProg);

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value =
            "SELECT sum(duree) from ( SELECT " +
                    "sum(l.durDif) duree, l.ide12 " +
                    "FROM " +
                    "PRIAM_LIGNE_PROGRAMME l inner join PRIAM_FICHIER as f " +
                    "on l.ID_FICHIER=f.ID " +
                    "WHERE " +
                    "f.numProg = ?1 " +
                    "GROUP BY l.ide12) result ")
    Long sommeDureeDeToutLesOeuvres(@Param("numProg") String numProg);

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value =
            "SELECT sum(duree) from ( SELECT " +
                    "sum(l.durDif) duree, l.ide12 " +
                    "FROM " +
                    "PRIAM_LIGNE_PROGRAMME l inner join PRIAM_FICHIER as f " +
                    "on l.ID_FICHIER=f.ID " +
                    "WHERE " +
                    "f.numProg = ?1 " +
                    "AND l.selection = 1 " +
                    "GROUP BY l.ide12) result " +
                    " ")
    Long sommeDureeDesOeuvresSelectionnees(@Param("numProg") String numProg);

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value =
            "SELECT sum(quantite) from ( SELECT " +
                    "sum(l.nbrDif) quantite, l.ide12 " +
                    "FROM " +
                    "PRIAM_LIGNE_PROGRAMME l " +
                    "inner join PRIAM_FICHIER as f " +
                    "on l.ID_FICHIER=f.ID " +
                    "WHERE f.numProg = ?1 " +
                    "GROUP BY l.ide12) result")
    Long sommeQuantiteDeToutLesOeuvres(@Param("numProg") String numProg);

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value =
            "SELECT sum(quantite) from ( SELECT " +
                    "sum(l.nbrDif) quantite, l.ide12 " +
                    "FROM " +
                    "PRIAM_LIGNE_PROGRAMME l " +
                    "inner join PRIAM_FICHIER as f " +
                    "on l.ID_FICHIER=f.ID " +
                    "WHERE f.numProg = ?1 " +
                    "AND l.selection = 1 " +
                    "GROUP BY l.ide12) result")
    Long sommeQuantiteDesOeuvresSelectionnees(@Param("numProg") String numProg);
}
