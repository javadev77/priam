package fr.sacem.priam.batch.participants.rep.mapper;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import fr.sacem.priam.batch.participants.rep.domain.Participant;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

import static fr.sacem.priam.batch.common.util.exception.PriamValidationException.ErrorType.FORMAT_ATTRIBUT;

public class ParticipantFraLineMapper extends DefaultLineMapper<Participant> {

    private LineTokenizer tokenizer;

    private FieldSetMapper<Participant> fieldSetMapper;

    @Override
    public Participant mapLine(String line, int lineNumber) throws Exception {

        try{

            Participant participant = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            participant.setLineNumber(lineNumber);
            return participant;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new Participant(new PriamValidationException(lineNumber, ex, FORMAT_ATTRIBUT, null));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<Participant> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }
}
