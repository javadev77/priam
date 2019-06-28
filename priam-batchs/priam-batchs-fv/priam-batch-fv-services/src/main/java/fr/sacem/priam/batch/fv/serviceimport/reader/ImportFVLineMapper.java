package fr.sacem.priam.batch.fv.serviceimport.reader;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

/**
 * Created by embouazzar on 23/11/2018.
 */
public class ImportFVLineMapper extends DefaultLineMapper<ExportCsvDto> {
    private LineTokenizer tokenizer;

    private FieldSetMapper<ExportCsvDto> fieldSetMapper;

    @Override
    public ExportCsvDto mapLine(String line, int lineNumber) throws Exception {

        try{

            ExportCsvDto exportCsvDto = fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
            exportCsvDto.setLineNumber(lineNumber);
            return exportCsvDto;

        } catch ( IncorrectTokenCountException e ) {
            throw e;
        } catch ( BindException | RuntimeException ex) {
            return new ExportCsvDto(new PriamValidationException(lineNumber, ex, PriamValidationException.ErrorType.FORMAT_ATTRIBUT, null));
        }


    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<ExportCsvDto> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }
}
