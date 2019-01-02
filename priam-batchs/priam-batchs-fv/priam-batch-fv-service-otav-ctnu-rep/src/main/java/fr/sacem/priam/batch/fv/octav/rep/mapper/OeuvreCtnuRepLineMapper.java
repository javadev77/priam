package fr.sacem.priam.batch.fv.octav.rep.mapper;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import static fr.sacem.priam.batch.common.util.exception.PriamValidationException.ErrorType.FORMAT_ATTRIBUT;
import fr.sacem.priam.batch.fv.octav.rep.reader.OeuvreCtnuRep;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

public class OeuvreCtnuRepLineMapper extends DefaultLineMapper<OeuvreCtnuRep> {

    private LineTokenizer tokenizer;

    private FieldSetMapper<OeuvreCtnuRep> fieldSetMapper;

    @Override
    public OeuvreCtnuRep mapLine(String line, int lineNumber) throws Exception {

        try{

            OeuvreCtnuRep participant = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            participant.setLineNumber(lineNumber);
            return participant;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new OeuvreCtnuRep(new PriamValidationException(lineNumber, ex, FORMAT_ATTRIBUT, null));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<OeuvreCtnuRep> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }
}
