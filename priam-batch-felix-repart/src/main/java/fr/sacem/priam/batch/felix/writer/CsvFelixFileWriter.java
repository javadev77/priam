package fr.sacem.priam.batch.felix.writer;


import fr.sacem.priam.batch.felix.domain.FelixData;
import fr.sacem.priam.common.constants.EnvConstants;
import fr.sacem.priam.model.dao.jpa.LignePreprepJdbcDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Programme;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by benmerzoukah on 20/07/2018.
 */

/**
 * Created by benmerzoukah on 13/06/2018.
 */

@Configuration
public class CsvFelixFileWriter {
    private static final String DOC_PREFIX ="FF_PRIAM_PREPREP101_" ;
    public static final String NAMES = "cdeCisac;cdeTer;rionEffet;cdeFamilTypUtil;numProg;keyLigPenel;cdeUtil;cdeTypUtil;cdeModFac;cdeTypProg;cdeCompl;libProg;compLibProg;datDbtProg;datFinProg;hrDbt;hrFin;cdeGreDif;cdeModDif;cdeTypIde12;ide12;datDif;hrDif;durDif;nbrDif;mt;ctna;paramCoefHor;durDifCtna;cdeLng;indDoubSsTit;tax";

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    private LignePreprepJdbcDao lignePreprepJdbcDao;

    @Autowired
    @Qualifier(value = "configAdmap")
    Map<String, String> configAdmap;


    private String head() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
        return
                "#--------------------------------------------------------------------------\n" +
                "# Extraction à destination de FELIX pour les calculs de répartition\n"+
                "#--------------------------------------------------------------------------\n"+
                "# JJ/MM/AAAA HH:MM - Auteur - Objet\n"+
                "#--------------------------------------------------------------------------\n"+
                "# " + dateFormat.format(new Date()) + " - PRIAM - Creation\n"+
                "#DEBUT=====================================================================\n" +
                "#cdeCisac;cdeTer;rionEffet;cdeFamilTypUtil;numProg;keyLigPenel;cdeUtil;cdeTypUtil;cdeModFac;cdeTypProg;cdeCompl;libProg;compLibProg;datDbtProg;datFinProg;hrDbt;hrFin;cdeGreDif;cdeModDif;cdeTypIde12;ide12;datDif;hrDif;durDif;nbrDif;mt;ctna;paramCoefHor;durDifCtna;cdeLng;indDoubSsTit;tax";
    }

    private String foot(Long lignes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
        return
                "#FIN " + dateFormat.format(new Date())  + "\n" +
                "# Nbr lignes de donnees       = " + lignes;
    }

    @Bean(name = "csvFileWriter")
    @Scope(value = "step")
    FelixCsvFileItemWriter databaseCsvItemWriter(@Value("#{jobParameters['numProg']}") String numProg) {
        FelixCsvFileItemWriter csvFileWriter = new FelixCsvFileItemWriter();


        csvFileWriter.setHeaderCallback(writer -> writer.write(head()));
        csvFileWriter.setFooterCallback(writer -> writer.write(foot(lignePreprepJdbcDao.countNbLignes(numProg))));

        Programme programme = programmeDao.findOne(numProg);

        String fileName = DOC_PREFIX
                + numProg + "_"
                + programme.getTypeUtilisation().getCode() + "_"
                + programme.getRionTheorique().getRion() + "_"
                + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".csv";


        String preprepDir = configAdmap.get(EnvConstants.FELIX_PREPREP_DIR.property());
        csvFileWriter.setResource(new FileSystemResource(preprepDir + File.separator +  fileName));

        LineAggregator<FelixData> lineAggregator = createCatalogueCmsLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    private LineAggregator<FelixData> createCatalogueCmsLineAggregator() {
        DelimitedLineAggregator<FelixData> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<FelixData> fieldExtractor = createExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<FelixData> createExtractor() {
        BeanWrapperFieldExtractor<FelixData> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(NAMES.split(";"));

        return extractor;
    }
}

