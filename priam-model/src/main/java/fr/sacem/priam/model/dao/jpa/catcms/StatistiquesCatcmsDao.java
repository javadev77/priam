package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.domain.catcms.StatistiqueCatcms;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by embouazzar on 28/09/2018.
 */
@Lazy
@Transactional
public interface StatistiquesCatcmsDao extends JpaRepository<StatistiqueCatcms, Long> {
}
