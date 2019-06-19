package fr.sacem.priam.batch.fv.adclesprotection.rep.mapper;

import fr.sacem.priam.batch.common.fv.util.OctavDTO;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import static fr.sacem.priam.batch.common.util.exception.PriamValidationException.ErrorType.FORMAT_ATTRIBUT;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

public class AdclesProtectionRepLineMapper extends DefaultLineMapper<OctavDTO> {

    private LineTokenizer tokenizer;

    private FieldSetMapper<OctavDTO> fieldSetMapper;

    @Override
    public OctavDTO mapLine(String line, int lineNumber) throws Exception {

        try{
            OctavDTO octavDTO = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            octavDTO.setLineNumber(lineNumber);
            return octavDTO;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new OctavDTO(new PriamValidationException(lineNumber, ex, FORMAT_ATTRIBUT, null));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<OctavDTO> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }
}
