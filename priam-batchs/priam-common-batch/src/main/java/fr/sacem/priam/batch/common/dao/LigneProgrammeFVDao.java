package fr.sacem.priam.batch.common.dao;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamLigneProgrammeFVSQLMapper;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
            "WHERE l.ID_FICHIER=? AND l.isOeuvreComplex=0";
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

    public Long countNbLignesForExport(Long numProg){
        String sql = "SELECT COUNT(*) AS NB_LIGNES " +
                "FROM PRIAM_LIGNE_PROGRAMME_FV FV " +
                "       INNER JOIN PRIAM_FICHIER PF on FV.ID_FICHIER = PF.ID " +
                "       INNER JOIN PRIAM_PROGRAMME PP on PP.NUMPROG = PF.NUMPROG " +
                "       INNER JOIN PRIAM_AYANT_DROIT PAD on FV.id = PAD.ID_FV " +
                "       INNER JOIN PRIAM_AYANT_DROIT_PERS PERS on PAD.NUMPERS = PERS.NUMPERS " +
                "WHERE PP.NUMPROG =? AND FV.isOeuvreComplex = 0 ";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("NB_LIGNES"), numProg);
    }
}
