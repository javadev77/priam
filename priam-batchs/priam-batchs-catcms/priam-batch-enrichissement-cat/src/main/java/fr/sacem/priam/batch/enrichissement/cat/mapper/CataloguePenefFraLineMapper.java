package fr.sacem.priam.batch.enrichissement.cat.mapper;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import fr.sacem.priam.batch.enrichissement.cat.domain.CataloguePenefFra;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

import static fr.sacem.priam.batch.common.util.exception.PriamValidationException.ErrorType.FORMAT_ATTRIBUT;

/**
 * Created by belwidanej on 29/08/2017.
 */
public class CataloguePenefFraLineMapper extends DefaultLineMapper<CataloguePenefFra> {

    private LineTokenizer tokenizer;

    private FieldSetMapper<CataloguePenefFra> fieldSetMapper;

    @Override
    public CataloguePenefFra mapLine(String line, int lineNumber) throws Exception {

        try{

            CataloguePenefFra catalogueRdoCsv = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            catalogueRdoCsv.setLineNumber(lineNumber);
            return catalogueRdoCsv;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new CataloguePenefFra(new PriamValidationException(lineNumber, ex, FORMAT_ATTRIBUT, null));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<CataloguePenefFra> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }



}
