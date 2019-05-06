package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.saref.SareftjLibter;
import fr.sacem.priam.model.domain.saref.SareftjLibterPK;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by belwidanej on 21/08/2017.
 */
@Lazy
@Transactional(value="transactionManager", readOnly = true)
public interface SareftjLibterDao extends JpaRepository<SareftjLibter, SareftjLibterPK> {

    //@Cacheable("sareftjLibter")
    @Query("SELECT sareftjLibter FROM SareftjLibter sareftjLibter " +
            "WHERE sareftjLibter.cdeLng = :lang " +
            "ORDER BY sareftjLibter.cdePaysIso4N")
    List<SareftjLibter> findByLang(@Param("lang") String lang);

    //@Cacheable("sareftjLibterByCodeAndLang")
    @Query("SELECT sareftjLibter FROM SareftjLibter sareftjLibter " +
            "WHERE sareftjLibter.cdePaysIso4N = :code "+
            "AND sareftjLibter.cdeLng = :lang")
    SareftjLibter findByCodeAndLang(@Param("code") Integer code, @Param("lang") String lang);
}
