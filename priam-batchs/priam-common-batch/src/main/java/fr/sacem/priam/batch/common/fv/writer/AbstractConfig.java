package fr.sacem.priam.batch.common.fv.writer;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.domain.Admap;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

public abstract class AbstractConfig<T> {

    private LigneProgrammeFVDao ligneProgrammeFVDao;

    public abstract String head();

    public abstract  FieldExtractor<T> createExtractor();

    public abstract String foot(Long lignes);

    public abstract String suffixNomFicher();

    public abstract Long countNbLignes(Long idFichier);


    @Bean
    @StepScope
    public CsvFileItemWriter databaseCsvItemWriter(@Value("#{jobParameters['idFichier']}")  Long idFichier, @Autowired
            Admap admap) {



        CsvFileItemWriter<T> csvFileWriter = new CsvFileItemWriter<>();

        StringHeaderWriter headerWriter = new StringHeaderWriter(head());
        csvFileWriter.setHeaderCallback(headerWriter);
        csvFileWriter.setFooterCallback(writer -> writer.write(foot(countNbLignes(idFichier))));

        String fileName = "FF_PRIAM_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + "_" + idFichier + suffixNomFicher();

        fileName = admap.getOutputFile() + fileName;
        csvFileWriter.setResource(new FileSystemResource(fileName));

        LineAggregator<T> lineAggregator = createAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    private LineAggregator<T> createAggregator() {
        DelimitedLineAggregator<T> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        lineAggregator.setFieldExtractor(createExtractor());

        return lineAggregator;
    }






}
