package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.LibelleTypeUtilisation;
import fr.sacem.priam.model.domain.LibelleTypeUtilisationPK;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Lazy
@Transactional(readOnly = true)
public interface LibelleTypeUtilisationDao extends JpaRepository<LibelleTypeUtilisation, LibelleTypeUtilisationPK> {
    
    @Cacheable("typeUtilisation")
    List<LibelleTypeUtilisation> findByLang(String lang);
}
