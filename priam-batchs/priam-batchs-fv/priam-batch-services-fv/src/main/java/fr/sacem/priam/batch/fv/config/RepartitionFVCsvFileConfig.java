package fr.sacem.priam.batch.fv.config;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.common.fv.writer.CsvFileItemWriter;
import fr.sacem.priam.batch.common.fv.writer.StringHeaderWriter;

import fr.sacem.priam.batch.fv.repartition.domain.LignePreprepFV;
import fr.sacem.priam.common.constants.EnvConstants;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.dao.jpa.fv.LignePreprepFVJdbcDao;
import fr.sacem.priam.model.domain.Programme;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.fv", "fr.sacem.priam.model"})
public class RepartitionFVCsvFileConfig {

    private static final String DOC_PREFIX ="FF_PRIAM_PREPREP101_" ;

    @Autowired
    LignePreprepFVJdbcDao lignePreprepFVDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    @Qualifier(value = "configAdmap")
    Map<String, String> configAdmap;

    private String head() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        return "#--------------------------------------------------------------------------\n" +
                "# Extraction a destination de FELIX pour les calculs de repartition\n" +
                "#--------------------------------------------------------------------------" +
                "# JJ/MM/AAAA HH:MM - Auteur - Objet\n" +
                "#--------------------------------------------------------------------------\n" +
                "# "+ dateFormat.format(new Date()) + " - PRIAM - Creation\n" +
                "#DEBUT=====================================================================\n" +
                "#typRepart;cdeCisac;cdeTer;rionEffet;cdeFamilTypUtil;numProg;keyLigPriam;cdeUtil;cdeTypUtil;cdeModFac;cdeTypProg;cdeCompl;libProg;datDbtProg;datFinProg;ide12;cdeTypIde12;datSitu;datConslt;durDif;nbrDif;typMt;mt;cdeTypDrtSacem;coadPayer;idSteAd;rolAd;cleAd;cdeTypProtec;coadOriEdtr;idSteOriEdtr;numCatal;points";
    }

    private String foot(Long lignes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        return
                "#FIN;;\n" +
                        "#" + dateFormat.format(new Date())  + "\n" +
                        "# Nbr lignes de donnees       = " + lignes;
    }

    @Bean(name = "repartitionFVFileWriter")
    @Scope(value = "step")
    CsvFileItemWriter<LignePreprepFV> databaseCsvItemWriter(@Value("#{jobParameters['numProg']}")  Long numProg) {

        Programme programme = programmeDao.findOne(String.valueOf(numProg));

        CsvFileItemWriter<LignePreprepFV> csvFileWriter = new CsvFileItemWriter<>();
        StringHeaderWriter headerWriter = new StringHeaderWriter(head());

        csvFileWriter.setFooterCallback(writer -> writer.write(foot(countNbLignes(numProg))));
        csvFileWriter.setHeaderCallback(headerWriter);

        String fileName = DOC_PREFIX + numProg + "_"
                + programme.getTypeUtilisation().getCode() + "_"
                + programme.getRionTheorique().getRion() + "_"
                + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".csv";
        String preprepDir = configAdmap.get(EnvConstants.FELIX_PREPREP_DIR.property());
        fileName = preprepDir + File.separator + fileName;

        csvFileWriter.setResource(new FileSystemResource(fileName));

        LineAggregator<LignePreprepFV> lineAggregator = createLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    public DelimitedLineAggregator<LignePreprepFV> createLineAggregator() {
        DelimitedLineAggregator<LignePreprepFV> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");


        FieldExtractor<LignePreprepFV> fieldExtractor = createExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }


    public FieldExtractor<LignePreprepFV> createExtractor() {
        BeanWrapperFieldExtractor<LignePreprepFV> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"typRepart", "cdeCisac", "cdeTer", "rionEffet", "cdeFamilTypUtil", "numProg", "id",
                "cdeUtil", "cdeTypUtil", "cdeModFac", "cdeTypProg", "cdeCompl", "libProg", "datDbtProg", "datFinProg", "ide12",
                "cdeTypIde12", "datSitu", "datConslt", "durDif", "nbrDif", "typMt", "mt", "cdeTypDrtSacem", "coadPayer", "idSteAd",
                "rolAd", "cleAd", "cdeTypProtec", "coadOriEdtr", "idSteOriEdtr", "numPers", "numCatal", "points"});
        return extractor;
    }

    public Long countNbLignes(Long numProg) {
        return lignePreprepFVDao.countNbLignes(numProg);
    }

}
