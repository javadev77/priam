package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.FichierFelixLog;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by benmerzoukah on 22/09/2017.
 */
@Lazy
public interface FichierFelixLogDao extends JpaRepository<FichierFelixLog, Long> {
    
}
