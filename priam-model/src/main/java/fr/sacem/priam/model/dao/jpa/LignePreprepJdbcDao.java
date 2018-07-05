package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.LignePreprep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.Date;
import java.util.List;

/**
 * Created by benmerzoukah on 12/06/2018.
 */
@Component
public class LignePreprepJdbcDao {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public void insertLignesPreprep(List<LignePreprep> preprepList) {

        String sql = "INSERT INTO PRIAM_LIGNE_PREPREP (" +
                "cdeCisac, cdeTer, rionEffet, cdeFamilTypUtil, " +
                "cdeModFac, numProg, cdeUtil, cdeTypUtil, cdeTypProg, " +
                "cdeCompl, libProg, compLibProg, datDbtProg, datFinProg, " +
                "hrDbt, hrFin, cdeGreDif, cdeModDif, cdeTypIde12, ide12, " +
                "datDif, hrDif, durDif, nbrDif, mt, ctna, paramCoefHor, " +
                "durDifCtna, cdeLng, indDoubSsTit, tax) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, preprepList, 100,  (ps, lignePreprep) -> {
            ps.setString(1, lignePreprep.getCdeCisac());
            ps.setInt(2, lignePreprep.getCdeTer() != null ? lignePreprep.getCdeTer() : 0);
            ps.setInt(3, lignePreprep.getRionEffet());
            ps.setString(4, lignePreprep.getCdeFamilTypUtil());

            ps.setString(5, lignePreprep.getCdeModFac());
            ps.setString(6, lignePreprep.getNumProg());
            ps.setString(7, lignePreprep.getCdeUtil());
            ps.setString(8, lignePreprep.getCdeTypUtil());
            ps.setString(9, lignePreprep.getCdeTypProg());

            ps.setString(10, lignePreprep.getCdeCompl());
            ps.setString(11, lignePreprep.getLibProg());
            ps.setString(12, lignePreprep.getCompLibProg());
            ps.setTimestamp(13, getCurrentTimeStamp(lignePreprep.getDatDbtProg()));
            ps.setTimestamp(14, getCurrentTimeStamp(lignePreprep.getDatFinProg()));

            ps.setInt(15, lignePreprep.getHrDbt() != null ? lignePreprep.getHrDbt() : 0);
            ps.setInt(16, lignePreprep.getHrFin() != null ? lignePreprep.getHrFin() : 0 );
            ps.setString(17, lignePreprep.getCdeGreDif());
            ps.setString(18, lignePreprep.getCdeModDif());
            ps.setString(19, lignePreprep.getCdeTypIde12());
            ps.setLong(20, lignePreprep.getIde12());

            ps.setTimestamp(21, getCurrentTimeStamp(lignePreprep.getDatDif()));
            ps.setString(22, lignePreprep.getHrDif());
            if(lignePreprep.getDurDif() != null) {
                ps.setLong(23, lignePreprep.getDurDif());
            } else {
                ps.setNull(23, Types.INTEGER);
            }

            if(lignePreprep.getNbrDif() != null) {
                ps.setLong(24, lignePreprep.getNbrDif());
            } else {
                ps.setNull(24, Types.INTEGER);
            }

            if(lignePreprep.getMt() != null) {
                ps.setDouble(25, lignePreprep.getMt());
            } else {
                ps.setNull(25, Types.DOUBLE);
            }


            ps.setString(26, lignePreprep.getCtna());
            ps.setString(27, lignePreprep.getParamCoefHor());

            if(lignePreprep.getDurDifCtna() != null) {
                ps.setLong(28, lignePreprep.getDurDifCtna());
            } else {
                ps.setNull(28, Types.INTEGER);
            }

            ps.setString(29, lignePreprep.getCdeLng());
            ps.setString(30, lignePreprep.getIndDoubSsTit());

            if( lignePreprep.getTax() != null) {
                ps.setDouble(31, lignePreprep.getTax());
            } else {
                ps.setNull(28, Types.DOUBLE);
            }


        });

    }

    public java.sql.Timestamp getCurrentTimeStamp(Date date) {
        if(date == null)
            return null;
        return new java.sql.Timestamp(date.getTime());
    }
}
