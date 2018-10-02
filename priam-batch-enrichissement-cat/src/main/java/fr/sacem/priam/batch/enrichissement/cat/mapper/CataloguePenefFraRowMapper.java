package fr.sacem.priam.batch.enrichissement.cat.mapper;

//import fr.sacem.priam.batch.enrichissement.cat.domain.CataloguePenefFra;

import fr.sacem.priam.batch.common.domain.CataloguePenefFra;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by belwidanej on 29/08/2017.
 */
public class CataloguePenefFraRowMapper implements RowMapper<CataloguePenefFra> {


    public CataloguePenefFra mapRow(ResultSet rs, int rowNum) throws SQLException {
        CataloguePenefFra cataloguePenefFra = new CataloguePenefFra();

        cataloguePenefFra.setIdFichier(rs.getLong("ID_FICHIER"));
        cataloguePenefFra.setIde12(rs.getLong("IDE12"));
        cataloguePenefFra.setCdeTypIde12(rs.getString("CDE_TYP_IDE12"));
        cataloguePenefFra.setCdeTypUtilOri(rs.getString("CDE_TYP_UTIL_ORI"));
        cataloguePenefFra.setRoleParticipant1(rs.getString("ROLE_PARTICIPANT1"));
        cataloguePenefFra.setNomParticipant1(rs.getString("NOM_PARTICIPANT1"));
        cataloguePenefFra.setTitreOeuvre(rs.getString("TITRE_OEUVRE"));
        cataloguePenefFra.setTypeCMS(rs.getString("TYPE_CMS"));

        return cataloguePenefFra;

    }


}
