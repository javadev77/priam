package fr.sacem.priam.batch.purge.catcms.dao;

import fr.sacem.priam.batch.common.domain.CatalogueCms;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogueCMSRepositoryForTest {

    private ResultSetExtractor<CatalogueCms> catalogueCmsExtractor = new CatalogueCmsExtractor();

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public CatalogueCms getOeuvreByIde12AndTypeCMS(long ide12, String typeCMS){
        String sql = "SELECT * " +
                " FROM PRIAM_CATCMS_CATALOGUE" +
                " WHERE IDE12=? AND TYPE_CMS=?";
        return jdbcTemplate.query(sql, catalogueCmsExtractor, ide12, typeCMS);
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
                catalogueCms.setId(rs.getLong("IDE12"));
                catalogueCms.setTypeCMS(rs.getString("TYPE_CMS"));
//                catalogueCms.setDateSortie(rs.getDate("DATE_SORTIE").toLocalDate());
                Date dateSortie = rs.getDate("DATE_SORTIE");
                catalogueCms.setDateSortie(dateSortie != null ? dateSortie.toLocalDate() : null);
            }
        }

        /*if (catalogueCms == null) {
            // no rows returned - throw an empty result exception
            throw new EmptyResultDataAccessException(1);
        }*/
        return catalogueCms;
    }

}
