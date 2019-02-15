package fr.sacem.priam.model.dao.jpa.fv;

import fr.sacem.priam.model.domain.fv.ExportProgrammeFV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ExportProgrammeFVDao extends JpaRepository<ExportProgrammeFV, Long> {

    @Transactional(readOnly = true)
    @Query(value ="SELECT e.filename FROM ExportProgrammeFV e where e.programme.numProg =:numProg ")
    String getFilepathByNumProg(@Param("numProg") String numProg);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value="INSERT INTO PRIAM_EXPORT_PROGRAMME_FV (NUMPROG, STATUT) " +
            "VALUES (?1, ?2)")
    void insertExportProgramme(@Param("numProg") String numProg, @Param("statut") String statut);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value="UPDATE PRIAM_EXPORT_PROGRAMME_FV SET FILENAME=?2, STATUT=?3, DATE_CREATION=NOW() WHERE NUMPROG =?1")
    void updateStatutExportProgramme(@Param("numProg") String numProg, @Param("fileName") String fileName, @Param("statut") String statut);

}
