package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.LigneProgramme;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
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
    @Query(value =
            "SELECT " +
                    "new fr.sacem.priam.model.domain.dto.KeyValueDto(count(l.durDif), l.ajout)" +
                    "FROM " +
                    "LigneProgramme l join l.fichier as f " +
                    "WHERE " +
                    "   l.fichier = f.id " +
                    "AND f.programme.numProg = :numProg "+
                    "AND l.selection =true " +
                    "GROUP BY l.ajout")
    List<KeyValueDto> compterOuvreSelectionnee(@Param("numProg") String numProg);

    @Transactional(readOnly = true)
    @Query(value =
            "SELECT " +
                    "new fr.sacem.priam.model.domain.dto.KeyValueDto(count(l.durDif), l.ajout) " +
                    "FROM " +
                    "LigneProgramme l join l.fichier as f " +
                    "WHERE " +
                    "   l.fichier = f.id " +
                    "AND f.programme.numProg = :numProg " +
                    "GROUP BY l.ajout")
    List<KeyValueDto> compterToutLesOeuvre(@Param("numProg") String numProg);

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
                    "count(l.ide12) quantite, l.ide12 " +
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
                    "count(l.ide12) quantite, l.ide12 " +
                    "FROM " +
                    "PRIAM_LIGNE_PROGRAMME l " +
                    "inner join PRIAM_FICHIER as f " +
                    "on l.ID_FICHIER=f.ID " +
                    "WHERE f.numProg = ?1 " +
                    "AND l.selection = 1 " +
                    "GROUP BY l.ide12) result")
    Long sommeQuantiteDesOeuvresSelectionnees(@Param("numProg") String numProg);
}
