package fr.sacem.priam.batch.participants.mapper;

import fr.sacem.priam.batch.participants.domain.CatalogueCms;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogueCmsMapper implements RowMapper<CatalogueCms> {

    @Override
    public CatalogueCms mapRow(ResultSet resultSet, int i) throws SQLException {
        CatalogueCms catalogueCms = new CatalogueCms();

        catalogueCms.setId(resultSet.getLong("ID"));
        catalogueCms.setIde12(resultSet.getLong("IDE12"));
        catalogueCms.setCdeTypIde12("COCV");

        return catalogueCms;
    }
}
