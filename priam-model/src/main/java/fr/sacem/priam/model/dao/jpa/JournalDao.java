package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by monfleurm on 26/01/2018.
 */

@Transactional(value="transactionManager")
public interface JournalDao extends JpaRepository<Journal, Long> {
    @Query(value="SELECT j " +
            "FROM Journal as j " +
            "WHERE j.numProg = :numProg ")
    Page<Journal> findJournalByNumProg(@Param("numProg") String numProg, Pageable pageable);
}

