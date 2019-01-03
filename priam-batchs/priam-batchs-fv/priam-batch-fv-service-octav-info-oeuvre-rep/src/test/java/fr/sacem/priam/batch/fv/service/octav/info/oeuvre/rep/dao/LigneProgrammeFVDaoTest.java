package fr.sacem.priam.batch.fv.service.octav.info.oeuvre.rep.dao;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by embouazzar on 06/12/2018.
 */
public class LigneProgrammeFVDaoTest {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public LigneProgrammeFV getLigneProgrammeFVByIde12(Long ide12){
        String sql = "SELECT * " +
                "FROM PRIAM_LIGNE_PROGRAMME_FV " +
                " WHERE ide12=? ";

        return jdbcTemplate.query(sql, resultSet -> {
            LigneProgrammeFV ligneProgrammeFV = new LigneProgrammeFV();
            while (resultSet.next()){
                ligneProgrammeFV.setIdFichier(resultSet.getLong("ID_FICHIER"));
                ligneProgrammeFV.setCdeCisac(resultSet.getString("cdeCisac"));
                ligneProgrammeFV.setIde12(resultSet.getString("ide12"));
                ligneProgrammeFV.setTitreOeuvre(resultSet.getString("titreOeuvre"));
                ligneProgrammeFV.setGenreOeuvre(resultSet.getString("genreOeuvre"));
                ligneProgrammeFV.setPaysOri(resultSet.getString("paysOri"));
            }
            if (ligneProgrammeFV == null) {
                // no rows returned - throw an empty result exception
                throw new EmptyResultDataAccessException(1);
            }
            return ligneProgrammeFV;
        } , ide12);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
