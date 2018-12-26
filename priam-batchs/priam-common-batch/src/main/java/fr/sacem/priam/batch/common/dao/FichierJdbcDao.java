package fr.sacem.priam.batch.common.dao;

import fr.sacem.priam.batch.common.domain.Fichier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */

@Repository
public class FichierJdbcDao {

    private final JdbcTemplate jdbcTemplate;



    public FichierJdbcDao(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<Fichier> getFichiersFvByStatutEnrichissement(String statut) {
        return jdbcTemplate.query("SELECT * FROM PRIAM_FICHIER WHERE CDEFAMILTYPUTIL='FDSVAL' AND STATUT_ENRICHISSEEMNT = '" + statut + "'", (rs, i) -> {
            Fichier fichier = new Fichier();
            Long id = rs.getLong("ID");
            fichier.setId(id);

            return fichier;
        });
    }

    @Transactional
    public void majStatutEnrichissement(Long idFichier, String statut) {
        String sql = "UPDATE PRIAM_FICHIER SET STATUT_ENRICHISSEEMNT=? WHERE ID=?";
        jdbcTemplate.update(sql, stmt -> {
            stmt.setString(1, statut);
            stmt.setLong(2, idFichier);

        });
    }
}
