package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.Role;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by monfleurm on 26/01/2018.
 */
@Lazy
@Transactional
public interface JournalDao extends JpaRepository<Journal,Long> {


}

