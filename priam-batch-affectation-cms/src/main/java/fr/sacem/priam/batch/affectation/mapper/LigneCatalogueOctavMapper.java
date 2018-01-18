package fr.sacem.priam.batch.affectation.mapper;

import fr.sacem.domain.LigneCatalogueOctav;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LigneCatalogueOctavMapper implements RowMapper<LigneCatalogueOctav> {

    @Override
    public LigneCatalogueOctav mapRow(ResultSet rs, int rowNum) throws SQLException {
        LigneCatalogueOctav ligneCatalogueOctav = new LigneCatalogueOctav();
        ligneCatalogueOctav.setTypeCMS(rs.getString("TYPE_CMS"));
        ligneCatalogueOctav.setIde12(rs.getLong("IDE12"));
        ligneCatalogueOctav.setTitre(rs.getString("TITRE"));
        ligneCatalogueOctav.setRole(rs.getString("ROLE"));
        ligneCatalogueOctav.setParticipant(rs.getString("PARTICIPANT"));
        ligneCatalogueOctav.setNumProg(rs.getString("NUMPROG"));
        ligneCatalogueOctav.setNbrDif(rs.getInt("NBRDIF"));
        return ligneCatalogueOctav;
    }

}
