package fr.sacem.priam.batch.common.catalogue.mapper;

import fr.sacem.priam.batch.common.domain.CatalogueRdoCsv;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractCatalogueRowMapper implements RowMapper<CatalogueRdoCsv> {



    @Override
    public CatalogueRdoCsv mapRow(ResultSet resultSet, int i) throws SQLException {
        CatalogueRdoCsv catalogueRdoCsv = new CatalogueRdoCsv();
        catalogueRdoCsv.setTypeCMS(resultSet.getString("TYPE_CMS"));
        catalogueRdoCsv.setIde12(resultSet.getLong("IDE12"));
        return catalogueRdoCsv;
    }
}
