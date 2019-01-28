package fr.sacem.priam.batch.common.dao;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by embouazzar on 22/12/2018.
 */
@Repository
public class AyanrtDroitPersDao {
    private JdbcTemplate jdbcTemplate;

    public AyanrtDroitPersDao(@Autowired DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public boolean isNumpersExist(String numPers) {

        try {
            Long numpers = jdbcTemplate.queryForObject("SELECT NUMPERS  FROM PRIAM_AYANT_DROIT_PERS WHERE NUMPERS=?",
                (resultSet, i) -> resultSet.getLong("NUMPERS"), numPers);

            return numpers != null;
        }catch (EmptyResultDataAccessException ex) {
            return false;
        }




    }


}
