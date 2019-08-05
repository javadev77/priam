package fr.sacem.priam.batch.repartition.fv.dao;

import fr.sacem.priam.batch.repartition.fv.domain.ReferentielParticipation;
import fr.sacem.priam.batch.repartition.fv.mapper.ReferentielParticipationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReferentielJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    public ReferentielJdbcDao(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(readOnly = true, value = "transactionManager")
    public List<ReferentielParticipation> getReferentielByIde12AndCdeTypUtil(Long ide12, String cdeTypUtil){
        String sql = "SELECT r.ide12, r.cdeTypUtil, r.rionPaiementMax FROM PRIAM_REFERENTIEL_PARTICIPATION_FDS r WHERE r.ide12=? AND r.cdeTypUtil=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ReferentielParticipation.class), ide12, cdeTypUtil);
    }

    @Transactional(readOnly = true, value = "transactionManager")
    public List<ReferentielParticipation> getIde12RepartisByNumProg(String numeroProgramme){
        String sql = "SELECT LPFV.ide12, LPFV.cdeTypUtil, LPFV.rionEffet " +
                "FROM PRIAM_LIGNE_PREPREP_FV LPFV " +
                "WHERE LPFV.numProg=? " +
                "GROUP BY LPFV.ide12 ";
        return jdbcTemplate.query(sql, new ReferentielParticipationMapper(), numeroProgramme);
    }

    @Transactional(value = "transactionManager")
    public void enregistrerReferentielParticipation(ReferentielParticipation referentielParticipation){
        String sql = "INSERT INTO PRIAM_REFERENTIEL_PARTICIPATION_FDS (ide12, cdeTypUtil, rionPaiementMax) VALUES (?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, referentielParticipation.getIde12());
            stmt.setString(2, referentielParticipation.getCdeTypUtil());
            stmt.setInt(3, referentielParticipation.getRionPaiementMax());
            return stmt;
        });
    }

    public void updateReferentielParticipation(ReferentielParticipation referentielParticipation){
        String sql = "UPDATE PRIAM_REFERENTIEL_PARTICIPATION_FDS " +
                "set rionPaiementMax =? " +
                "where ide12 =? and cdeTypUtil =?";
        jdbcTemplate.update(sql, stmt -> {
            stmt.setInt(1, referentielParticipation.getRionPaiementMax());
            stmt.setLong(2, referentielParticipation.getIde12());
            stmt.setString(3, referentielParticipation.getCdeTypUtil());
        });
    }

}
