package fr.sacem.priam.batch.enrichissement.cat.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Created by benmerzoukah on 25/05/2018.
 */
@Repository
public class CatalogueCmsPenefRepository {

    @Autowired
    DataSource dataSource;

    JdbcTemplate jdbcTemplate;


    @PostConstruct
    public void init() {
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public void supprimerDonneesCatPenefParIdFichier(Long idFichier) {
        if(idFichier != null) {
            String sql = "DELETE FROM PRIAM_CATCMS_PENEF WHERE ID_FICHIER=?";
            jdbcTemplate.update(sql, stmt -> stmt.setLong(1, idFichier));
        }
    }
}
