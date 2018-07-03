package fr.sacem.priam.batch.filiation.npu.dao;

import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OeuvreFiliationNPURepositoryForTest {

    private ResultSetExtractor<CatalogueCms> catalogueCmsExtractor = new CatalogueCmsExtractor();

    private JdbcTemplate jdbcTemplate;
    private String nomTableFichier;



    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public CatalogueCms getoeuvre(long ide12Rep){
        String sql = "SELECT * " +
                " FROM " + this.nomTableFichier +
                " WHERE IDE12=? ";
//        return jdbcTemplate.queryForObject(sql, new Object[] {ide12Rep}, CatalogueFra.class);
        return jdbcTemplate.query(sql, catalogueCmsExtractor, ide12Rep) ;
    }

    public String getNomTableFichier() {
        return nomTableFichier;
    }

    public void setNomTableFichier(String nomTableFichier) {
        this.nomTableFichier = nomTableFichier;
    }

    private class CatalogueCmsExtractor implements ResultSetExtractor<CatalogueCms> {
        public CatalogueCms extractData(ResultSet rs) throws SQLException, DataAccessException {
            return mapCatalogueCms(rs);
        }
    }

    private CatalogueCms mapCatalogueCms(ResultSet rs) throws SQLException {
        CatalogueCms catalogueCms = null;
        while (rs.next()) {
            if (catalogueCms == null) {
                catalogueCms = new CatalogueCms();
                catalogueCms.setId(rs.getLong("ID"));
                catalogueCms.setTypeCMS(rs.getString("TYPE_CMS"));
                catalogueCms.setTitre(rs.getString("TITRE"));
            }
        }

        if (catalogueCms == null) {
            // no rows returned - throw an empty result exception
            throw new EmptyResultDataAccessException(1);
        }
        return catalogueCms;
    }

}
