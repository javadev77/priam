package fr.sacem.priam.model.dao.jpa.fv;

import fr.sacem.priam.model.domain.fv.ExportProgrammeFV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ExportProgrammeFVDao extends JpaRepository<ExportProgrammeFV, Long> {

    @Transactional(readOnly = true)
    @Query(value ="SELECT e.filename FROM ExportProgrammeFV e where e.programme.numProg =:numProg ")
    String getFilepathByNumProg(@Param("numProg") String numProg);
}
