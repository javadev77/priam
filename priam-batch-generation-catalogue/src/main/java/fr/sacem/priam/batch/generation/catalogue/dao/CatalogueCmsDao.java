package fr.sacem.priam.batch.generation.catalogue.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Created by benmerzoukah on 13/06/2018.
 */
@Repository
public class CatalogueCmsDao {

    @Autowired
    DataSource dataSource;

    JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long countNbLignes(String typeCms) {
        String sql =  "SELECT " +
                "COUNT(*) as NB_LIGNES " +
                "FROM " +
                "PRIAM_CATCMS_CATALOGUE CATALOGUE INNER JOIN PRIAM_CATCMS_PARTICIPANTS PARTICIPANTS " +
                "ON CATALOGUE.IDE12 = PARTICIPANTS.IDE12 AND CATALOGUE.TYPE_CMS = PARTICIPANTS.TYPE_CMS " +
                "WHERE  CATALOGUE.TYPE_CMS=? AND CATALOGUE.DATE_SORTIE IS NULL";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("NB_LIGNES"), typeCms);
    }
}
