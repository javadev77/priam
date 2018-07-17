package fr.sacem.priam.model.dao.jpa.cp;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Created by benmerzoukah on 12/04/2018.
 */
@Component
public class LigneProgrammeCPJdbcDao {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void batchUpdateNbrDifTemporaireByNumProgramme(List<LigneProgrammeCP> records) {
        /*String sql = "update  PRIAM_LIGNE_PROGRAMME_CP p " +
                "INNER JOIN " +
                "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                "set " +
                "  p.nbrDifEdit=?, p.SEL_EN_COURS=? "+
                "where " +
                "  f.NUMPROG = ? " +
                " AND p.ide12 = ? " +
                " AND p.cdeUtil = ? " +
                " AND p.idOeuvreManuel is NULL ";
        return jdbcTemplate.batchUpdate(sql, records, 25,  (ps, ligneProgrammeCP) -> {
                                            ps.setLong(1, ligneProgrammeCP.getNbrDifEdit());
                                            ps.setBoolean(2, ligneProgrammeCP.isSelectionEnCours());
                                            ps.setString(3, ligneProgrammeCP.getNumProg());
                                            ps.setLong(4, ligneProgrammeCP.getIde12());
                                            ps.setString(5, ligneProgrammeCP.getCdeUtil());
                                        });*/

        int batchSize = 25;
        String sql = "update  PRIAM_LIGNE_PROGRAMME_CP p " +
                "INNER JOIN " +
                "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                "set " +
                "  p.nbrDifEdit=?, p.SEL_EN_COURS=? "+
                "where " +
                "  f.NUMPROG = ? " +
                " AND p.ide12 = ? " +
                " AND p.cdeUtil = ? " +
                " AND p.idOeuvreManuel is NULL ";
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DataSourceUtils.getConnection(this.dataSource);
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement(sql);

            for (int i = 0; i < records.size(); i++) {

                if(i > 0 && i % batchSize == 0) {
                    stmt.executeBatch();
                    stmt.clearParameters();
                }

                LigneProgrammeCP ligneProgrammeCP = records.get(i);

                stmt.setLong(1, ligneProgrammeCP.getNbrDifEdit());
                stmt.setBoolean(2, ligneProgrammeCP.isSelectionEnCours());
                stmt.setString(3, ligneProgrammeCP.getNumProg());
                stmt.setLong(4, ligneProgrammeCP.getIde12());
                stmt.setString(5, ligneProgrammeCP.getCdeUtil());

                stmt.addBatch();
            }
            stmt.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            JdbcUtils.closeStatement(stmt);
            DataSourceUtils.releaseConnection(connection, this.dataSource);

        }
    }

    public int[][] batchpdateSelectionTemporaireByNumProgramme(List<LigneProgrammeCP> records) {
        String sql = "update  PRIAM_LIGNE_PROGRAMME_CP p " +
                "INNER JOIN " +
                "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                "set " +
                "  p.SEL_EN_COURS=? "+
                "where " +
                "  f.NUMPROG = ? " +
                " AND p.ide12 = ? " +
                " AND p.cdeUtil = ? ";
        return jdbcTemplate.batchUpdate(sql, records, 50,  (ps, ligneProgrammeCP) -> {
            ps.setBoolean(1, ligneProgrammeCP.isSelectionEnCours());
            ps.setString(2, ligneProgrammeCP.getNumProg());
            ps.setLong(3, ligneProgrammeCP.getIde12());
            ps.setString(4, ligneProgrammeCP.getCdeUtil());
        });
    }


    public void batchUpdateSelection(List<LigneProgrammeCP> records) {
        List<Long> ids = Lists.transform(records, f -> f.getId());
        if(ids != null && !ids.isEmpty()) {
            String sql = "update PRIAM_LIGNE_PROGRAMME_CP p " +
//                "INNER JOIN " +
//                "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                    "set  p.selection=p.SEL_EN_COURS " +
                    "WHERE id IN (" + Joiner.on(",").join(ids).toString() + ") ";

            System.out.println("Executing batchUpdateSelection() ...... ");

            jdbcTemplate.update(sql);

        }
    }

    public void updateNbrDif(List<Long> ids) {
        String sql = "update PRIAM_LIGNE_PROGRAMME_CP p " +
//                "INNER JOIN " +
//                "PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                "set  p.nbrDif=p.nbrDifEdit " +
                "WHERE id IN (" + Joiner.on(",").join(ids).toString() + ") ";

        System.out.println("Executing batchUpdateSelection() ...... ");

        jdbcTemplate.update(sql);

    }

    public int[][] batchUpdateDurDifTemporaireByNumProgramme(List<LigneProgrammeCP> records) {
        String sql = "update  PRIAM_LIGNE_PROGRAMME_CP p " +
                "INNER JOIN " +
                "  PRIAM_FICHIER f ON p.ID_FICHIER = f.ID " +
                "set " +
                "  p.durDifEdit=?, p.SEL_EN_COURS=? "+
                "where " +
                "  f.NUMPROG = ? " +
                " AND p.ide12 = ? " +
                " AND p.cdeUtil = ? ";
        return jdbcTemplate.batchUpdate(sql, records, 50,  (ps, ligneProgrammeCP) -> {
            ps.setLong(1, ligneProgrammeCP.getDurDifEdit());
            ps.setBoolean(2, ligneProgrammeCP.isSelectionEnCours());
            ps.setString(3, ligneProgrammeCP.getNumProg());
            ps.setLong(4, ligneProgrammeCP.getIde12());
            ps.setString(5, ligneProgrammeCP.getCdeUtil());
        });
    }

    public int[][] batchUpdateOeuvreAuto(List<LigneProgrammeCP> founds) {

        String sql = "update  PRIAM_LIGNE_PROGRAMME_CP p " +
                "set p.idOeuvreManuel=? "+
                "where p.id=? ";
        return jdbcTemplate.batchUpdate(sql, founds, 25,  (ps, ligneProgrammeCP) -> {
            ps.setLong(1, ligneProgrammeCP.getOeuvreManuel().getId());
            ps.setLong(2, ligneProgrammeCP.getId());

        });
    }

    public List<Map<String, Long>> compterOuvres(String numProg) {

        return jdbcTemplate.query("SELECT count(*), temp.ajout " +
                "FROM(SELECT lp.ajout as ajout " +
                "     FROM PRIAM_LIGNE_PROGRAMME_CP lp " +
                "       INNER JOIN PRIAM_FICHIER as f on f.ID=lp.ID_FICHIER AND f.NUMPROG=? " +
                "     WHERE lp.SEL_EN_COURS=1 AND lp.idOeuvreManuel IS NULL " +
                "    ) as temp " +
                "GROUP BY temp.ajout ", ps -> ps.setString(1, numProg),
                (resultSet, i) -> {
                    Map<String, Long> map = new HashMap<>();
                    map.put(resultSet.getString(2), resultSet.getLong(1));
                    return map;
                });
    }

    public Long calculerQuantiteOeuvres(String numProg) {

        return  jdbcTemplate.queryForObject("SELECT sum(ajout) "+
                "FROM(SELECT lp.nbrDifEdit as ajout "+
                "FROM PRIAM_LIGNE_PROGRAMME_CP lp "+
                "INNER JOIN PRIAM_FICHIER as f on f.ID=lp.ID_FICHIER AND f.NUMPROG=? "+
                "WHERE lp.SEL_EN_COURS=1 AND lp.idOeuvreManuel is null "+
                ") as temp",
                new Object[]{numProg}
                ,
                (resultSet, i) -> resultSet.getLong(1));
    }

    public Long calculerDureeOeuvres(String numProg) {
        return  jdbcTemplate.queryForObject("SELECT sum(ajout) "+
                        "FROM(SELECT lp.durDifEdit as ajout "+
                        "FROM PRIAM_LIGNE_PROGRAMME_CP lp "+
                        "INNER JOIN PRIAM_FICHIER as f on f.ID=lp.ID_FICHIER AND f.NUMPROG=? "+
                        "WHERE lp.SEL_EN_COURS=1 AND lp.idOeuvreManuel is null "+
                        ") as temp",
                new Object[]{numProg}
                ,
                (resultSet, i) -> resultSet.getLong(1));
    }
}
