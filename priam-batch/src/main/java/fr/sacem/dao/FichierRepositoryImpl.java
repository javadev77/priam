package fr.sacem.dao;

import fr.sacem.domain.Fichier;
import fr.sacem.domain.LigneProgramme;
import fr.sacem.util.UtilFile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * Created by fandis on 17/05/2017.
 */
@Repository
public class FichierRepositoryImpl implements FichierRepository {

    private JdbcTemplate jdbcTemplate;
    private ResultSetExtractor<Fichier> fichierExtractor = new FichierExtractor();
    private static final String STATUT_OK = "CHARGEMENT_OK";

    public Fichier findByName(String nomFichier) {
        String sql = "SELECT f.ID,f.DATE_DEBUT_CHGT,f.DATE_FIN_CHGT,f.CDEFAMILTYPUTIL,f.NB_LIGNES,f.NOM,f.CDETYPUTIL,f.STATUT_CODE FROM PRIAM_FICHIER f WHERE f.NOM=?";
        return jdbcTemplate.query(sql, fichierExtractor, nomFichier);
    }

    public void addFichier(Fichier fichier) {
        // traitement des données d'un ficiher
        // insetion des données de ficiher avec le statut EN COURS
        String sql = "INSERT INTO PRIAM_FICHIER(DATE_DEBUT_CHGT,CDEFAMILTYPUTIL,NB_LIGNES,NOM,CDETYPUTIL,STATUT_CODE) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {

            public void setValues(PreparedStatement stmt) throws SQLException {
                stmt.setTimestamp(1, fichier.getDateDebutChargt());
                stmt.setString(2, fichier.getFamille());
                stmt.setLong(3, fichier.getNbLignes());
                stmt.setString(4, fichier.getNom());
                stmt.setString(5, fichier.getTypeUtilisation());
                stmt.setString(6, fichier.getStatut());
            }
        });
    }
    @Override
    public void updateFichierDate(String nomFichier) {
        String sql = "UPDATE PRIAM_FICHIER SET STATUT_CODE=?,DATE_FIN_CHGT=? WHERE NOM=?";
        //String sql = "UPDATE PRIAM_FICHIER SET STATUT_CODE=?,DATE_FIN_CHGT=? WHERE ID=(SELECT ID, max(DATE_DEBUT_CHGT) from PRIAM_FICHIER WHERE NOM=? GROUP BY ID ,MAX(DATE_DEBUT_CHGT))";

        jdbcTemplate.update(sql, new PreparedStatementSetter() {

            public void setValues(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, STATUT_OK);
                stmt.setTimestamp(2, UtilFile.getCurrentTimeStamp());
                stmt.setString(3, nomFichier);

            }
        });
    }

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
}
