package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.ProgrammeKey;
import fr.sacem.priam.model.domain.ProgrammeSequence;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by fandis on 22/06/2017.
 */
@Lazy
@Transactional(readOnly = true, value = "transactionManager")
public interface ProgrammeSequnceDao extends JpaRepository<ProgrammeSequence, ProgrammeKey> {

    @Query(value="SELECT  max(p.programmeKey.codeSequence) from ProgrammeSequence p where p.programmeKey.annee=:currentAnnee")
    String getLastElement(@Param("currentAnnee") String currentAnnee);
}
