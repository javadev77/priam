package fr.sacem.util.mapper;

import fr.sacem.domain.Repartition;
import fr.sacem.util.exception.PriamValidationException;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import static fr.sacem.util.exception.PriamValidationException.ErrorType.FORMAT_ATTRIBUT;

/**
 * Created by belwidanej on 29/08/2017.
 */
public class CsvPriamLineMapper extends DefaultLineMapper<Repartition> {

    private LineTokenizer tokenizer;

    private FieldSetMapper<Repartition> fieldSetMapper;

    @Override
    public Repartition mapLine(String line, int lineNumber) throws Exception {

        try{

            Repartition repartition = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            repartition.setLineNumber(lineNumber);
            return repartition;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new Repartition(new PriamValidationException(lineNumber, ex, FORMAT_ATTRIBUT, null));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<Repartition> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }



}
