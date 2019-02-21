package fr.sacem.priam.batch.common.fv.mapper;

import fr.sacem.priam.batch.common.domain.AyantDroit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AyantDroitMapper implements RowMapper<AyantDroit> {

    @Override
    public AyantDroit mapRow(ResultSet resultSet, int i) throws SQLException {

        AyantDroit ayantDroit = new AyantDroit();
        ayantDroit.setId(resultSet.getLong("ID"));
        ayantDroit.setNumPers(resultSet.getLong("NUMPERS"));
        return ayantDroit;
    }
}
