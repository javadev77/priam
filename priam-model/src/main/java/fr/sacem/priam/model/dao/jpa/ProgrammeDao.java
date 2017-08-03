package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.LigneProgramme;
import fr.sacem.priam.model.domain.StatutProgramme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


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
                    "count(l.durDif) " +
                    "FROM " +
                    "LigneProgramme l ,Programme p " +
                    "WHERE l.numProg=p.numProg " +
                    "AND p.numProg=:numProg "+
                    "AND l.selection =true ")
    Long findDureeByProgrammeValide(@Param("numProg") String numProg);

    @Transactional(readOnly = true)
    @Query(value =
            "SELECT " +
                    "count(l.durDif) " +
                    "FROM " +
                    "LigneProgramme l ,Programme p " +
                    "WHERE l.numProg=p.numProg " +
                    "AND p.numProg=:numProg ")
    Long findDureeByProgrammeEnCours(@Param("numProg") String numProg);
}
