package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Parametrage;
import fr.sacem.priam.model.domain.ParametragePK;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by belwidanej on 21/08/2017.
 */
@Lazy
@Transactional(readOnly = true, value = "transactionManager")
public interface ParametrageDao extends JpaRepository<Parametrage, ParametragePK> {

    @Query("SELECT parametrage FROM Parametrage parametrage " +
            "WHERE parametrage.userId like :userId")
    List<Parametrage> findByUserId(@Param("userId") String userId);

    @Query("SELECT parametrage FROM Parametrage parametrage " +
            "WHERE parametrage.userId like :userId " +
            "AND parametrage.key like :key")
    Parametrage findByKeyAndUserId(@Param("userId") String userId, @Param("key") String key);
}
