package fr.sacem.priam.batch.fv.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by embouazzar on 06/12/2018.
 */
public class LigneProgrammeFVDaoTest {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long countNbLignesByIdFichier(Long idFichier){
        String sql = "SELECT COUNT(*) " +
                "FROM PRIAM_LIGNE_PROGRAMME_FV " +
                " WHERE ID_FICHIER=? ";

        Long nbLignes =
                jdbcTemplate.queryForObject(sql, new Object[] {idFichier}, Long.class);
        return  nbLignes;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
