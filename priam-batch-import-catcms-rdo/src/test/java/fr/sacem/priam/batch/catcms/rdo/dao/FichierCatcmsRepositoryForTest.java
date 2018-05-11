package fr.sacem.priam.batch.catcms.rdo.dao;

import fr.sacem.priam.batch.common.domain.Fichier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FichierCatcmsRepositoryForTest {

    private JdbcTemplate jdbcTemplate;
    private String nomTableFichier;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long getLastIdFichier(){
        String sql = "SELECT ID " +
                " FROM " + this.nomTableFichier +
                " WHERE ID=LAST_INSERT_ID() ";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public Long countNbLignesByIdFichier(Long idFichier){
        String sql = "SELECT NB_LIGNES " +
                "FROM " + this.nomTableFichier +
                " WHERE ID=? ";

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

    public String getNomTableFichier() {
        return nomTableFichier;
    }

    public void setNomTableFichier(String nomTableFichier) {
        this.nomTableFichier = nomTableFichier;
    }
}
