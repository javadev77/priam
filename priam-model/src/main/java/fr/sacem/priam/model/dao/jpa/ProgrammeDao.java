package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Programme;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by fandis on 14/06/2017.
 */
public interface ProgrammeDao extends JpaRepository<Programme, String> {
}
