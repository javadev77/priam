package fr.sacem.priam.batch.fv.adclesprotection.req.config;

import com.google.common.collect.ImmutableMap;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.config.CommonBatchConfig;
import fr.sacem.priam.batch.common.fv.listener.FlagDemiInterfaceStepListener;
import fr.sacem.priam.batch.common.fv.writer.CsvFileItemWriter;
import fr.sacem.priam.batch.fv.adclesprotection.req.listener.JobListener;
import fr.sacem.priam.batch.fv.adclesprotection.req.model.OctavDTO;
import fr.sacem.priam.batch.fv.adclesprotection.req.model.OctavDTO.FondsCategory;
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

    private static Map<FondsCategory, String> rightCategoryMap = ImmutableMap.of(
        FondsCategory.FD01, "DE",
        FondsCategory.FD02, "PH",
        FondsCategory.FD03, "PH") ;

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
        AdClesPersReqFVReader adClesPersReqFVReader) {
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
