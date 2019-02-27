package fr.sacem.priam.batch.common.dao;

import com.google.common.base.Joiner;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AyantDroitDao {
    private JdbcTemplate jdbcTemplate;

    public AyantDroitDao(@Autowired DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public void deleteDedoublonnageTableAD(final String numProg) {
        String selectSql = "SELECT AD.ID AS id " +
                "FROM PRIAM_LIGNE_PROGRAMME_FV FV " +
                "INNER JOIN PRIAM_FICHIER PF ON FV.ID_FICHIER = PF.ID " +
                "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=PF.NUMPROG " +
                "INNER JOIN PRIAM_AYANT_DROIT AD ON FV.id = AD.ID_FV " +
                "WHERE PF.NUMPROG=? " +
                "AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' " +
                "AND AD.ID NOT IN (SELECT temp.id3 " +
"                                       FROM(" +
"                                       SELECT MIN(AD.ID) AS id3 " +
"                                       FROM PRIAM_LIGNE_PROGRAMME_FV FV " +
"                                       INNER JOIN PRIAM_FICHIER PF ON FV.ID_FICHIER = PF.ID" +
                "                       INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=PF.NUMPROG " +
"                                       INNER JOIN PRIAM_AYANT_DROIT AD ON FV.id = AD.ID_FV " +
"                                       WHERE PF.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' " +
"                                       GROUP BY AD.COAD, AD.ROLAD) AS temp)" +
                "  GROUP BY AD.COAD, AD.ROLAD";

        List<Long> ids = jdbcTemplate.query(selectSql,
                (resultSet, i) -> resultSet.getLong("id"), numProg, numProg);

        if(ids != null && !ids.isEmpty()) {
            String sql =  "DELETE FROM PRIAM_AYANT_DROIT WHERE id IN (" + Joiner.on(",").join(ids).toString() + ") ";
            jdbcTemplate.update(sql);
        }
    }

    @Transactional
    public boolean isAyantDroitExist(Long coad) {
        try {
            List<Long> result = jdbcTemplate.query("SELECT COAD  FROM PRIAM_AYANT_DROIT WHERE COAD=?",
                (resultSet, i) -> resultSet.getLong("COAD"), coad);

            return result != null && !result.isEmpty();
        }catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }
}
