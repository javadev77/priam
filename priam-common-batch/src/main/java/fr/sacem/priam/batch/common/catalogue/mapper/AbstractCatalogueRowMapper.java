package fr.sacem.priam.batch.common.catalogue.mapper;

import fr.sacem.priam.batch.common.domain.CatalogueRdoCsv;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractCatalogueRowMapper implements RowMapper<CatalogueRdoCsv> {

    protected CatalogueRdoCsv catalogueRdo = new CatalogueRdoCsv();

    @Override
    public CatalogueRdoCsv mapRow(ResultSet resultSet, int i) throws SQLException {
        catalogueRdo.setTypeCMS(resultSet.getString("TYPE_CMS"));
        return catalogueRdo;
    }
}
