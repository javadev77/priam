package fr.sacem.priam.batch.common.util.mapper.importPenef;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

/**
 * Created by embouazzar on 23/11/2018.
 */
public class PriamLineFVMapper extends DefaultLineMapper<LigneProgrammeFV> {
    private LineTokenizer tokenizer;

    private FieldSetMapper<LigneProgrammeFV> fieldSetMapper;

    @Override
    public LigneProgrammeFV mapLine(String line, int lineNumber) throws Exception {

        try{

            LigneProgrammeFV lineProgramme = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            lineProgramme.setLineNumber(lineNumber);
            return lineProgramme;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new LigneProgrammeFV(new PriamValidationException(lineNumber, ex, PriamValidationException.ErrorType.FORMAT_ATTRIBUT, null));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<LigneProgrammeFV> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }
}
