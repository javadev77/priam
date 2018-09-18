package fr.sacem.priam.batch.generation.catalogue.mapper;

import fr.sacem.priam.batch.common.catalogue.mapper.AbstractCatalogueRowMapper;
import fr.sacem.priam.batch.common.domain.CatalogueRdoCsv;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerationRowMapper extends AbstractCatalogueRowMapper {

    @Override
    public CatalogueRdoCsv mapRow(ResultSet resultSet, int i) throws SQLException {
        CatalogueRdoCsv catalogueRdoCsv = super.mapRow(resultSet, i);
        catalogueRdoCsv.setTitre(resultSet.getString("TITRE"));
        catalogueRdoCsv.setParticipant(resultSet.getString("PARTICIPANT"));
        catalogueRdoCsv.setRole(resultSet.getString("ROLE"));
        return catalogueRdoCsv;
    }
}
