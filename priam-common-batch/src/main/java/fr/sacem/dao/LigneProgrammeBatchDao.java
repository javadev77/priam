package fr.sacem.dao;

import com.google.common.base.Joiner;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by fandis on 15/12/2017.
 */
@Repository
public class LigneProgrammeBatchDao {

    private JdbcTemplate jdbcTemplate;

    private String nomTableLigneProgramme;

    public LigneProgrammeBatchDao() {

    }

    public void deleteDedoublonnage(String numProg) {
        String selectSql = "SELECT l2.id as id " +
        "FROM PRIAM_LIGNE_PROGRAMME_CMS l2 " +
                "INNER JOIN PRIAM_FICHIER f ON l2.ID_FICHIER=f.ID " +
                "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG "+
                "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
                "AND l2.id not IN (SELECT temp.id3 "+
                "from ( "+
                "select min(l.id) as id3 "+
                "from PRIAM_LIGNE_PROGRAMME_CMS l "+
                "INNER JOIN PRIAM_FICHIER f ON l.ID_FICHIER=f.ID "+
                "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG "+
                "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
                "group by l.ide12, l.cdeFamilTypUtilOri) as  temp) ";



        List<Long> ids = jdbcTemplate.query(selectSql,
                (resultSet, i) -> resultSet.getLong("id"), numProg, numProg);

        if(ids != null && !ids.isEmpty()) {
            String sql =  "DELETE FROM " + this.nomTableLigneProgramme + " WHERE id IN (" + Joiner.on(",").join(ids).toString() + ") ";
            jdbcTemplate.update(sql);
        }
    }



    public Double countSommePoints(String numProg) {
        String sql =  "SELECT sum(points) as points from ( SELECT " +
                "sum(l.mt) points, l.ide12 " +
                "FROM " +
                "PRIAM_LIGNE_PROGRAMME_CMS l inner join PRIAM_FICHIER as f " +
                "on l.ID_FICHIER=f.ID " +
                "WHERE " +
                "f.numProg = ? " +
                "GROUP BY l.ide12) result  ";
        Double points = jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getDouble("points"), numProg);

        return points;
    }

    public Long countNbOeuvres(String numProg) {
        String sql =  "SELECT " +
                "count(l.id) as nbOeuvres " +
                "FROM " +
                "PRIAM_LIGNE_PROGRAMME_CMS l inner join PRIAM_FICHIER as f " +
                "on l.ID_FICHIER=f.ID " +
                "WHERE " +
                "f.numProg = ? ";
        Long nbOeuvres = jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("nbOeuvres"), numProg);

        return nbOeuvres;
    }


    public Long countNbOeuvresCatalogue() {
        String sql =  "SELECT " +
                "count(cat.id) as nbOeuvres " +
                "FROM PRIAM_CATALOGUE_OCTAV cat ";
        Long nbOeuvres = jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("nbOeuvres"));

        return nbOeuvres;
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

