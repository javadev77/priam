package fr.sacem.priam.batch.affectation.mapper;

import fr.sacem.priam.batch.common.domain.LigneCatalogueOctav;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LigneCatalogueOctavMapper extends CatalogueOctavMapper {

    @Override
    public LigneCatalogueOctav mapRow(ResultSet rs, int rowNum) throws SQLException {

        LigneCatalogueOctav ligneCatalogueOctav = super.mapRow(rs, rowNum);

        ligneCatalogueOctav.setNumProg(rs.getString("NUMPROG"));
        ligneCatalogueOctav.setNbrDif(rs.getInt("NBRDIF"));

        return ligneCatalogueOctav;
    }

}
