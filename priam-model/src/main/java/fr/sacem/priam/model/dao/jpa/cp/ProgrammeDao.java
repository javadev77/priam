package fr.sacem.priam.model.dao.jpa.cp;

import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
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

    @Transactional(readOnly = true)
    @Query(value = "SELECT pr " +
            "FROM Programme AS pr " +
            "WHERE pr.numProg = :numProg ")
    Programme findByNumProg(@Param("numProg") String numProg);

}
