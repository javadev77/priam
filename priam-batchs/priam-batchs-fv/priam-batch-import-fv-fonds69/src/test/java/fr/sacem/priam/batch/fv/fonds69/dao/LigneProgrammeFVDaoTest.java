package fr.sacem.priam.batch.fv.fonds69.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
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
