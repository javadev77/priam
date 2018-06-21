package fr.sacem.priam.batch.participants.req.dao;

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
                "count(l.ID) as NB_LIGNES " +
                "FROM " +
                "PRIAM_CATCMS_CATALOGUE l " +
                "WHERE l.DATE_SORTIE IS NULL AND l.TYPE_CMS=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("NB_LIGNES"), typeCms);
    }
}
