package fr.sacem.util;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Map.Entry;

/**
 * Implementation for {@link ItemSqlParameterSourceProvider},
 * creates {@link MapSqlParameterSource} from {@link FieldSet}.
 *
 * @author Created by fandis on 09/05/2017.
 */
public class FieldSetSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<FieldSet> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlParameterSource createSqlParameterSource(FieldSet item) {
        MapSqlParameterSource sps = new MapSqlParameterSource();
        for (Entry<Object, Object> entry : item.getProperties().entrySet()) {
            sps.addValue(entry.getKey().toString(), entry.getValue());
        }
        return sps;
    }
}
