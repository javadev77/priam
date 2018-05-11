package fr.sacem.priam.batch.affectation.mapper;

import fr.sacem.priam.batch.common.domain.LigneCatalogueOctav;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogueOctavMapper implements RowMapper<LigneCatalogueOctav> {

    @Override
    public LigneCatalogueOctav mapRow(ResultSet rs, int rowNum) throws SQLException {
        LigneCatalogueOctav ligneCatalogueOctav = new LigneCatalogueOctav();
        ligneCatalogueOctav.setTypeCMS(rs.getString("TYPE_CMS"));
        ligneCatalogueOctav.setIde12(rs.getLong("IDE12"));
        ligneCatalogueOctav.setTitre(rs.getString("TITRE"));
        ligneCatalogueOctav.setRole(rs.getString("ROLE"));
        ligneCatalogueOctav.setParticipant(rs.getString("PARTICIPANT"));

        return ligneCatalogueOctav;
    }
}
