package fr.sacem.priam.batch.purge.catcms.mapper;

import fr.sacem.priam.batch.common.domain.CatalogueCms;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogueCMSRowMapper implements RowMapper<CatalogueCms> {
    @Override
    public CatalogueCms mapRow(ResultSet resultSet, int i) throws SQLException {
        CatalogueCms catalogueCms = new CatalogueCms();
        catalogueCms.setId(resultSet.getLong("ID"));
        return catalogueCms;
    }
}
