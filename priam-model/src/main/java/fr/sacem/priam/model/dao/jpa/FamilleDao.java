package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Famille;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Lazy
@Transactional(readOnly = true)
public interface FamilleDao extends JpaRepository<Famille, String> {
}
