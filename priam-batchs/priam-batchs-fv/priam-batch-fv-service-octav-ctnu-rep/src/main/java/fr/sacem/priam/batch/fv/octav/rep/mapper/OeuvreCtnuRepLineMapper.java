package fr.sacem.priam.batch.fv.octav.rep.mapper;

import fr.sacem.priam.batch.common.domain.OctavCtnu;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import static fr.sacem.priam.batch.common.util.exception.PriamValidationException.ErrorType.FORMAT_ATTRIBUT;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

public class OeuvreCtnuRepLineMapper extends DefaultLineMapper<OctavCtnu> {

    private LineTokenizer tokenizer;

    private FieldSetMapper<OctavCtnu> fieldSetMapper;

    @Override
    public OctavCtnu mapLine(String line, int lineNumber) throws Exception {

        try{

            OctavCtnu octavCtnu = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            octavCtnu.setLineNumber(lineNumber);
            return octavCtnu;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new OctavCtnu(new PriamValidationException(lineNumber, ex, FORMAT_ATTRIBUT, null));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<OctavCtnu> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }
}
