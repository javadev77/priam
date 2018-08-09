package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.domain.catcms.JournalCatcms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by embouazzar on 09/08/2018.
 */
@Transactional
public interface JournalCatcmsDao extends JpaRepository<JournalCatcms, Long>{
}
