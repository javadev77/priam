package fr.sacem.priam.batch.enrichissement.cat.dao;

import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogueCmsRepositoryForTest {

    private JdbcTemplate jdbcTemplate;
    private String nomTableCatalogue;

    private ResultSetExtractor<CatalogueCms> catalogueCmsExtractor = new CatalogueCmsExtractor();


    public CatalogueCms getoeuvre(long ide12Rep){
        String sql = "SELECT * " +
                " FROM " + this.nomTableCatalogue +
                " WHERE IDE12=? ";
        return jdbcTemplate.query(sql, catalogueCmsExtractor, ide12Rep) ;
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String getNomTableCatalogue() {
        return nomTableCatalogue;
    }

    public void setNomTableCatalogue(String nomTableCatalogue) {
        this.nomTableCatalogue = nomTableCatalogue;
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
                catalogueCms.setIde12(rs.getLong("IDE12"));
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
