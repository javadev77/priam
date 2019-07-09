package fr.sacem.priam.batch.felix.mapper;

import fr.sacem.priam.batch.felix.domain.LignePreprepFV;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Precision;
import org.springframework.jdbc.core.RowMapper;

public class LignePreprepFVRowMapper implements RowMapper<LignePreprepFV> {

    @Override
    public LignePreprepFV mapRow(ResultSet rs, int i) throws SQLException {
        LignePreprepFV lignePreprepFV = new LignePreprepFV();
        lignePreprepFV.setLineNumber(i);
        if(hasColumn(rs,"typRepart")) lignePreprepFV.setTypRepart(rs.getString("typRepart"));
        if(hasColumn(rs,"cdeCisac")) lignePreprepFV.setCdeCisac(rs.getString("cdeCisac"));
        if(hasColumn(rs,"CDE_TER") || hasColumn(rs,"cdeTer")) lignePreprepFV.setCdeTer(rs.getInt("cdeTer"));
        if(hasColumn(rs,"RION_THEORIQUE") || hasColumn(rs,"rionEffet")) lignePreprepFV.setRionEffet(rs.getInt("rionEffet"));
        if(hasColumn(rs,"cdeFamilTypUtil")) lignePreprepFV.setCdeFamilTypUtil(rs.getString("cdeFamilTypUtil"));
        if(hasColumn(rs,"NUMPROG") || hasColumn(rs,"numProg")) lignePreprepFV.setNumProg(rs.getInt("numProg"));
        if(hasColumn(rs,"id")) lignePreprepFV.setId(rs.getLong("id"));
        if(hasColumn(rs,"cdeUtil")) lignePreprepFV.setCdeUtil(rs.getString("cdeUtil"));
        if(hasColumn(rs,"cdeTypUtil")) lignePreprepFV.setCdeTypUtil(rs.getString("cdeTypUtil"));
        if(hasColumn(rs,"cdeModFac")) lignePreprepFV.setCdeModFac(rs.getString("cdeModFac"));
        if(hasColumn(rs,"cdeTypProg")) lignePreprepFV.setCdeTypProg(rs.getString("cdeTypProg"));
        if(hasColumn(rs,"cdeCompl")) lignePreprepFV.setCdeCompl(rs.getString("cdeCompl"));
        if(hasColumn(rs,"NOM") || hasColumn(rs,"libProg")) lignePreprepFV.setLibProg(rs.getString("libProg"));
        if(hasColumn(rs,"DATE_DBT_PRG") || hasColumn(rs,"datDbtProg")) lignePreprepFV.setDatDbtProg(rs.getDate("datDbtProg"));
        if(hasColumn(rs,"DATE_FIN_PRG") || hasColumn(rs,"datFinProg")) lignePreprepFV.setDatFinProg(rs.getDate("datFinProg"));
        if(hasColumn(rs,"ide12")) lignePreprepFV.setIde12(rs.getLong("ide12"));
        if(hasColumn(rs,"cdeTypIde12")) lignePreprepFV.setCdeTypIde12(rs.getString("cdeTypIde12"));
        if(hasColumn(rs,"datsitu")) lignePreprepFV.setDatSitu(rs.getDate("datsitu"));
        if(hasColumn(rs,"datconslt")) lignePreprepFV.setDatConslt(rs.getDate("datconslt"));
        if(hasColumn(rs,"durDif")) lignePreprepFV.setDurDif(rs.getLong("durDif"));
        if(hasColumn(rs,"nbrDif")) lignePreprepFV.setNbrDif(rs.getLong("nbrDif"));
        if(hasColumn(rs,"typMt")) lignePreprepFV.setTypMt(rs.getString("typMt"));
        if(hasColumn(rs,"mt")) lignePreprepFV.setMt(Precision.round(rs.getDouble("mt"),2));
        if(hasColumn(rs, "cdeTypDrtSacem") || hasColumn(rs, "TYPE_DROIT")) lignePreprepFV.setCdeTypDrtSacem(rs.getString("cdeTypDrtSacem"));
        /*if(hasColumn(rs, "TYPE_DROIT")) lignePreprepFV.setCdeTypDrtSacem(rs.getString("TYPE_DROIT"));*/
        if(hasColumn(rs, "coadPayer")) lignePreprepFV.setCoadPayer(rs.getLong("coadPayer"));
        if(hasColumn(rs, "idSteAd")) lignePreprepFV.setIdSteAd(rs.getLong("idSteAd"));
        if(hasColumn(rs, "rolAd")) lignePreprepFV.setRolAd(rs.getString("rolAd"));
        if(hasColumn(rs, "cleAd")) lignePreprepFV.setCleAd(Precision.round(rs.getDouble("cleAd"),8));
        if(hasColumn(rs, "cdeTypProtec")) lignePreprepFV.setCdeTypProtec(rs.getString("cdeTypProtec"));
        if(hasColumn(rs, "coadOriEdtr")) lignePreprepFV.setCoadOriEdtr(rs.getLong("coadOriEdtr"));
        if(hasColumn(rs, "idSteOriEdtr")) lignePreprepFV.setIdSteOriEdtr(rs.getLong("idSteOriEdtr"));
        if(hasColumn(rs, "numPers")) lignePreprepFV.setNumPers(rs.getLong("numPers"));
        if(hasColumn(rs, "numCatal")) lignePreprepFV.setNumCatal(rs.getLong("numCatal"));
        if(hasColumn(rs, "points")) lignePreprepFV.setPoints(Precision.round(rs.getDouble("points"),4));

        return lignePreprepFV;
    }

    /**
     *
     * @param resultSet
     * @param columnName Name of column to be checked
     * @return
     * @throws SQLException
     */
    private boolean hasColumn(ResultSet resultSet, String columnName) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int coulmnCount = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= coulmnCount; i++) {
            if (StringUtils.upperCase(columnName).equals(StringUtils.upperCase(resultSetMetaData.getColumnName(i)))) {
                return true;
            }
        }
        return false;
    }
}
