package fr.sacem.priam.batch.common.dao;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.util.OctavDTO;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamLigneProgrammeFVSQLMapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    public Long countNbLignesByIdFichier(Long idFichier){
        String sql = "SELECT " +
                "count(l.ID) as NB_LIGNES " +
                "FROM " +
                "PRIAM_LIGNE_PROGRAMME_FV l " +
                "WHERE l.ID_FICHIER=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("NB_LIGNES"), idFichier);
    }

    public LigneProgrammeFV findOeuvreByIde12(Long ide12, Long idFichier) {

        List<LigneProgrammeFV> result = jdbcTemplate.query("SELECT l.* FROM PRIAM_LIGNE_PROGRAMME_FV l WHERE l.ide12=? AND l.ID_FICHIER=?",
            new PriamLigneProgrammeFVSQLMapper(), ide12, idFichier);

        return result != null && result.size() == 1 ? result.get(0) : null;
    }

    public Long countNbLignesInfoOeuvreReqByIdFichier(final Long idFichier) {

        String sql = "SELECT " +
            "count(l.ID) as NB_LIGNES " +
            "FROM " +
            "PRIAM_LIGNE_PROGRAMME_FV l " +
            "WHERE l.ID_FICHIER=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("NB_LIGNES"), idFichier);
    }

    public Long countNbLignesOeuvreCtnuByIdFichier(Long idFichier){
        String sql = "SELECT " +
                "count(*) AS NB_LIGNES FROM (SELECT DISTINCT l.ide12 " +
                "FROM " +
                "PRIAM_LIGNE_PROGRAMME_FV l " +
                "WHERE l.ID_FICHIER=? AND l.cdeTypIde12='COPT') as NB_LIGNES ";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("NB_LIGNES"), idFichier);
    }

    public Long countNbLignesInfosADByIdFichier(Long idFichier){
        String sql = "SELECT COUNT(AD.NUMPERS) AS NB_LIGNES " +
                "FROM PRIAM_AYANT_DROIT AD " +
                "INNER JOIN PRIAM_LIGNE_PROGRAMME_FV l ON l.ID = AD.ID_FV " +
                "INNER JOIN PRIAM_FICHIER f ON l.ID_FICHIER = f.ID " +
                "WHERE f.ID = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("NB_LIGNES"), idFichier);
    }


    @Transactional(value="transactionManager")
    public void majOeuvreWithInfoOctav(final OctavDTO octavDTO) {
        String sql =  "UPDATE PRIAM_LIGNE_PROGRAMME_FV SET datconslt = ?, datsitu = ? WHERE ide12 = ?";
        jdbcTemplate.update(sql, stmt -> {
            DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate localDate = LocalDate.parse(octavDTO.getDatConsult(), yyyyMMdd);
            Date from = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            stmt.setDate(1, new java.sql.Date(from.getTime()));

            localDate = LocalDate.parse(octavDTO.getDatSitu(), yyyyMMdd);
            from = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            stmt.setDate(2, new java.sql.Date(from.getTime()));
            stmt.setString(3, octavDTO.getIde12());
        });
    }

    @Transactional(value="transactionManager")
    public void majDateConsultSitu(Long idFichier){
        String sql =  "UPDATE PRIAM_LIGNE_PROGRAMME_FV SET datconslt = ?, datsitu = ? WHERE ID_FICHIER =? ";
        jdbcTemplate.update(sql, stmt -> {
            String dateJour = new SimpleDateFormat("yyyyMMdd").format(new Date());
            DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate localDate = LocalDate.parse(dateJour, yyyyMMdd);
            Date from = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            stmt.setDate(1, new java.sql.Date(from.getTime()));
            stmt.setDate(2, new java.sql.Date(from.getTime()));
            stmt.setLong(3, idFichier);
        });
    }


    public Long countNbLignesAdclesByIdFichier(Long idFichier){
        String sql = "SELECT " +
            "count(*) AS NB_LIGNES FROM (SELECT DISTINCT l.ide12 " +
            "FROM " +
            "PRIAM_LIGNE_PROGRAMME_FV l " +
            "WHERE l.ID_FICHIER=? AND l.isOeuvreComplex=0 " +
            "GROUP BY l.ide12) as NB_LIGNES ";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("NB_LIGNES"), idFichier);
    }

}
