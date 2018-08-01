package fr.sacem.priam.batch.generation.catalogue.mapper;

import fr.sacem.priam.batch.common.catalogue.mapper.AbstractCatalogueRowMapper;
import fr.sacem.priam.batch.common.domain.CatalogueRdoCsv;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogueRowMapper extends AbstractCatalogueRowMapper {

    @Override
    public CatalogueRdoCsv mapRow(ResultSet resultSet, int i) throws SQLException {
        catalogueRdo.setIde12(resultSet.getLong("IDE12"));
        return catalogueRdo;
    }
}
