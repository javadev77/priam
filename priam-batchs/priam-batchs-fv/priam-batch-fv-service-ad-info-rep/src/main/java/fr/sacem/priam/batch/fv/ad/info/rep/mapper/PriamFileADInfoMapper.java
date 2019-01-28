package fr.sacem.priam.batch.fv.ad.info.rep.mapper;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import fr.sacem.priam.batch.fv.ad.info.rep.domain.AyantDroitPers;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

/**
 * Created by embouazzar on 23/11/2018.
 */
public class PriamFileADInfoMapper extends DefaultLineMapper<AyantDroitPers> {
    private LineTokenizer tokenizer;

    private FieldSetMapper<AyantDroitPers> fieldSetMapper;

    @Override
    public AyantDroitPers mapLine(String line, int lineNumber) throws Exception {

        try{

            AyantDroitPers ayantDroitPers = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            ayantDroitPers.setLineNumber(lineNumber);
            return ayantDroitPers;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new AyantDroitPers(new PriamValidationException(lineNumber, ex, PriamValidationException.ErrorType.FORMAT_ATTRIBUT, null));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<AyantDroitPers> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }
}
