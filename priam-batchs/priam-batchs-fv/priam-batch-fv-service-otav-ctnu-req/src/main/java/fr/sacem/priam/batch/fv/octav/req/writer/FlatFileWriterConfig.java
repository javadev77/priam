package fr.sacem.priam.batch.fv.octav.req.writer;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.fv.octav.req.dao.FichierJdbcDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;

/**
 * Created by benmerzoukah on 13/06/2018.
 */

@Configuration
public class FlatFileWriterConfig {

    private static final Logger LOG = LoggerFactory.getLogger(FlatFileWriterConfig.class);

    @Autowired
    Admap admap;

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    private String head() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        return
            "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n" +
            "# Fichier a destination de OCTAV;;;;;;;;;;;;;;;;;;;\n"+
            "# en provenance de PRIAM pour service ŒUVRE CTNU;;;;;;;;;;;;;;;;;;;\n"+
            "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n"+
            "# JJ/MM/AAAA HH:MM - Auteur - Objet;;;;;;;;;;;;;;;;;;;\n"+
            "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n"+
            "# " + dateFormat.format(new Date()) + " - PRIAM - Creation\n"+
            "#DEBUT;;;;;;;;;;;;;;;;;;;\n"+
            "#IDE12CMPLX;CDETYPIDE12CMPLX;;;;;;;;;;;;;;;;;;";
    }

    private String foot(Long lignes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        return
                "#FIN;;\n" +
                "#" + dateFormat.format(new Date())  + "\n" +
                "# Nbr lignes de donnees       = " + lignes;
    }

    @Bean(name = "writeReqFile")
    @Scope("step")
    OctavCtnuReqCsvFileItemWriter databaseCsvItemWriter(@Value("#{jobParameters['idFichier']}") String idFichier) {

        OctavCtnuReqCsvFileItemWriter csvFileWriter = new OctavCtnuReqCsvFileItemWriter();

        StringHeaderWriter headerWriter = new StringHeaderWriter(head());
        csvFileWriter.setHeaderCallback(headerWriter);
        csvFileWriter.setFooterCallback(writer -> writer.write(foot(fichierJdbcDao.countNbLignesFvById(idFichier))));


        String fileName = "FF_PRIAM_"  + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
            +"_" + idFichier + "_OeuvreCTNU_REQ.csv";
        csvFileWriter.setResource(new FileSystemResource(admap.getOutputFile() + fileName));

        LineAggregator<LigneProgrammeFV> lineAggregator = createAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    private LineAggregator<LigneProgrammeFV> createAggregator() {
        DelimitedLineAggregator<LigneProgrammeFV> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<LigneProgrammeFV> fieldExtractor = createExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<LigneProgrammeFV> createExtractor() {
        BeanWrapperFieldExtractor<LigneProgrammeFV> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"ide12", "cdeTypIde12"});
        return extractor;
    }
}
