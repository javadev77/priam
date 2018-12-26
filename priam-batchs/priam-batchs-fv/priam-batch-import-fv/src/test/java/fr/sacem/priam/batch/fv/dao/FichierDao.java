package fr.sacem.priam.batch.fv.dao;

import fr.sacem.priam.batch.common.domain.Fichier;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by embouazzar on 20/12/2018.
 */
public class FichierDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Fichier findByName(String nomFichier) {
        String sql = "SELECT f.ID, f.NB_LIGNES,f.NOM,f.CDETYPUTIL " +
                "FROM PRIAM_FICHIER f " +
                "WHERE f.NOM=? ";
        return jdbcTemplate.query(sql, resultSet -> {
            Fichier fichier = new Fichier();
            while (resultSet.next()){
                fichier.setNom(resultSet.getString("NOM"));
                fichier.setTypeUtilisation(resultSet.getString("CDETYPUTIL"));
                fichier.setNbLignes(resultSet.getLong("NB_LIGNES"));
                fichier.setId(resultSet.getLong("ID"));
            }
            if (fichier == null) {
                // no rows returned - throw an empty result exception
                throw new EmptyResultDataAccessException(1);
            }
            return fichier;
        } , nomFichier) ;
    }
}
