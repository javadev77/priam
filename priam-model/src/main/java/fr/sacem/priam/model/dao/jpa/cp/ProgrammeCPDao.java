package fr.sacem.priam.model.dao.jpa.cp;

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
public interface ProgrammeCPDao extends JpaRepository<Programme, String> {

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
            "           PRIAM_LIGNE_PROGRAMME_CP l " +
            "       inner join " +
            "           PRIAM_FICHIER as f on l.ID_FICHIER=f.ID " +
            "       WHERE " +
            "           f.numProg = ?1 " +
                    "AND (?2 IS NULL OR l.SEL_EN_COURS = ?2) " +
                    "AND l.idOeuvreManuel IS NULL " +
            "       GROUP BY " +
            "           l.ide12, l.ajout, l.cdeUtil" +
            "       ) result " +
            "GROUP BY ajout")
    List<Object> compterOuvres(@Param("numProg") String numProg, @Param("selection") Integer selection);

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value =
            "SELECT sum(duree) from ( SELECT " +
                    "sum(l.durDif) duree, l.ide12 " +
                    "FROM " +
                    "PRIAM_LIGNE_PROGRAMME_CP l inner join PRIAM_FICHIER as f " +
                    "on l.ID_FICHIER=f.ID " +
                    "WHERE " +
                    "f.numProg = ?1 " +
                    "AND (?2 IS NULL OR l.SEL_EN_COURS = ?2) " +
                    "AND l.idOeuvreManuel IS NULL " +
                    "GROUP BY l.ide12, l.cdeUtil) result ")
    Long calculerDureeOeuvres(@Param("numProg") String numProg, @Param("selection") Integer selection);

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value =
            "SELECT sum(quantite) from ( SELECT " +
                    "sum(l.nbrDif) quantite, l.ide12 " +
                    "FROM " +
                    "PRIAM_LIGNE_PROGRAMME_CP l " +
                    "inner join PRIAM_FICHIER as f " +
                    "on l.ID_FICHIER=f.ID " +
                    "WHERE f.numProg = ?1 " +
                    "AND (?2 IS NULL OR l.SEL_EN_COURS = ?2) " +
                    "AND l.idOeuvreManuel IS NULL " +
                    "GROUP BY l.ide12, l.cdeUtil) result")
    Long calculerQuantiteOeuvres(@Param("numProg") String numProg, @Param("selection") Integer selection);

}
