package fr.sacem.priam.batch.filiation.npu.dao;

import fr.sacem.priam.batch.common.util.DateTimeUtils;
import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import fr.sacem.priam.model.domain.catcms.JournalCatcms;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by embouazzar on 29/08/2018.
 */
@Repository
@Primary
public class JournalCatcmsRepository {

    private JdbcTemplate jdbcTemplate;


    public void saveJournalCatcms(Long ide12, Long ide12rep){
        List<CatalogueCms> catalogueCmsList = findByIde12(ide12);

        List<JournalCatcms> journalCatcmsList = new ArrayList<>();

        for (CatalogueCms catalogueCms: catalogueCmsList) {

            JournalCatcms journalCatcmsSortieNPU = new JournalCatcms();
            JournalCatcms journalCatcmsCreationNPU = new JournalCatcms();

            journalCatcmsSortieNPU.setEvenement("SORTIE_NPU");
            journalCatcmsCreationNPU.setEvenement("CREATION_NPU");

            journalCatcmsSortieNPU.setIde12(catalogueCms.getIde12());
            journalCatcmsCreationNPU.setIde12(ide12rep);

            journalCatcmsSortieNPU.setDate(new Date());
            journalCatcmsCreationNPU.setDate(new Date());

            journalCatcmsSortieNPU.setUtilisateur("Batch NPU");
            journalCatcmsCreationNPU.setUtilisateur("Batch NPU");

            journalCatcmsSortieNPU.setTypeCMS(catalogueCms.getTypeCMS());
            journalCatcmsCreationNPU.setTypeCMS(catalogueCms.getTypeCMS());

            journalCatcmsList.addAll(Arrays.asList(journalCatcmsSortieNPU, journalCatcmsCreationNPU));
        }

        String sql =  "INSERT INTO PRIAM_CATCMS_JOURNAL (evenement, ide12, date, utilisateur, type_cms) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                JournalCatcms journalCatcms = journalCatcmsList.get(i);
                preparedStatement.setString(1, journalCatcms.getEvenement());
                preparedStatement.setLong(2, journalCatcms.getIde12());
                preparedStatement.setTimestamp(3, new DateTimeUtils().getCurrentTimeStamp());
                preparedStatement.setString(4, journalCatcms.getUtilisateur());
                preparedStatement.setString(5, journalCatcms.getTypeCMS());
            }

            @Override
            public int getBatchSize() {
                return journalCatcmsList.size();
            }
        });

    }

//    private ResultSetExtractor<CatalogueCms> catalogueCmsExtractor = new CatalogueCmsExtractor();

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<CatalogueCms> findByIde12(Long ide12){
        String sql = "SELECT c.* FROM PRIAM_CATCMS_CATALOGUE c WHERE c.IDE12=? AND c.DATE_SORTIE IS NULL";
//        return jdbcTemplate.queryForList (sql, fichierExtractor, ide12);
        return jdbcTemplate.query(sql, new ResultSetExtractor<List<CatalogueCms>>() {
            @Override
            public List<CatalogueCms> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<CatalogueCms> list = new ArrayList<>();
                while(rs.next()){
                    CatalogueCms catalogueCms = new CatalogueCms();
                    catalogueCms.setId(rs.getLong("ID"));
                    catalogueCms.setIde12(rs.getLong("IDE12"));
                    catalogueCms.setTypeCMS(rs.getString("TYPE_CMS"));
                    list.add(catalogueCms);
                }
                return list;
            }
        }, ide12);
    }

    /*private class CatalogueCmsExtractor implements ResultSetExtractor<List<CatalogueCms>> {

        public List<CatalogueCms> extractData(ResultSet rs) throws SQLException, DataAccessException {
            return mapCatalogueCms(rs);
        }

    }

    private List<CatalogueCms> mapCatalogueCms(ResultSet rs) throws SQLException {
        CatalogueCms catalogueCms = null;
        while (rs.next()) {
            if (catalogueCms == null) {
                catalogueCms = new CatalogueCms();
                catalogueCms.setId(rs.getLong("ID"));
                catalogueCms.setIde12(rs.getLong("IDE12"));
                catalogueCms.setTypeCMS(rs.getString("TYPE_CMS"));
            }
        }

        if (catalogueCms == null) {
            // no rows returned - throw an empty result exception
            throw new EmptyResultDataAccessException(1);
        }
        return catalogueCms;
    }*/

}
