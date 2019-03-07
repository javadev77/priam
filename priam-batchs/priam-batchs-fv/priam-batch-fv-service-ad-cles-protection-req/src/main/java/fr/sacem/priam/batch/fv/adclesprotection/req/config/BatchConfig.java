package fr.sacem.priam.batch.fv.adclesprotection.req.config;

import com.google.common.collect.ImmutableMap;
import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.config.CommonBatchConfig;
import fr.sacem.priam.batch.common.fv.listener.FlagDemiInterfaceStepListener;
import fr.sacem.priam.batch.common.fv.writer.CsvFileItemWriter;
import fr.sacem.priam.batch.fv.adclesprotection.req.listener.JobListener;
import fr.sacem.priam.batch.common.fv.util.OctavDTO;
import fr.sacem.priam.batch.common.fv.util.OctavDTO.FondsCategory;
import fr.sacem.priam.batch.fv.adclesprotection.req.reader.AdClesPersReqFVReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by embouazzar on 20/12/2018.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig extends CommonBatchConfig {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;
    //FD03, FD10, FD02, FD07: 'PH' et FD04, FD11, FD13, FD09, FD01, FD05: 'DE'
    private static Map<FondsCategory, String> rightCategoryMap;

    static {
        rightCategoryMap = ImmutableMap.<FondsCategory, String>builder()
            .put(FondsCategory.FD03, "PH")
            .put(FondsCategory.FD10, "PH")
            .put(FondsCategory.FD02, "PH")
            .put(FondsCategory.FD07, "PH")
            .put(FondsCategory.FD04, "DE")
            .put(FondsCategory.FD11, "DE")
            .put(FondsCategory.FD13, "DE")
            .put(FondsCategory.FD01, "DE")
            .put(FondsCategory.FD05, "DE").build();
    }

    @Bean
    public Job jobADClesPersGenerationREQ(Step stepPrepareAndGenerateREQ) {
        return jobBuilderFactory.get("jobADClesPersGenerationREQ")
                .listener(listener())
                .start(stepPrepareAndGenerateREQ)
                .build();
    }

    @Bean
    public JobListener listener(){
        return new JobListener();
    }

    @Bean
    public Step stepPrepareAndGenerateREQ(CsvFileItemWriter<OctavDTO> databaseCsvItemWriter,
        AdClesPersReqFVReader adClesPersReqFVReader, LigneProgrammeFVDao ligneProgrammeFVDao) {
        return stepBuilderFactory.get("stepPrepareAndGenerateREQ").<LigneProgrammeFV, OctavDTO>chunk(100)
                .reader(adClesPersReqFVReader)
                .processor(ligneProgrammeFV -> {

                    OctavDTO octavDTO = new OctavDTO();

                    octavDTO.setCdeCisac(ligneProgrammeFV.getCdeCisac());
                    octavDTO.setCdeTer(250);
                    octavDTO.setCdeFamilTypUtil(ligneProgrammeFV.getCdeFamilTypUtil());
                    octavDTO.setCdeTypUtil(ligneProgrammeFV.getCdeTypUtil());
                    octavDTO.setCdeTypDrtSacem(rightCategoryMap.get(FondsCategory.toEnum(ligneProgrammeFV.getCdeTypUtil())));
                    octavDTO.setIde12(ligneProgrammeFV.getIde12());
                    octavDTO.setCdeTypIde12(ligneProgrammeFV.getCdeTypIde12());

                    String dateJour = new SimpleDateFormat("yyyyMMdd").format(new Date());
                    octavDTO.setDatConsult(dateJour);
                    octavDTO.setDatSitu(dateJour);

                    ligneProgrammeFVDao.majOeuvreWithInfoOctav(octavDTO);

                    return octavDTO;
                })
                .writer(databaseCsvItemWriter)
                .listener(stepListener())
                .build();
    }

    @Bean
    public StepExecutionListener stepListener(){
        return new FlagDemiInterfaceStepListener();
    }
}
