package fr.sacem.priam.batch.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

@Repository
public class FichierFVEnrichissementLogDao {

    private final JdbcTemplate jdbcTemplate;

    public FichierFVEnrichissementLogDao(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(value="transactionManager")
    public Long enregistrerLog(Long idFichier, String statut) {
        String sql = "INSERT INTO PRIAM_FICHIER_FV_ENRICHISSEMENT_LOG (ID_FICHIER, STATUT, DATE) VALUES(?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, idFichier);
            stmt.setString(2, statut);
            stmt.setTimestamp(3, new Timestamp(new Date().getTime()));
            return stmt;
        }, keyHolder);

        return keyHolder.getKey().longValue();

    }
}
