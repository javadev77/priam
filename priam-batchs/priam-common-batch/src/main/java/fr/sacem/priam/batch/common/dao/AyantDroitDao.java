package fr.sacem.priam.batch.common.dao;

import com.google.common.base.Joiner;

import java.util.*;
import java.util.stream.Collectors;
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

//    @Transactional(value="transactionManager")
    public void deleteDedoublonnageTableAD(final String numProg) {
        String selectSql = "SELECT AD.ID AS id, AD.ID_FV AS ID_FV  " +
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
"                                       GROUP BY AD.COAD, AD.ROLAD, AD.IDE12REPCOAD) AS temp)" +
                "  GROUP BY AD.COAD, AD.ROLAD, AD.IDE12REPCOAD";

        /*List<Long> ids = jdbcTemplate.query(selectSql, (resultSet, i) -> resultSet.getLong("id"), numProg, numProg);*/

        List<Map<String, Object>> maps = jdbcTemplate.queryForList(selectSql, numProg, numProg);

        List<Long> list_id = new ArrayList<>();
        Set<Long> list_id_FV = new LinkedHashSet<>();

        for (Map map : maps) {
            list_id.add((Long.valueOf(map.get("id").toString())));
            list_id_FV.add(Long.valueOf(map.get("ID_FV").toString()));
        }



        if(list_id != null && !list_id.isEmpty()) {
            String sql =  "DELETE FROM PRIAM_AYANT_DROIT WHERE id IN (" + Joiner.on(",").join(list_id).toString() + ") ";
            jdbcTemplate.update(sql);
        }

        if (list_id_FV != null && !list_id_FV.isEmpty()) {
            String sql =  "DELETE FROM PRIAM_LIGNE_PROGRAMME_FV WHERE id IN (" + Joiner.on(",").join(list_id_FV).toString() + ") ";
            jdbcTemplate.update(sql);
        }
    }

    public Set<Long> getLigneProgrammeFVToDelete(final String numProg){
        String selectSql = "SELECT AD.ID_FV AS ID_FV  " +
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
                "                                       GROUP BY AD.COAD, AD.ROLAD, AD.IDE12REPCOAD) AS temp)" +
                "  GROUP BY AD.COAD, AD.ROLAD, AD.IDE12REPCOAD";

        return jdbcTemplate.query(selectSql, (resultSet, i) -> resultSet.getLong("ID_FV"), numProg, numProg)
                .stream().collect(Collectors.toSet());
    }

    @Transactional(value="transactionManager")
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
