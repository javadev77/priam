package fr.sacem.priam.batch.felix.mapper;

import fr.sacem.priam.batch.felix.domain.FelixData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by benmerzoukah on 23/07/2018.
 */
public class FelixDataRowMapper implements RowMapper<FelixData> {

    @Override
    public FelixData mapRow(ResultSet rs, int i) throws SQLException {
        FelixData felixData = new FelixData();


        //1.
        felixData.setKeyLigPenel(rs.getLong("keyLigPenel"));
        felixData.setCdeCisac(rs.getString("cdeCisac"));
        felixData.setCdeTer(rs.getInt("cdeTer"));
        felixData.setRionEffet(rs.getInt("rionEffet"));
        felixData.setCdeFamilTypUtil(rs.getString("cdeFamilTypUtil"));

        //2.
        felixData.setCdeModFac(rs.getString("cdeModFac"));
        felixData.setNumProg(rs.getString("numProg"));
        felixData.setCdeUtil(rs.getString("cdeUtil"));
        felixData.setCdeTypUtil(rs.getString("cdeTypUtil"));
        felixData.setCdeTypProg(rs.getString("cdeTypProg"));

        //3.
        felixData.setCdeCompl(rs.getString("cdeCompl"));
        felixData.setLibProg(rs.getString("libProg"));
        felixData.setCompLibProg(rs.getString("compLibProg"));
        felixData.setDatDbtProg(getDateFromTimestamp(rs.getTimestamp("datDbtProg")));
        felixData.setDatFinProg(getDateFromTimestamp(rs.getTimestamp("datFinProg")));

        //4.
        felixData.setHrDbt(rs.getInt("hrDbt"));
        felixData.setHrFin(rs.getInt("hrFin"));
        felixData.setCdeGreDif(rs.getString("cdeGreDif"));
        felixData.setCdeModDif(rs.getString("cdeModDif"));
        felixData.setCdeTypIde12(rs.getString("cdeTypIde12"));
        felixData.setIde12(rs.getLong("ide12"));

        //5.
        felixData.setDatDif(getDateFromTimestamp(rs.getTimestamp("datDif")));
        felixData.setHrDif(rs.getString("hrDif"));
        felixData.setDurDif(rs.getLong("durDif"));
        felixData.setNbrDif(rs.getLong("nbrDif"));
        felixData.setMt(rs.getDouble("mt"));
        felixData.setCtna(rs.getString("ctna"));
        felixData.setParamCoefHor(rs.getString("paramCoefHor"));

        //6.
        felixData.setDurDifCtna(rs.getLong("durDifCtna"));
        felixData.setCdeLng(rs.getString("cdeLng"));
        felixData.setIndDoubSsTit(rs.getString("indDoubSsTit"));
        felixData.setTax(rs.getDouble("tax"));


        return felixData;
    }

    private String getDateFromTimestamp(Timestamp ts) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        if(ts == null) return null;
        return formatter.format(new Date(ts.getTime()));
    }
}
