package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Journal;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
=======

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
>>>>>>> d31a5ac... PRIAM-164 : appel des logs
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by monfleurm on 26/01/2018.
 */
@Transactional
public interface JournalDao extends JpaRepository<Journal,Long> {
    /*@Transactional(readOnly = true)
    @Query(value="SELECT new fr.sacem.priam.model.domain.Journal(j.numProg, j.evenement, j.ide12, j.date, j.utilisateur, av.SITUATION, ap.SITUATION) " +
            "FROM JournalEvenement j "+
            "INNER JOIN PRIAM_SITUATION_AVANT av ON j.id=av.ID_EVENEMENT " +
            "INNER JOIN PRIAM_SITUATION_APRES ap ON j.id=ap.ID_EVENEMENT " +
            "WHERE j.numProg = :numProg " +
            "ORDER BY j.date")
    Page<Journal> findByNumProg(@Param("numProg") String numProg, Pageable pageable);*/
}

