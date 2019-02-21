package fr.sacem.priam.batch.fv.ad.info.req.reader;

import fr.sacem.priam.batch.common.fv.mapper.AyantDroitMapper;
import fr.sacem.priam.batch.common.domain.AyantDroit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@StepScope
public class TableAyantDroitReader extends JdbcPagingItemReader<AyantDroit> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableAyantDroitReader.class);

    public TableAyantDroitReader(@Value("#{jobParameters['idFichier']}") Long idFichier, @Autowired DataSource dataSource) {

        final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
                sqlPagingQueryProviderFactoryBean.setSelectClause("SELECT AD.ID, AD.NUMPERS");
        sqlPagingQueryProviderFactoryBean.setFromClause("FROM PRIAM_AYANT_DROIT AD " +
                "INNER JOIN PRIAM_LIGNE_PROGRAMME_FV l ON AD.ID_FV = l.ID " +
                "INNER JOIN PRIAM_FICHIER f ON l.ID_FICHIER=f.ID ");
        sqlPagingQueryProviderFactoryBean.setWhereClause("WHERE f.ID = " + idFichier);
        sqlPagingQueryProviderFactoryBean.setGroupClause("GROUP BY AD.NUMPERS");
        sqlPagingQueryProviderFactoryBean.setSortKey("AD.ID");

        try {
            this.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
            this.setDataSource(dataSource);
            this.setPageSize(2000);
            this.setRowMapper(new AyantDroitMapper());
        } catch (Exception e) {
            LOGGER.error(String.format("Exception %s", e));
        }

    }
}
