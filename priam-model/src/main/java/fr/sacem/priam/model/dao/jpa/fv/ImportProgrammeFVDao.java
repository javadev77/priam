package fr.sacem.priam.model.dao.jpa.fv;

import fr.sacem.priam.model.domain.fv.ImportProgrammeFV;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Habib BENMERZOUKA
 */
public interface ImportProgrammeFVDao extends JpaRepository<ImportProgrammeFV, Long> {


    @Query("SELECT ifv from ImportProgrammeFV ifv inner join ifv.programme prg where prg.numProg=:numProg and ifv.statutImportProgramme = 'EN_COURS'")
    @Transactional(readOnly = true, value = "transactionManager")
    ImportProgrammeFV getFichierImporte(@Param("numProg") String numProg);


    @Transactional(readOnly =true)
    @Query(nativeQuery = true, value="SELECT FL.LOG from  PRIAM_IMPORT_PROGRAMME_FV_LOG FL " +
                                     "INNER JOIN PRIAM_IMPORT_PROGRAMME_FV IFV ON IFV.ID=FL.ID_FICHIER WHERE IFV.NUMPROG = ?1 AND IFV.STATUT='CHARGEMENT_KO' ORDER BY IFV.DATE_CREATION DESC, FL.LOG")
    Set<String> getLogs(@Param("numProg") String numProg);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value="DELETE FROM PRIAM_IMPORT_PROGRAMME_FV WHERE NUMPROG=?1 ")
    void deleteByNumProg(@Param("numProg") String numProg);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value="DELETE FROM PRIAM_IMPORT_PROGRAMME_FV_LOG WHERE ID_FICHIER IN (SELECT ID FROM PRIAM_IMPORT_PROGRAMME_FV WHERE NUMPROG=?1) ")
    void deleteLogsByNumProg(@Param("numProg") String numProg);

}
