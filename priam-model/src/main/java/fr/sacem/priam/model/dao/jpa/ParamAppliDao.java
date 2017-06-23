package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.ParamAppli;
import fr.sacem.priam.model.domain.Programme;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fandis on 22/06/2017.
 */
public interface ParamAppliDao extends JpaRepository<ParamAppli, Long> {
}
