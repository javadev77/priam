package fr.sacem.priam.batch.enrichissement.cat.dao;

import fr.sacem.priam.batch.common.util.DateTimeUtils;
import fr.sacem.priam.model.domain.catcms.StatistiqueCatcms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by embouazzar on 28/09/2018.
 */
@Repository
public class StatistiquesCatcmsRepository {

    @Autowired
    DataSource dataSource;

    JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public List<StatistiqueCatcms> findAll(){
        String sql = "SELECT * FROM PRIAM_CATCMS_STATISTIQUES";
//        return jdbcTemplate.queryForList (sql, fichierExtractor, ide12);
        return jdbcTemplate.query(sql, new ResultSetExtractor<List<StatistiqueCatcms>>() {
            @Override
            public List<StatistiqueCatcms> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<StatistiqueCatcms> list = new ArrayList<>();
                while(rs.next()){
                    StatistiqueCatcms statistiqueCatcms = new StatistiqueCatcms();
                    statistiqueCatcms.setId(rs.getLong("ID"));
                    statistiqueCatcms.setTypeCMS(rs.getString("TYPE_CMS"));
                    statistiqueCatcms.setDate(rs.getDate("DATE"));
                    statistiqueCatcms.setNomFichier(rs.getString("NOM_FICHIER"));
                    statistiqueCatcms.setNbOeuvres(rs.getLong("NB_OEUVRES"));
                    statistiqueCatcms.setNbCreation(rs.getLong("NB_CREATION"));
                    statistiqueCatcms.setNbRenouvellement(rs.getLong("NB_RENOUVELLEMENT"));
                    statistiqueCatcms.setNbTotalOeuvres(rs.getLong("NB_TOTAL_RENOUVELLEMENT"));
                    list.add(statistiqueCatcms);
                }
                return list;
            }
        });
    }

    @Transactional(value="transactionManager")
    public long saveStatistique(StatistiqueCatcms statistiqueCatcms) {
        String sql =  "INSERT INTO PRIAM_CATCMS_STATISTIQUES (TYPE_CMS, DATE, NOM_FICHIER, NB_OEUVRES, NB_CREATION, NB_RENOUVELLEMENT, NB_TOTAL_OEUVRES)" +
                "  VALUES (?, ?, ?, ?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, statistiqueCatcms.getTypeCMS());
            stmt.setTimestamp(2, new DateTimeUtils().getCurrentTimeStamp());
            stmt.setString(3, statistiqueCatcms.getNomFichier());
            stmt.setLong(4, statistiqueCatcms.getNbOeuvres());
            stmt.setLong(5, statistiqueCatcms.getNbCreation());
            stmt.setLong(6, statistiqueCatcms.getNbRenouvellement());
            stmt.setLong(7, statistiqueCatcms.getNbTotalOeuvres());

            return stmt;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

}
