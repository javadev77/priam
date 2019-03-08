package fr.sacem.priam.model.dao.jpa.fv;

import fr.sacem.priam.model.domain.fv.AyantDroit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AyantDroitFVDao extends JpaRepository<AyantDroit, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value="DELETE FROM PRIAM_AYANT_DROIT WHERE ID_FV IN (" +
        "SELECT FV.id FROM PRIAM_LIGNE_PROGRAMME_FV FV INNER JOIN PRIAM_FICHIER F ON F.NUMPROG=:numProg AND F.ID=FV.ID_FICHIER)")
    void deleteByNumProg(@Param("numProg") String numProg);
}
