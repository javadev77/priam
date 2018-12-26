package fr.sacem.priam.batch.common.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by embouazzar on 22/12/2018.
 */
@Repository
public class LigneProgrammeFVDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(LigneProgrammeFVDao.class);



    private JdbcTemplate jdbcTemplate;

    public LigneProgrammeFVDao(@Autowired DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
//
//    @PostConstruct
//    public void init() {
//
//        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
//    }

    public Long countNbLignesByIdFichier(Long idFichier){
        String sql = "SELECT " +
                "count(l.ID) as NB_LIGNES " +
                "FROM " +
                "PRIAM_LIGNE_PROGRAMME_FV l " +
                "WHERE l.ID_FICHIER=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("NB_LIGNES"), idFichier);
    }
}
