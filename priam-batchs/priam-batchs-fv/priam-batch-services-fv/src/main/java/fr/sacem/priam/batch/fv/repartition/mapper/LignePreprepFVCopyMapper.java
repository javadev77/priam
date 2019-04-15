package fr.sacem.priam.batch.fv.repartition.mapper;

import fr.sacem.priam.batch.fv.repartition.domain.LignePreprepFV;
import org.apache.commons.math3.util.Precision;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class LignePreprepFVCopyMapper implements RowMapper<LignePreprepFV> {

    @Override
    public LignePreprepFV mapRow(ResultSet rs, int i) throws SQLException {
        LignePreprepFV lignePreprepFV = new LignePreprepFV();
        lignePreprepFV.setId(rs.getLong("id"));
        lignePreprepFV.setCdeCisac(rs.getString("cdeCisac"));
        lignePreprepFV.setCdeTer(rs.getInt("cdeTer"));
        lignePreprepFV.setRionEffet(rs.getInt("rionEffet"));
        lignePreprepFV.setCdeFamilTypUtil(rs.getString("cdeFamilTypUtil"));
        lignePreprepFV.setNumProg(rs.getInt("numProg"));
        lignePreprepFV.setCdeUtil(rs.getString("cdeUtil"));
        lignePreprepFV.setCdeTypUtil(rs.getString("cdeTypUtil"));
        lignePreprepFV.setLibProg(rs.getString("libProg"));
        lignePreprepFV.setDatDbtProg(rs.getDate("datDbtProg"));
        lignePreprepFV.setDatFinProg(rs.getDate("datFinProg"));
        lignePreprepFV.setIde12(rs.getLong("ide12"));
        lignePreprepFV.setCdeTypIde12(rs.getString("cdeTypIde12"));
        lignePreprepFV.setDatSitu(rs.getDate("datsitu"));
        lignePreprepFV.setDatConslt(rs.getDate("datconslt"));
        lignePreprepFV.setDurDif(rs.getLong("durDif"));
        lignePreprepFV.setNbrDif(rs.getLong("nbrDif"));
        lignePreprepFV.setTypMt(rs.getString("typMt"));
        lignePreprepFV.setMt(Precision.round(rs.getDouble("mt"),2));
        return lignePreprepFV;
    }
}
