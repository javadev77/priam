package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class JournalBatchDao {

    private JdbcTemplate jdbcTemplate;
    public static final int BATCH_SIZE = 25;

    @Transactional(value="transactionManager")
    public long saveJournal(Journal journal) {
        String sql =  "INSERT INTO PRIAM_JOURNAL_EVENEMENT (evenement, date, utilisateur, numprog)" +
                "  VALUES (?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, journal.getEvenement());

            stmt.setTimestamp(2, getCurrentTimeStamp());
            stmt.setString(3, journal.getUtilisateur());
            stmt.setString(4, journal.getNumProg());

            return stmt;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }


    private java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }


    public void saveSituationAvantJournal(List<SituationAvant> situationAvantList, Long idJournal){

        if(situationAvantList != null && !situationAvantList.isEmpty()) {

            String sql = "INSERT INTO PRIAM_SITUATION_AVANT (SITUATION, ID_EVENEMENT) VALUES (?, ?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    SituationAvant situationAvant = situationAvantList.get(i);
                    preparedStatement.setString(1, situationAvant.getSituation());
                    preparedStatement.setLong(2, idJournal.longValue());
                }

                @Override
                public int getBatchSize() {
                    return situationAvantList.size();
                }
            });
        }
    }

    public void saveSituationApresJournal(List<SituationApres> situationApresList, Long idJournal){

        if(situationApresList != null && !situationApresList.isEmpty()) {
            String sql = "INSERT INTO PRIAM_SITUATION_APRES (SITUATION, ID_EVENEMENT) VALUES (?, ?)";

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    SituationApres situationApres = situationApresList.get(i);
                    preparedStatement.setString(1, situationApres.getSituation());
                    preparedStatement.setLong(2, idJournal);
                }

                @Override
                public int getBatchSize() {
                    return situationApresList.size();
                }
            });
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
