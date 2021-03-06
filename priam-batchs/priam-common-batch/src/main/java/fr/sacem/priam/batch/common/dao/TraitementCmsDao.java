package fr.sacem.priam.batch.common.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 18/12/2017.
 */
@Repository
public class TraitementCmsDao {
    private static final Logger LOG = LoggerFactory.getLogger(TraitementCmsDao.class);
    private JdbcTemplate jdbcTemplate;

    public TraitementCmsDao() {

    }


    @Transactional(value="transactionManager")
    public long createTraitement(String numProg, Long nbOeuvres) {
        String sql =  "INSERT INTO PRIAM_TRAITEMENT_ELIGIBILITE_CMS (DATE_DEBUT_TMT, NUMPROG, STATUT_ELIGIBILITE, NB_OEUVRES_EXTRACT)" +
                "  VALUES (?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            java.util.Date today = new java.util.Date();
            stmt.setTimestamp(1,new java.sql.Timestamp(today.getTime()));
            stmt.setString(2, numProg);
            stmt.setString(3, "EN_COURS_ELIGIBILITE");
            stmt.setLong(4, nbOeuvres);

            return stmt;
        }, keyHolder);


        return keyHolder.getKey().longValue();
    }


    @Transactional(value="transactionManager")
    public void majTraitment(Long idTraitementCMS, Long nbOeuvresCatalogue,
                             Long oeuvresRetenues, Double sommePoints, String statutEligibilite) {
        String sql =  "UPDATE PRIAM_TRAITEMENT_ELIGIBILITE_CMS SET STATUT_ELIGIBILITE = ?, DATE_FIN_TMT = ?, " +
                "NB_OEUVRES_CATALOGUE = ?, NB_OEUVRES_RETENUES = ?, SOMME_POINTS=? " +
                " WHERE ID = ?";
        java.util.Date now = new java.util.Date();
        jdbcTemplate.update(sql, stmt -> {
            stmt.setString(1, statutEligibilite);
            stmt.setTimestamp(2, new java.sql.Timestamp(now.getTime()));
            stmt.setLong(3, nbOeuvresCatalogue);
            stmt.setLong(4, oeuvresRetenues);
            stmt.setDouble(5, sommePoints);
            stmt.setLong(6, idTraitementCMS);
        });
    }


    @Transactional(value="transactionManager")
    public void viderCatalogueOctav(String typeCms) {
        LOG.info("=== Suppression du contenu de la table PRIAM_CATALOGUE_OCTAV ====");
        String sql = "DELETE FROM PRIAM_CATALOGUE_OCTAV WHERE TYPE_CMS='" + typeCms + "'";
        jdbcTemplate.update(sql);
    }


    @Transactional(value="transactionManager")
    public void viderCatalogueOctavAnt(String numProg){
        LOG.info("=== Suppression du contenu de la table PRIAM_CATALOGUE_OCTAV ====");
        String sql = "DELETE FROM PRIAM_CATALOGUE_OCTAV_ANT WHERE NUMPROG="+ "'" + numProg + "'";
        jdbcTemplate.update(sql);
    }


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
