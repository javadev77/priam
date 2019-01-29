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

    public boolean isNumpersExistByIdFichier(Long numPers, Long idFichier) {

        try {
            Long numpers = jdbcTemplate.queryForObject("SELECT NUMPERS FROM PRIAM_AYANT_DROIT " +
                            "INNER JOIN PRIAM_LIGNE_PROGRAMME_FV PLPF on PRIAM_AYANT_DROIT.ID_FV = PLPF.id " +
                            "INNER JOIN PRIAM_FICHIER PF on PLPF.ID_FICHIER = PF.ID " +
                            "WHERE PRIAM_AYANT_DROIT.NUMPERS =? AND PF.ID =? GROUP BY PRIAM_AYANT_DROIT.NUMPERS ",
                    (resultSet, i) -> resultSet.getLong("NUMPERS"), numPers, idFichier);

            return numpers != null;
        }catch (EmptyResultDataAccessException ex) {
            return false;
        }




    }


}
