package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by monfleurm on 26/01/2018.
 */
@Transactional
public interface JournalDao extends JpaRepository<Journal,Long> {


}

