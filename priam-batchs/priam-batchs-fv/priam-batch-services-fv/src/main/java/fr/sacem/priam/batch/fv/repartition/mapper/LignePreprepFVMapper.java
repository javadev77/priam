package fr.sacem.priam.batch.fv.repartition.mapper;

import fr.sacem.priam.batch.fv.repartition.domain.LignePreprepFV;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LignePreprepFVMapper extends LignePreprepFVCopyMapper {

    @Override
    public LignePreprepFV mapRow(ResultSet rs, int i) throws SQLException {

        LignePreprepFV lignePreprepFV = super.mapRow(rs, i);

        lignePreprepFV.setCdeTypDrtSacem(rs.getString("cdeTypDrtSacem"));
        lignePreprepFV.setCoadPayer(rs.getLong("coadPayer"));
        lignePreprepFV.setIdSteAd(rs.getLong("idSteAd"));
        lignePreprepFV.setRolAd(rs.getString("rolAd"));
        lignePreprepFV.setCleAd(rs.getDouble("cleAd"));
        lignePreprepFV.setCdeTypProtec(rs.getString("cdeTypProtec"));
        lignePreprepFV.setCoadOriEdtr(rs.getLong("coadOriEdtr"));
        lignePreprepFV.setIdSteOriEdtr(rs.getLong("idSteOriEdtr"));
        lignePreprepFV.setNumPers(rs.getLong("numPers"));
        lignePreprepFV.setNumCatal(rs.getLong("numCatal"));
        lignePreprepFV.setPoints(rs.getDouble("points"));

        return lignePreprepFV;
    }
}
