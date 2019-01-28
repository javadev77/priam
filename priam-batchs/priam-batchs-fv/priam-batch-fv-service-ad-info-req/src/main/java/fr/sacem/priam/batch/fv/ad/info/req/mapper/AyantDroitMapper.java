package fr.sacem.priam.batch.fv.ad.info.req.mapper;

import fr.sacem.priam.batch.fv.ad.info.req.domain.AyantDroit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AyantDroitMapper implements RowMapper<AyantDroit> {

    @Override
    public AyantDroit mapRow(ResultSet resultSet, int i) throws SQLException {

        AyantDroit ayantDroit = new AyantDroit();
        ayantDroit.setId(resultSet.getLong("ID"));
        ayantDroit.setNumPers(resultSet.getLong("NUMPERS"));
        /*ayantDroit.setIde12RepCoad(resultSet.getLong("IDE12REPCOAD"));
        ayantDroit.setCdeTypIde12RepCoad(resultSet.getString("CDETYPIDE12REPCOAD"));
        ayantDroit.setRolAd(resultSet.getString("ROLAD"));
        ayantDroit.setCoad(resultSet.getLong("COAD"));
        ayantDroit.setIdSteAd(resultSet.getString("IDSTEAD"));
        ayantDroit.setCleAd(resultSet.getDouble("CLEAD"));
        ayantDroit.setCdeTypProtect(resultSet.getString("CDETYPPROTECT"));
        ayantDroit.setCoadOriEdtr(resultSet.getInt("COADORIEDTR"));
        ayantDroit.setIdSteOriEdtr(resultSet.getString("IDSTEORIEDTR"));
        ayantDroit.setNumCatal(resultSet.getLong("NUMCATAL"));
        ayantDroit.setPrenom(resultSet.getString("PRENOM"));
        ayantDroit.setNom(resultSet.getString("NOM"));
        ayantDroit.setAnneeDeNaissance(resultSet.getInt("ANNEE_NAISSANCE"));
        ayantDroit.setAnneeDeDeces(resultSet.getInt("ANNEE_DECES"));
        ayantDroit.setIndicateurSacem(resultSet.getInt("INDICSACEM") == 1 ? true : false);
        ayantDroit.setSousRole(resultSet.getString("SOUS_ROLE"));*/
        return ayantDroit;
    }
}
