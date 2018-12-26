package fr.sacem.priam.batch.common.fv.reader;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamLigneProgrammeFVSQLMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by embouazzar on 20/12/2018.
 */
@Component
@StepScope
public class TableLigneProgrammeFVReader extends JdbcPagingItemReader<LigneProgrammeFV> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableLigneProgrammeFVReader.class);

    public TableLigneProgrammeFVReader(@Value("#{jobParameters['idFichier']}") Long idFichier, @Autowired DataSource dataSource) {
        final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
        sqlPagingQueryProviderFactoryBean.setSelectClause("SELECT l.*");
        sqlPagingQueryProviderFactoryBean.setFromClause("FROM PRIAM_LIGNE_PROGRAMME_FV l " +
                "INNER JOIN PRIAM_FICHIER f ON l.ID_FICHIER=f.ID");
        sqlPagingQueryProviderFactoryBean.setWhereClause("WHERE f.ID = " + idFichier);
        sqlPagingQueryProviderFactoryBean.setSortKey("id");

        try {
            this.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
            this.setDataSource(dataSource);
            this.setPageSize(2000);
            this.setRowMapper(new PriamLigneProgrammeFVSQLMapper());
        } catch (Exception e) {
            LOGGER.error(String.format("Exception %s", e));
        }
    }
}
