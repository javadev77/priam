package fr.sacem.priam.batch.felix.mapper;

import fr.sacem.priam.batch.felix.domain.FelixData;
import fr.sacem.priam.model.domain.LignePreprep;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by benmerzoukah on 23/07/2018.
 */
public class LignePreprepRowMapper implements RowMapper<LignePreprep> {

    @Override
    public LignePreprep mapRow(ResultSet rs, int i) throws SQLException {
        LignePreprep lignePreprep = new LignePreprep();


        //1.
        lignePreprep.setCdeCisac(rs.getString("cdeCisac"));
        lignePreprep.setCdeTer(rs.getInt("cdeTer"));
        lignePreprep.setRionEffet(rs.getInt("rionEffet"));
        lignePreprep.setCdeFamilTypUtil(rs.getString("cdeFamilTypUtil"));

        //2.
        lignePreprep.setCdeModFac(rs.getString("cdeModFac"));
        lignePreprep.setNumProg(rs.getString("numProg"));
        lignePreprep.setCdeUtil(rs.getString("cdeUtil"));
        lignePreprep.setCdeTypUtil(rs.getString("cdeTypUtil"));
        lignePreprep.setCdeTypProg(rs.getString("cdeTypProg"));

        //3.
        lignePreprep.setCdeCompl(rs.getString("cdeCompl"));
        lignePreprep.setLibProg(rs.getString("libProg"));
        lignePreprep.setDatDbtProg(getDateFromTimestamp(rs.getTimestamp("datDbtProg")));
        lignePreprep.setDatFinProg(getDateFromTimestamp(rs.getTimestamp("datFinProg")));

        //4.
        lignePreprep.setCdeGreDif(rs.getString("cdeGreDif"));
        lignePreprep.setCdeModDif(rs.getString("cdeModDif"));
        lignePreprep.setCdeTypIde12(rs.getString("cdeTypIde12"));
        lignePreprep.setIde12(rs.getLong("ide12"));

        //5.
        lignePreprep .setDurDif(rs.getLong("durDif"));
        lignePreprep .setNbrDif(rs.getLong("nbrDif"));
        lignePreprep .setMt(rs.getDouble("mt"));
        lignePreprep .setCtna(rs.getString("ctna"));
        lignePreprep .setParamCoefHor(rs.getString("paramCoefHor"));

        //6.
        lignePreprep.setDurDifCtna(rs.getLong("durDifCtna"));
        lignePreprep.setCdeLng(rs.getString("cdeLng"));
        lignePreprep.setIndDoubSsTit(rs.getString("indDoubSsTit"));
        lignePreprep.setTax(rs.getDouble("tax"));


        return lignePreprep;
    }

    private Date getDateFromTimestamp(Timestamp ts) throws SQLException {
        if(ts == null) return null;
        return new Date(ts.getTime());
    }
}
