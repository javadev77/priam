package fr.sacem.priam.batch.fv.serviceimport.writer;

import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcParameterUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class ImportFvJdbcItemWriter implements ImportFvItemWriter<ExportCsvDto>, InitializingBean {
        protected static final Log logger = LogFactory.getLog(ImportFvJdbcItemWriter.class);
        private NamedParameterJdbcOperations namedParameterJdbcTemplate;
        private ItemPreparedStatementSetter<ExportCsvDto> itemPreparedStatementSetter;
        private ItemSqlParameterSourceProvider<ExportCsvDto> itemSqlParameterSourceProvider;
        private String sql;
        private boolean assertUpdates = true;
        private int parameterCount;
        private boolean usingNamedParameters;

    public ImportFvJdbcItemWriter() {
        }

    public void setAssertUpdates(boolean assertUpdates) {
        this.assertUpdates = assertUpdates;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setItemPreparedStatementSetter(ItemPreparedStatementSetter<ExportCsvDto> preparedStatementSetter) {
        this.itemPreparedStatementSetter = preparedStatementSetter;
    }

    public void setItemSqlParameterSourceProvider(ItemSqlParameterSourceProvider<ExportCsvDto> itemSqlParameterSourceProvider) {
        this.itemSqlParameterSourceProvider = itemSqlParameterSourceProvider;
    }

    public void setDataSource(DataSource dataSource) {
        if(this.namedParameterJdbcTemplate == null) {
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        }

    }

    public void setJdbcTemplate(NamedParameterJdbcOperations namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void afterPropertiesSet() {
        Assert.notNull(this.namedParameterJdbcTemplate, "A DataSource or a NamedParameterJdbcTemplate is required.");
        Assert.notNull(this.sql, "An SQL statement is required.");
        List<String> namedParameters = new ArrayList();
        this.parameterCount = JdbcParameterUtils.countParameterPlaceholders(this.sql, namedParameters);
        if(namedParameters.size() > 0) {
            if(this.parameterCount != namedParameters.size()) {
                throw new InvalidDataAccessApiUsageException("You can't use both named parameters and classic \"?\" placeholders: " + this.sql);
            }

            this.usingNamedParameters = true;
        }

        if(!this.usingNamedParameters) {
            Assert.notNull(this.itemPreparedStatementSetter, "Using SQL statement with '?' placeholders requires an ItemPreparedStatementSetter");
        }

    }

    public void write(final List<? extends ExportCsvDto> items) throws Exception {
        if(!items.isEmpty()) {
            if(logger.isDebugEnabled()) {
                logger.debug("Executing batch with " + items.size() + " items.");
            }

            int[] updateCounts = null;
            int value;
            if(!this.usingNamedParameters) {
                updateCounts = (int[])this.namedParameterJdbcTemplate.getJdbcOperations().execute(this.sql, new PreparedStatementCallback<int[]>() {
                    public int[] doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        Iterator i$ = items.iterator();

                        while(i$.hasNext()) {
                            ExportCsvDto item = (ExportCsvDto) i$.next();
                            ImportFvJdbcItemWriter.this.itemPreparedStatementSetter.setValues(item, ps);
                            ps.addBatch();
                        }

                        return ps.executeBatch();
                    }
                });
            } else if(items.get(0) instanceof Map) {
                updateCounts = this.namedParameterJdbcTemplate.batchUpdate(this.sql, (Map[])items.toArray(new Map[0]));
            } else {
                SqlParameterSource[] batchArgs = new SqlParameterSource[items.size()];
                value = 0;

                ExportCsvDto item;
                for(Iterator i$ = items.iterator(); i$.hasNext(); batchArgs[value++] = this.itemSqlParameterSourceProvider.createSqlParameterSource(item)) {
                    item = (ExportCsvDto) i$.next();
                }

                updateCounts = this.namedParameterJdbcTemplate.batchUpdate(this.sql, batchArgs);
            }

            if(this.assertUpdates) {
                for(int i = 0; i < updateCounts.length; ++i) {
                    value = updateCounts[i];
                    if(value == 0) {
                        throw new EmptyResultDataAccessException("Item " + i + " of " + updateCounts.length + " did not update any rows: [" + items.get(i) + "]", 1);
                    }
                }
            }
        }

    }
}
