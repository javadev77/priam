package fr.sacem.dao;

import fr.sacem.util.UtilFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by fandis on 15/12/2017.
 */
@Repository
public class LigneProgrammeDao {

    private JdbcTemplate jdbcTemplate;

    private String nomTableLigneProgramme;

    public LigneProgrammeDao() {

    }

    public void deleteDedoublonnage(String numProg) {
        String sql =  "DELETE FROM " + this.nomTableLigneProgramme + " WHERE id NOT IN (SELECT * from (" +
                "select min(l.id) as id " +
                "from " + this.nomTableLigneProgramme + " l" +
                "  INNER JOIN PRIAM_FICHIER f ON l.ID_FICHIER=f.ID " +
                "  INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG " +
                "WHERE p.NUMPROG=? " +
                "group by l.ide12, l.cdeFamilTypUtilOri) as  temp) ";
        jdbcTemplate.update(sql, stmt -> {
            stmt.setString(1, numProg);

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
}

