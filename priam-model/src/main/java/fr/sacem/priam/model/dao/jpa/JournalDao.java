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
    /*@Transactional(value="transactionManager", readOnly = true)
    @Query(value="SELECT new fr.sacem.priam.model.domain.Journal(j.numProg, j.evenement, j.ide12, j.date, j.utilisateur, av.SITUATION, ap.SITUATION) " +
            "FROM JournalEvenement j "+
            "INNER JOIN PRIAM_SITUATION_AVANT av ON j.id=av.ID_EVENEMENT " +
            "INNER JOIN PRIAM_SITUATION_APRES ap ON j.id=ap.ID_EVENEMENT " +
            "WHERE j.numProg = :numProg " +
            "ORDER BY j.date")
    Page<Journal> findByNumProg(@Param("numProg") String numProg, Pageable pageable);*/

   @Transactional(value="transactionManager",  readOnly = true)
    //@Query(value="SELECT new fr.sacem.priam.model.domain.dto.JournalDto(j.numProg, j.evenement, j.ide12, j.date, j.utilisateur) " +
    @Query(value="SELECT j " +
            "FROM Journal as j " +
            "WHERE j.numProg = :numProg ")
    Page<Journal> findJournalByNumProg(@Param("numProg") String numProg, Pageable pageable);
}

