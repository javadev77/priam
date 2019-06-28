package fr.sacem.priam.batch.fv.serviceimport.mapper;

import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import fr.sacem.priam.batch.fv.export.mapper.ExportCsvMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportDataRowMapper extends ExportCsvMapper {


    @Override
    public ExportCsvDto mapRow(ResultSet resultSet, int i) throws SQLException {

        ExportCsvDto exportCsvDto = super.mapRow(resultSet, i);


        exportCsvDto.setIdFichier(resultSet.getLong("ID_FICHIER"));
        exportCsvDto.setPoints(resultSet.getDouble("points"));


        return exportCsvDto;
    }
}
