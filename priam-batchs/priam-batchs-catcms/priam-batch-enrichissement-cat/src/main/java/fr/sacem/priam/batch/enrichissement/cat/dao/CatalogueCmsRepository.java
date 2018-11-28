package fr.sacem.priam.batch.enrichissement.cat.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Created by embouazzar on 28/09/2018.
 */
@Repository
public class CatalogueCmsRepository {

    @Autowired
    DataSource dataSource;

    JdbcTemplate jdbcTemplate;


    @PostConstruct
    public void init() {
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public Long nbTotalOeuvresCatalogueByTypeCMS(String typeCMS){
        String sql = "SELECT COUNT(*) FROM PRIAM_CATCMS_CATALOGUE WHERE TYPE_CMS=? AND (DATE_SORTIE IS NULL OR DATE_SORTIE >= CURRENT_DATE)";
        return jdbcTemplate.queryForObject(sql, Long.class, typeCMS);
    }

}
