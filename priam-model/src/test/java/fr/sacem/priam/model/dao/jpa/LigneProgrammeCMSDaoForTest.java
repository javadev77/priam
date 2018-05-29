package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCMSDao;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LigneProgrammeCMSDaoForTest extends LigneProgrammeCMSDao {

    @Transactional
    @Query(value="SELECT l " +
            "FROM LigneProgrammeCMS l join l.fichier as f "+
            "WHERE l.fichier = f.id " +
            "AND f.programme.numProg = :numProg ")
    List<LigneProgrammeCMS> findLigneProgrammeByNumProg(@Param("numProg") String numProg);
}
