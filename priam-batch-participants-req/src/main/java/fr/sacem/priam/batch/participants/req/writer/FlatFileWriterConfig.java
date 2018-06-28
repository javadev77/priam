package fr.sacem.priam.batch.participants.req.writer;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.participants.req.dao.CatalogueCmsDao;
import fr.sacem.priam.batch.participants.req.domain.CatalogueCms;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by benmerzoukah on 13/06/2018.
 */

@Configuration
public class FlatFileWriterConfig {

    @Autowired
    Admap admap;

    @Autowired
    CatalogueCmsDao catalogueCmsDao;

    private String head() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
        return       "#-------------------------------------------------------------------------------------------------------------------;;\n" +
                     "# Fichier a destination de OCTAV;;\n"+
                     "# en provenance de PRIAM pour reader PARTICIPANTS FRA;;\n"+
                     "#-------------------------------------------------------------------------------------------------------------------;;\n"+
                     "# JJ/MM/AAAA HH:MM - Auteur - Objet;;\n"+
                     "#-------------------------------------------------------------------------------------------------------------------;;\n"+
                     "# " + dateFormat.format(new Date()) + " - PRIAM - Creation\n"+
                     "#DEBUT=====================================================================\n"+
                     "#IDE12;CDETYPIDE12;DATCONSLT";
    }

    private String foot(Long lignes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
        return
                "#FIN;;\n" +
                "#" + dateFormat.format(new Date())  + "\n" +
                "# Nbr lignes de donnees       = " + lignes;
    }

    @Bean(name = "csvFileWriter")
    ItemWriter<CatalogueCms> databaseCsvItemWriter(Environment environment) {
        FlatFileItemWriter<CatalogueCms> csvFileWriter = new ParticipantsReqCsvFileItemWriter();


        StringHeaderWriter headerWriter = new StringHeaderWriter(head());
        csvFileWriter.setHeaderCallback(headerWriter);
        csvFileWriter.setFooterCallback(writer -> writer.write(foot(catalogueCmsDao.countNbLignes("FR"))));

        String fileName = "FF_PRIAM_PARTICIPANTS_FRA_REQ_"
                + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".csv";
        csvFileWriter.setResource(new FileSystemResource(admap.getOutputFile() + fileName));

        LineAggregator<CatalogueCms> lineAggregator = createCatalogueCmsLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    private LineAggregator<CatalogueCms> createCatalogueCmsLineAggregator() {
        DelimitedLineAggregator<CatalogueCms> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<CatalogueCms> fieldExtractor = createStudentFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<CatalogueCms> createStudentFieldExtractor() {
        BeanWrapperFieldExtractor<CatalogueCms> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"ide12", "cdeTypIde12", "datConslt"});
        return extractor;
    }
}
