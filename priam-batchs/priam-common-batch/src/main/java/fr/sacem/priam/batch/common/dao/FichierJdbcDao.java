package fr.sacem.priam.batch.common.dao;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import fr.sacem.priam.batch.common.domain.Fichier;
import fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<Fichier> getFichiersByStatutAndCdeTypUtil(String statut, List<String> fonds) {
        String inClause = "'" + Joiner.on("','").join(fonds) + "'";
        return jdbcTemplate.query("SELECT * FROM PRIAM_FICHIER WHERE CDEFAMILTYPUTIL='FDSVAL' " +
            "AND CDETYPUTIL IN (" + inClause + ") "+
            "AND STATUT_CODE= '" + statut + "'", (rs, i) -> {

            Fichier fichier = new Fichier();
            Long id = rs.getLong("ID");
            fichier.setId(id);

            return fichier;
        });
    }

    @Transactional(readOnly = true)
    public List<Fichier> getFichiersInfoOeuvreEligible() {
        ArrayList<String> fonds = Lists.newArrayList("FD01", "FD02", "FD03", "FD07", "FD13");
        String inClause = "'" + Joiner.on("','").join(fonds) + "'";
        return jdbcTemplate.query("SELECT * FROM PRIAM_FICHIER WHERE CDEFAMILTYPUTIL='FDSVAL' " +
            "AND (STATUT_CODE= 'CHARGEMENT_OK' AND CDETYPUTIL IN (" + inClause + ") ) " +
            "OR (STATUT_ENRICHISSEEMNT='" + EtapeEnrichissementEnum.DONE_SRV_OCTAV_CTNU.getCode() + "')", (rs, i) -> {

            Fichier fichier = new Fichier();
            Long id = rs.getLong("ID");
            fichier.setId(id);

            return fichier;
        });
    }
}
