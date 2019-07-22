package fr.sacem.priam.batch.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class FichierFVEnrichissementEnvoyeDao {

    private final JdbcTemplate jdbcTemplate;

    public FichierFVEnrichissementEnvoyeDao(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(value="transactionManager")
    public Long getNbInfosEnvoye(Long idFichier, String service) throws EmptyResultDataAccessException {
        String sql = "SELECT NB_INFO_ENVOYE FROM PRIAM_FICHIER_FV_ENRICHISSEMENT_ENVOYE WHERE ID_FICHIER=? AND SERVICE=?";
        return jdbcTemplate.queryForObject(sql, Long.class, idFichier, service);
    }

    @Transactional(value="transactionManager")
    public Long enregistrerNbInfoEnvoye(Long idFichier, String service, Long nbInfoEnvoye) {
        String sql = "INSERT INTO PRIAM_FICHIER_FV_ENRICHISSEMENT_ENVOYE (ID_FICHIER, SERVICE, NB_INFO_ENVOYE) VALUES(?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, idFichier);
            stmt.setString(2, service);
            stmt.setLong(3, nbInfoEnvoye);
            return stmt;
        }, keyHolder);

        return keyHolder.getKey().longValue();

    }

    @Transactional(value="transactionManager")
    public void supprimerNbInfoEnvoye(Long idFichier, String service){
        if(idFichier != null) {
            String deleteSql = "DELETE FROM PRIAM_FICHIER_FV_ENRICHISSEMENT_ENVOYE WHERE ID_FICHIER=? AND SERVICE=?";
            jdbcTemplate.update(deleteSql, preparedStatement -> {
                preparedStatement.setLong(1, idFichier);
                preparedStatement.setString(2, service);
            });
        }
    }

}
