package fr.sacem.priam.batch.common.dao;

import fr.sacem.priam.batch.common.domain.Programme;
import fr.sacem.priam.batch.common.util.DateTimeUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * Created by fandis on 15/12/2017.
 */
@Repository
public class ProgrammeBatchDao {

    private JdbcTemplate jdbcTemplate;

    private String nomTableLigneProgramme;

    public ProgrammeBatchDao() {

    }

    @Transactional(value="transactionManager")
    public void majStattutEligibilite(String numProg, String statut) {
        String sql =  "UPDATE PRIAM_PROGRAMME SET STATUT_ELIGIBILITE = ? WHERE NUMPROG = ?";
        jdbcTemplate.update(sql, stmt -> {
            stmt.setString(1, statut);
            stmt.setString(2, numProg);
        });
    }

    @Transactional(value="transactionManager")
    public void majStattutProgramme(String numProg, String statut) {
        String sql =  "UPDATE PRIAM_PROGRAMME SET STATUT_PROG_CODE = ? WHERE NUMPROG = ?";
        jdbcTemplate.update(sql, stmt -> {
            stmt.setString(1, statut);
            stmt.setString(2, numProg);
        });
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String getNomTableLigneProgramme() {
        return nomTableLigneProgramme;
    }

    public void setNomTableLigneProgramme(String nomTableLigneProgramme) {
        this.nomTableLigneProgramme = nomTableLigneProgramme;
    }

    @Transactional(value="transactionManager")
    public void updateProgramme(String numProg, String user) {

        String sql =  "UPDATE PRIAM_PROGRAMME SET STATUT_PROG_CODE=?, USERMAJ=?, DATMAJ=? WHERE NUMPROG = ?";
        jdbcTemplate.update(sql, stmt -> {
            stmt.setString(1, "CREE");
            stmt.setString(2, user);
            stmt.setTimestamp(3, new DateTimeUtils().getCurrentTimeStamp());
            stmt.setString(4, numProg);
        });
    }

    @Transactional(value="transactionManager")
    public Programme findByNumProg(String numProg) {
        String sql = "SELECT p.NUMPROG as NUMPROG, p.CDETYPUTIL as CDETYPUTIL, p.TYPE_REPART " +
                    "FROM PRIAM_PROGRAMME p " +
                    "WHERE p.NUMPROG=? ";

        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> {

            Programme programme = new Programme();
            programme.setNumProg(resultSet.getString("NUMPROG"));
            programme.setTypeUtilisation(resultSet.getString("CDETYPUTIL"));
            programme.setTypeRepart(resultSet.getString("TYPE_REPART"));

            return programme;

        }, numProg);
    }
}

