package fr.sacem.priam.batch.common.dao;

import fr.sacem.priam.batch.common.domain.Fichier;
import fr.sacem.priam.batch.common.util.DateTimeUtils;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.common.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by fandis on 17/05/2017.
 */
@Repository
@Primary
public class FichierRepositoryImpl implements FichierRepository {


    private JdbcTemplate jdbcTemplate;
    private String nomTableFichier;
    private String nomTableLigneProgramme;
    private String nomTableLigneProgrammeLog;
    private String typeFichier;
    private ResultSetExtractor<Fichier> fichierExtractor = new FichierExtractor();
    private static final String STATUT_OK = "CHARGEMENT_OK";
    private static final String STATUT_KO = "CHARGEMENT_KO";
    private static final String STATUT_EN_COURS = "EN_COURS";



    public Fichier findByName(String nomFichier) {
        String sql = "SELECT f.ID,f.DATE_DEBUT_CHGT,f.DATE_FIN_CHGT,f.CDEFAMILTYPUTIL,f.NB_LIGNES,f.NOM,f.CDETYPUTIL,f.STATUT_CODE " +
                         "FROM " +this.nomTableFichier+ " f " +
                         "WHERE f.NOM=?";
        return jdbcTemplate.query(sql, fichierExtractor, nomFichier) ;
    }
    
    @Override
    @Transactional
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
        String sql = "SELECT f.ID,f.DATE_DEBUT_CHGT,f.DATE_FIN_CHGT,f.CDEFAMILTYPUTIL,f.NB_LIGNES,f.NOM,f.CDETYPUTIL,f.STATUT_CODE " +
                         "FROM " + this.nomTableFichier +" f " +
                         "WHERE f.ID=?";
        return jdbcTemplate.query(sql, fichierExtractor, String.valueOf(idFichier)) ;
    }

    @Override
    @Transactional
    public void rejeterFichier(Long idFichier, Set<String> errors) {

        String sql = "UPDATE " + this.nomTableFichier + " SET STATUT_CODE=?,DATE_FIN_CHGT=? WHERE ID=?";

        jdbcTemplate.update(sql, stmt -> {
            stmt.setString(1, STATUT_KO);
            stmt.setTimestamp(2, new DateTimeUtils().getCurrentTimeStamp());
            stmt.setLong(3, idFichier);

        });
    }

    @Override
    @Transactional
    public void supprimerLigneProgrammeParIdFichier(Long idFichier) {

        String sql = "DELETE FROM "+ this.nomTableLigneProgramme + " WHERE ID_FICHIER=?";

        jdbcTemplate.update(sql, stmt -> {
            stmt.setLong(1, idFichier);
        });
    }

    @Override
    @Transactional
    public void enregistrerLog(Long idFichier, Set<String> errors) {

        String query = new StringBuilder("INSERT INTO " + this.nomTableLigneProgrammeLog + "(ID_FICHIER, DATE, LOG) VALUES (")
                                        .append(idFichier).append(", ")
                                        .append("NOW()").append(", ")
                                        .append("?)")
                                        .toString();



        List<Object[]> parameters = new ArrayList<>();

        errors.forEach( log -> {
            parameters.add(new Object[] { log });
        });
        jdbcTemplate.batchUpdate(query, parameters);

    }

    @Override
    @Transactional
    public void clearSelectedFichiers(String numProg, String statut) {
        String sql = "UPDATE PRIAM_FICHIER SET NUMPROG = NULL, STATUT_CODE =? WHERE NUMPROG=?";

        jdbcTemplate.update(sql, stmt -> {
            stmt.setString(1, statut);
            stmt.setString(2, numProg);
        });

    }

    @Transactional
    public Long addFichier(Fichier fichier) throws PriamValidationException {
        // traitement des données d'un ficiher
        // insetion des données de ficiher avec le statut EN COURS
        String sql = "INSERT INTO " + this.nomTableFichier + "(DATE_DEBUT_CHGT,CDEFAMILTYPUTIL,NB_LIGNES,NOM,CDETYPUTIL,STATUT_CODE,TYPE_FICHIER) VALUES(?,?,?,?,?,?,?)";

        String typeUtilisation = extractTypeUtilisationFromNomFichier(fichier.getNom());
        String codeFamilleTypeUtilisation = extractCodeFamilleTypeUtilisationFromNomFichier(typeUtilisation);

        try{
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setTimestamp(1, fichier.getDateDebutChargt());
                stmt.setString(2, codeFamilleTypeUtilisation);
                stmt.setLong(3, fichier.getNbLignes());
                stmt.setString(4, fichier.getNom());
                stmt.setString(5, typeUtilisation);
                stmt.setString(6, STATUT_EN_COURS);
                stmt.setString(7,this.typeFichier);
                return stmt;
            }, keyHolder);

            return keyHolder.getKey().longValue();

        }catch (Exception e) {
            throw new PriamValidationException(-1, e, PriamValidationException.ErrorType.FORMAT_FICHIER, null);
        }

    }

    private String extractTypeUtilisationFromNomFichier(String nom) {
        StringBuilder result = new StringBuilder();

        if(nom.startsWith(FileUtils.PREFIX_PRIV_SON_PH))
            result.append(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode());
        else if(nom.startsWith(FileUtils.PREFIX_PRIV_SON_RD))
            result.append(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode());
        else if(nom.startsWith(FileUtils.PREFIX_FRA))
            result.append(TypeUtilisationEnum.CMS_FRA.getCode());
        else if(nom.startsWith(FileUtils.PREFIX_ANT_RION2))
            result.append(TypeUtilisationEnum.CMS_ANT.getCode());
        else if(nom.startsWith(FileUtils.PREFIX_ANT_RION4))
            result.append(TypeUtilisationEnum.CMS_ANT.getCode());
        else if(nom.startsWith(FileUtils.PREFIX_PENEF_FV_FONDS06))
            result.append(TypeUtilisationEnum.FV_FONDS_06.getCode());
        else if(nom.startsWith(FileUtils.PREFIX_PENEF_FV_FONDS09))
            result.append(TypeUtilisationEnum.FV_FONDS_09.getCode());
        if(result.toString().isEmpty())
        {
            return null;
        }
        return result.toString();
    }

    private String extractCodeFamilleTypeUtilisationFromNomFichier(String typeUtilisation) {
        StringBuilder result = new StringBuilder();

        if(typeUtilisation == null || typeUtilisation.isEmpty())
        {
            return null;
        }

        result.append(TypeUtilisationEnum.getValue(typeUtilisation).getCodeFamille());
        return result.toString();
    }


    @Override
    @Transactional
    public void updateFichierDate(String nomFichier) {
        String sql = "UPDATE " + this.nomTableFichier + " SET STATUT_CODE=?,DATE_FIN_CHGT=? " +
                     "WHERE NOM=? AND (STATUT_CODE IS NULL OR STATUT_CODE=?)";
        jdbcTemplate.update(sql, stmt -> {
		stmt.setString(1, STATUT_OK);
		stmt.setTimestamp(2, new DateTimeUtils().getCurrentTimeStamp());
		stmt.setString(3, nomFichier);
		stmt.setString(4, STATUT_EN_COURS);

	  });
    }

    @Override
    @Transactional
    public Long addFichierLink(String numProg) {

        String selectSql = "SELECT f.ID FROM PRIAM_FICHIER f WHERE f.NUMPROG=? AND f.SOURCE_AUTO = 0";

        List<Long> ids = jdbcTemplate.query(selectSql,
                (resultSet, i) -> resultSet.getLong("id"), numProg);

        if(ids.isEmpty()) {

            String sql = "INSERT INTO PRIAM_FICHIER (NUMPROG, SOURCE_AUTO) VALUES (?,?)";

            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, numProg);
                stmt.setBoolean(2, false);
                return stmt;
            }, keyHolder);

            return keyHolder.getKey().longValue();
        } else {
            return ids.get(0);
        }

    }

    @Override
    @Transactional
    public void deleteFichierLinkForAntille(String numProg) {
        String sql = "DELETE FROM PRIAM_FICHIER WHERE NUMPROG=? AND SOURCE_AUTO = 0";

        jdbcTemplate.update(sql, stmt -> stmt.setString(1, numProg));
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public FichierRepositoryImpl() {
    }

    private Fichier mapFichier(ResultSet rs) throws SQLException {
        Fichier fichier = null;
        while (rs.next()) {
            if (fichier == null) {
                fichier = new Fichier();
                fichier.setDateDebutChargt(rs.getTimestamp("DATE_DEBUT_CHGT"));
                fichier.setFamille(rs.getString("CDEFAMILTYPUTIL"));
                fichier.setNom(rs.getString("NOM"));
                fichier.setTypeUtilisation(rs.getString("CDETYPUTIL"));
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

    private class FichierExtractor implements ResultSetExtractor<Fichier> {

        public Fichier extractData(ResultSet rs) throws SQLException, DataAccessException {
            return mapFichier(rs);
        }

    }

    public String getNomTableFichier() {
        return this.nomTableFichier;
    }

    public void setNomTableFichier(String nomTableFichier) {
        this.nomTableFichier = nomTableFichier;
    }

    public String getNomTableLigneProgramme() {
        return nomTableLigneProgramme;
    }

    public void setNomTableLigneProgramme(String nomTableLigneProgramme) {
        this.nomTableLigneProgramme = nomTableLigneProgramme;
    }

    public String getNomTableLigneProgrammeLog() {
        return nomTableLigneProgrammeLog;
    }

    public void setNomTableLigneProgrammeLog(String nomTableLigneProgrammeLog) {
        this.nomTableLigneProgrammeLog = nomTableLigneProgrammeLog;
    }

    public String getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(String typeFichier) {
        this.typeFichier = typeFichier;
    }
}
