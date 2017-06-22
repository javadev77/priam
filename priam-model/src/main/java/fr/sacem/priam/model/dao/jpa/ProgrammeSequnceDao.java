package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.ProgrammeKey;
import fr.sacem.priam.model.domain.ProgrammeSequence;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fandis on 22/06/2017.
 */
@Lazy
@Transactional(readOnly = true)
public interface ProgrammeSequnceDao extends JpaRepository<ProgrammeSequence, ProgrammeKey> {
}
