package fr.sacem.util.mapper.importPenef;

import fr.sacem.domain.LigneProgramme;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by belwidanej on 29/08/2017.
 */
public class PriamLigneProgrammeCmsAntSQLMapper extends PriamLigneProgrammeSQLMapper {


    @Override
    public LigneProgramme mapRow(ResultSet rs, int rowNum) throws SQLException {
        LigneProgramme ligneProgramme = super.mapRow(rs, rowNum);
        ligneProgramme.setRionAnt(rs.getString("RION_N_ANT"));

        return ligneProgramme;

    }


}
