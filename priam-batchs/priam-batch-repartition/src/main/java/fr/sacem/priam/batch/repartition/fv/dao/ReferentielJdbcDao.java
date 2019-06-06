package fr.sacem.priam.batch.repartition.fv.dao;

import fr.sacem.priam.batch.repartition.fv.domain.ReferentielParticipation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
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

}
