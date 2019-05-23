package fr.sacem.priam.batch.fv.participation.fonds.mapper;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import fr.sacem.priam.batch.fv.participation.fonds.domain.ReferentielParticipationFonds;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

import static fr.sacem.priam.batch.common.util.exception.PriamValidationException.ErrorType.FORMAT_ATTRIBUT;

public class ReferentielParticipationFondsLineMapper extends DefaultLineMapper<ReferentielParticipationFonds> {

    private LineTokenizer tokenizer;

    private FieldSetMapper<ReferentielParticipationFonds> fieldSetMapper;

    @Override
    public ReferentielParticipationFonds mapLine(String line, int lineNumber) throws Exception {

        try{

            ReferentielParticipationFonds referentielParticipationFonds = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            referentielParticipationFonds.setLineNumber(lineNumber);
            return referentielParticipationFonds;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new ReferentielParticipationFonds(new PriamValidationException(lineNumber, ex, FORMAT_ATTRIBUT, null));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<ReferentielParticipationFonds> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }
}
