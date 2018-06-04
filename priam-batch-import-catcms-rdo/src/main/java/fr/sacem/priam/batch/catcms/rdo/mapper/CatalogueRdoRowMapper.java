package fr.sacem.priam.batch.catcms.rdo.mapper;

import fr.sacem.priam.batch.catcms.rdo.domain.CatalogueRdoCsv;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by belwidanej on 29/08/2017.
 */
public class CatalogueRdoRowMapper implements RowMapper<CatalogueRdoCsv> {


    public CatalogueRdoCsv mapRow(ResultSet rs, int rowNum) throws SQLException {
        CatalogueRdoCsv catalogueRdo = new CatalogueRdoCsv();

        catalogueRdo.setTypeCMS(rs.getString("TYPE_CMS"));
        catalogueRdo.setTypUtilGen(rs.getString("TYP_UTIL_GEN"));
        catalogueRdo.setIde12(rs.getLong("IDE12"));
        catalogueRdo.setDateCatal(rs.getDate("DATE_CATAL").toLocalDate());
        catalogueRdo.setRole(rs.getString("ROLE"));
        catalogueRdo.setParticipant(rs.getString("PARTICIPANT"));
        catalogueRdo.setTitre(rs.getString("TITRE"));
        catalogueRdo.setPourcentageDP(rs.getShort("POURCENTAGE_DP"));


        return catalogueRdo;

    }


}
