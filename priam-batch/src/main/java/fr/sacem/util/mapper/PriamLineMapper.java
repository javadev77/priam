package fr.sacem.util.mapper;

import fr.sacem.domain.LigneProgramme;
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
public class PriamLineMapper extends DefaultLineMapper<LigneProgramme> {

    private LineTokenizer tokenizer;

    private FieldSetMapper<LigneProgramme> fieldSetMapper;

    @Override
    public LigneProgramme mapLine(String line, int lineNumber) throws Exception {

        try{

            LigneProgramme lineProgramme = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            lineProgramme.setLineNumber(lineNumber);
            return lineProgramme;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new LigneProgramme(new PriamValidationException(lineNumber, ex, FORMAT_ATTRIBUT));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<LigneProgramme> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }



}
