package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.ParamAppli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by fandis on 22/06/2017.
 */
public interface ParamAppliDao extends JpaRepository<ParamAppli, String> {
    @Query(value="SELECT  p from ParamAppli p where p.cdeParam=:cdeParam")
    ParamAppli getParam(@Param("cdeParam") String cdeParam);
}
