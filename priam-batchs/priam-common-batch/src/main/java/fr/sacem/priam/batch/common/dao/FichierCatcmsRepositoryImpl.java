package fr.sacem.priam.batch.common.dao;

import fr.sacem.priam.batch.common.domain.Fichier;
import fr.sacem.priam.batch.common.util.DateTimeUtils;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import fr.sacem.priam.common.util.FileUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

@Repository
public class FichierCatcmsRepositoryImpl implements FichierRepository {

    private JdbcTemplate jdbcTemplate;
    private String nomTableFichier;
    private ResultSetExtractor<Fichier> fichierExtractor = new FichierExtractor();
    private static final String STATUT_OK = "CHARGEMENT_OK";
    private static final String STATUT_KO = "CHARGEMENT_KO";
    private static final String STATUT_EN_COURS = "EN_COURS";


    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private Fichier mapFichier(ResultSet rs) throws SQLException {
        Fichier fichier = null;
        while (rs.next()) {
            if (fichier == null) {
                fichier = new Fichier();
                fichier.setDateDebutChargt(rs.getTimestamp("DATE_DEBUT_CHGT"));
                fichier.setNom(rs.getString("NOM"));
                fichier.setStatut(rs.getString("STATUT_CODE"));
                fichier.setNbLignes(rs.getLong("NB_LIGNES"));
                fichier.setDateFinChargt(rs.getTimestamp("DATE_FIN_CHGT"));
                fichier.setId(rs.getLong("ID"));
                // set internal entity identifier (primary key)
            }
        }

        if (fichier == null) {
            // no rows returned - throw an empty result exception
            throw new EmptyResultDataAccessException(1);
        }
        return fichier;
    }

    @Override
    public Long addFichier(Fichier fichier) throws PriamValidationException {

        String sql = "INSERT INTO " + this.nomTableFichier +
                "(DATE_DEBUT_CHGT, NB_LIGNES, NOM, STATUT_CODE, TYPE_FICHIER) " +
                "VALUES(?,?,?,?,?)";
        try{
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setTimestamp(1, fichier.getDateDebutChargt());
                stmt.setLong(2, fichier.getNbLignes());
                stmt.setString(3, fichier.getNom());
                stmt.setString(4, STATUT_EN_COURS);
                stmt.setString(5,getTypeFichierCatcms(fichier.getNom()));
                return stmt;
            }, keyHolder);

            return keyHolder.getKey().longValue();

        }catch (Exception e) {
            throw new PriamValidationException(-1, e, PriamValidationException.ErrorType.FORMAT_FICHIER, null);
        }
    }

    @Override
    public void updateFichierDate(String nomFichier) {

    }

    @Override
    public Fichier findByName(String nomFichier) {
        String sql = "SELECT ID, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, NOM, STATUT_CODE " +
                "FROM " + this.nomTableFichier +
                " WHERE NOM=? ";
        return jdbcTemplate.query(sql, fichierExtractor, nomFichier) ;
    }

    @Override
    public void updateFichierById(Long idFichier) {
        String sql = "UPDATE " + this.nomTableFichier + " SET STATUT_CODE=?,DATE_FIN_CHGT=? " +
                "WHERE ID=?";
        jdbcTemplate.update(sql, stmt -> {
            stmt.setString(1, STATUT_OK);
            stmt.setTimestamp(2, new DateTimeUtils().getCurrentTimeStamp());
            stmt.setLong(3, idFichier);
        });
    }

    @Override
    public Fichier findById(Long idFichier) {
        String sql = "SELECT ID, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, NOM, STATUT_CODE " +
                "FROM " + this.nomTableFichier +
                " WHERE ID=?";
        return jdbcTemplate.query(sql, fichierExtractor, String.valueOf(idFichier)) ;
    }

    @Override
    public void rejeterFichier(Long idFichier, Set<String> errors) {
        String sql = "UPDATE " + this.nomTableFichier + " SET STATUT_CODE=?,DATE_FIN_CHGT=? WHERE ID=?";

        jdbcTemplate.update(sql, stmt -> {
            stmt.setString(1, STATUT_KO);
            stmt.setTimestamp(2, new DateTimeUtils().getCurrentTimeStamp());
            stmt.setLong(3, idFichier);

        });
    }

    @Override
    public void supprimerLigneProgrammeParIdFichier(Long idFichier) {

    }

    @Override
    public void enregistrerLog(Long idFichier, Set<String> errors) {

    }

    @Override
    public void clearSelectedFichiers(String numProg, String statut) {

    }

    @Override
    public Long addFichierLink(String numProg) {
        return null;
    }

    @Override
    public void deleteFichierLinkForAntille(String numProg) {

    }

    private class FichierExtractor implements ResultSetExtractor<Fichier> {

        public Fichier extractData(ResultSet rs) throws SQLException, DataAccessException {
            return mapFichier(rs);
        }

    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getNomTableFichier() {
        return nomTableFichier;
    }

    public void setNomTableFichier(String nomTableFichier) {
        this.nomTableFichier = nomTableFichier;
    }

    public ResultSetExtractor<Fichier> getFichierExtractor() {
        return fichierExtractor;
    }

    public void setFichierExtractor(ResultSetExtractor<Fichier> fichierExtractor) {
        this.fichierExtractor = fichierExtractor;
    }

    private String getTypeFichierCatcms(String nomFichierCatcms){
        String result;
        if(nomFichierCatcms.startsWith(FileUtils.PREFIX_PRIAM_CATALOGUE_FR)){
            result = FileUtils.CATALOGUE_TYPE_CMS_FR;
        } else {
            result = FileUtils.CATALOGUE_TYPE_CMS_ANF;
        }
        return result;
    }

}
