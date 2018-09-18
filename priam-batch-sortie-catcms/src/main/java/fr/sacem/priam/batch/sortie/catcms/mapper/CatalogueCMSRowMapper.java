package fr.sacem.priam.batch.sortie.catcms.mapper;

import fr.sacem.priam.batch.common.domain.CatalogueCms;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogueCMSRowMapper implements RowMapper<CatalogueCms> {
    @Override
    public CatalogueCms mapRow(ResultSet resultSet, int i) throws SQLException {
        CatalogueCms catalogueCms = new CatalogueCms();
        catalogueCms.setId(resultSet.getLong("ID"));
        catalogueCms.setIde12(resultSet.getLong("IDE12"));
        catalogueCms.setTypeCMS(resultSet.getString("TYPE_CMS"));
        return catalogueCms;
    }
}
