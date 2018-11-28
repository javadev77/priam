package fr.sacem.priam.batch.catcms.rdo.util.mapper;

import fr.sacem.priam.batch.catcms.rdo.domain.CatalogueRdoCsv;
import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
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
public class PriamLineMapper extends DefaultLineMapper<CatalogueRdoCsv> {

    private LineTokenizer tokenizer;

    private FieldSetMapper<CatalogueRdoCsv> fieldSetMapper;

    @Override
    public CatalogueRdoCsv mapLine(String line, int lineNumber) throws Exception {

        try{

            CatalogueRdoCsv catalogueRdoCsv = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            catalogueRdoCsv.setLineNumber(lineNumber);
            return catalogueRdoCsv;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new CatalogueRdoCsv(new PriamValidationException(lineNumber, ex, FORMAT_ATTRIBUT, null));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<CatalogueRdoCsv> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }



}
