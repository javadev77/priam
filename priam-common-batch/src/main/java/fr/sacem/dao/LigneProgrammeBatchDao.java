package fr.sacem.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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

