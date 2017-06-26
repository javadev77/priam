package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.ParamAppli;
import fr.sacem.priam.model.domain.Programme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by fandis on 14/06/2017.
 */
public interface ProgrammeDao extends JpaRepository<Programme, String> {
    //@Query(value="SELECT  p from Programme p where p.rionTheorique=:rionTheorique and p.famille=:famille and p.typeUtilisation=:typeUtilisation")
    //List<Programme> verifierRionFamilleTypeUtilisation(@Param("rionTheorique") String rionTheorique, @Param("famille") String famille, @Param("typeUtilisation") String typeUtilisation);
}
