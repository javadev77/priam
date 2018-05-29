package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Created by benmerzoukah on 12/04/2018.
 */
@Component
public class JournalJdbcDao {

    @Autowired
    private DataSource dataSource;

    private Connection connection;

    @PostConstruct
    public void postConstruct() {

       /* try {
            connection = DataSourceUtils.getConnection(this.dataSource);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @PreDestroy
    public void destroy() {
       /* try {
            if(!this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

    }


    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void bathInsertJournal(List<Journal> journaux) {
        if(journaux == null || journaux.isEmpty()) {
            return;
        }
        String sql =  "INSERT INTO PRIAM_JOURNAL_EVENEMENT (evenement, date, utilisateur, numprog, ide12)" +
                "  VALUES (?, ?, ?, ?, ?)";

        Queue<Long> generedIds = new LinkedList<>();

        int batchSize = 25;
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(this.dataSource);
            connection.setAutoCommit(false);
            try(PreparedStatement ps = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < journaux.size(); i++) {

                    if(i > 0 && i % batchSize == 0) {
                        ps.executeBatch();
                        ResultSet generatedKeys = ps.getGeneratedKeys();
                        while(generatedKeys.next()) {
                            generedIds.add(generatedKeys.getLong(1));
                        }
                    }

                    Journal journal = journaux.get(i);

                    ps.setString(1, journal.getEvenement());
                    ps.setTimestamp(2, new Timestamp(new Date().getTime()));
                    ps.setString(3, journal.getUtilisateur());
                    ps.setString(4, journal.getNumProg());
                    ps.setLong(5, journal.getIde12());

                    ps.addBatch();
                }
                ps.executeBatch();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                while(generatedKeys.next()) {
                    generedIds.add(generatedKeys.getLong(1));
                }
               // connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }


            for(Journal journal : journaux) {
                Long journalID = generedIds.poll();
                try(PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO PRIAM_SITUATION_AVANT (SITUATION, ID_EVENEMENT) VALUES (?, ?)")) {

                    List<SituationAvant> listSituationAvant = journal.getListSituationAvant();
                    for (int i = 0; i < listSituationAvant.size(); i++) {
                        if(i > 0 && i % batchSize == 0) {
                            ps.executeBatch();
                        }

                        SituationAvant situationAvant = listSituationAvant.get(i);

                        ps.setString(1, situationAvant.getSituation());
                        ps.setLong(2, journalID);

                        ps.addBatch();
                    }
                    ps.executeBatch();
                } catch (SQLException e) {
                    try {
                        connection.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                try(PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO PRIAM_SITUATION_APRES (SITUATION, ID_EVENEMENT) VALUES (?, ?)")) {

                    List<SituationApres> listSituationApres = journal.getListSituationApres();
                    for (int i = 0; i < listSituationApres.size(); i++) {
                        if(i > 0 && i % batchSize == 0) {
                            ps.executeBatch();
                        }

                        SituationApres situationApres = listSituationApres.get(i);

                        ps.setString(1, situationApres.getSituation());
                        ps.setLong(2, journalID);

                        ps.addBatch();
                    }
                    ps.executeBatch();
                } catch (SQLException e) {
                    try {
                        connection.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {

            DataSourceUtils.releaseConnection(connection, this.dataSource);
        }





       /* for(Journal journal : journaux) {
            Long journalID = generedIds.poll();
            try(PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO PRIAM_SITUATION_AVANT (SITUATION, ID_EVENEMENT) VALUES (?, ?)")) {

                List<SituationAvant> listSituationAvant = journal.getListSituationAvant();
                for (int i = 0; i < listSituationAvant.size(); i++) {
                    if(i > 0 && i % batchSize == 0) {
                        ps.executeBatch();
                    }

                    SituationAvant situationAvant = listSituationAvant.get(i);

                    ps.setString(1, situationAvant.getSituation());
                    ps.setLong(2, journalID);

                    ps.addBatch();
                }
                ps.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            try(PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO PRIAM_SITUATION_APRES (SITUATION, ID_EVENEMENT) VALUES (?, ?)")) {

                List<SituationApres> listSituationApres = journal.getListSituationApres();
                for (int i = 0; i < listSituationApres.size(); i++) {
                    if(i > 0 && i % batchSize == 0) {
                        ps.executeBatch();
                    }

                    SituationApres situationApres = listSituationApres.get(i);

                    ps.setString(1, situationApres.getSituation());
                    ps.setLong(2, journalID);

                    ps.addBatch();
                }
                ps.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        }*/

       /* try {
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }*/

    }
}
