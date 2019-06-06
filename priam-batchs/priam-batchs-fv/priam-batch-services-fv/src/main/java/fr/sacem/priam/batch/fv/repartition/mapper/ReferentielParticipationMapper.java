package fr.sacem.priam.batch.fv.repartition.mapper;

import fr.sacem.priam.batch.fv.repartition.domain.LignePreprepFV;
import fr.sacem.priam.batch.fv.repartition.domain.ReferentielParticipation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReferentielParticipationMapper implements RowMapper<ReferentielParticipation> {

    @Override
    public ReferentielParticipation mapRow(ResultSet rs, int i) throws SQLException {
        ReferentielParticipation referentielParticipation = new ReferentielParticipation();
        referentielParticipation.setIde12(rs.getLong("ide12"));
        referentielParticipation.setCdeTypUtil(rs.getString("cdeTypUtil"));
        referentielParticipation.setRionPaiementMax(rs.getInt("rionEffet"));
        return referentielParticipation;
    }
}
