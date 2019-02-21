package fr.sacem.priam.batch.fv.export.mapper;

import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExportCsvMapper implements RowMapper<ExportCsvDto> {
    @Override
    public ExportCsvDto mapRow(ResultSet resultSet, int i) throws SQLException {

        ExportCsvDto exportCsvDto = new ExportCsvDto();
        exportCsvDto.setCdeFamilTypUtil(resultSet.getString("cdeFamilTypUtil"));
        exportCsvDto.setCdeTypUtil(resultSet.getString("cdeTypUtil"));
        exportCsvDto.setNumProg(resultSet.getLong("numProg"));
        exportCsvDto.setRionEffet(resultSet.getString("rionEffet"));
        exportCsvDto.setIde12(resultSet.getString("ide12"));
        exportCsvDto.setCdeTypIde12(resultSet.getString("cdeTypIde12"));
        exportCsvDto.setIde12RepCoad(resultSet.getLong("IDE12REPCOAD"));
        exportCsvDto.setCdeTypIde12RepCoad(resultSet.getString("CDETYPIDE12REPCOAD"));
        exportCsvDto.setCoad(resultSet.getLong("COAD"));
        exportCsvDto.setNumPers(resultSet.getLong("NUMPERS"));
        exportCsvDto.setNumCatal(resultSet.getLong("NUMCATAL"));
        exportCsvDto.setIdSteAd(resultSet.getString("IDSTEAD"));
        exportCsvDto.setRolAd(resultSet.getString("ROLAD"));
        exportCsvDto.setCleAd(resultSet.getDouble("CLEAD"));
        exportCsvDto.setCdeTypProtect(resultSet.getString("CDETYPPROTEC"));
        exportCsvDto.setCoadOriEdtr(resultSet.getInt("COADORIEDTR"));
        exportCsvDto.setIdSteOriEdtr(resultSet.getString("IDSTEORIEDTR"));
        exportCsvDto.setTax(resultSet.getString("tax"));
        exportCsvDto.setDurDif(resultSet.getString("durDif"));
        exportCsvDto.setNbrDif(resultSet.getString("nbrDif"));
        exportCsvDto.setTypMt(resultSet.getString("typMt"));
        exportCsvDto.setMt(resultSet.getDouble("mt"));
        exportCsvDto.setGenreOeuvre(resultSet.getString("genreOeuvre"));
        exportCsvDto.setTitreOeuvre(resultSet.getString("titreOeuvre"));
        exportCsvDto.setDureeDeposee(resultSet.getInt("dureeDeposee"));
        exportCsvDto.setTaxOri(resultSet.getDouble("taxOri"));
        exportCsvDto.setPaysOri(resultSet.getString("paysOri"));
        exportCsvDto.setIndicRepart(resultSet.getInt("indicRepart"));
        exportCsvDto.setNom(resultSet.getString("NOM"));
        exportCsvDto.setPrenom(resultSet.getString("PRENOM"));
        exportCsvDto.setIndicSacem(resultSet.getInt("INDICSACEM"));
        exportCsvDto.setSousRole(resultSet.getString("SOUS_ROLE"));
        exportCsvDto.setAnneeNaissance(resultSet.getInt("ANNEE_NAISSANCE"));
        exportCsvDto.setAnneeDeces(resultSet.getInt("ANNEE_DECES"));
        exportCsvDto.setIndicRepart(resultSet.getInt("INDICDRTPERCUS"));


        return exportCsvDto;
    }
}
