package fr.sacem.priam.model.dao.jpa.fv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class LignePreprepFVJdbcDao {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void deleteByNumprog(String numProg){
        String sql = "DELETE FROM PRIAM_LIGNE_PREPREP_FV WHERE numProg =?";
        jdbcTemplate.update(sql, numProg);
    }



    public Long countNbLignes(Long numProg) {
        String sql =  "SELECT " +
                "count(l.id) as NB_LIGNES " +
                "FROM PRIAM_LIGNE_PREPREP_FV l " +
                "WHERE l.numProg=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("NB_LIGNES"), numProg);
    }

}
