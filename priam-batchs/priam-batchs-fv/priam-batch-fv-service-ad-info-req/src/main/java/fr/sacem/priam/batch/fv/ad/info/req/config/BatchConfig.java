package fr.sacem.priam.batch.fv.ad.info.req.config;

import fr.sacem.priam.batch.common.fv.config.CommonBatchConfig;
import fr.sacem.priam.batch.common.fv.listener.FlagDemiInterfaceStepListener;
import fr.sacem.priam.batch.common.fv.writer.CsvFileItemWriter;
import fr.sacem.priam.batch.fv.ad.info.req.listener.JobListener;
import fr.sacem.priam.batch.fv.ad.info.req.domain.AyantDroit;
import fr.sacem.priam.batch.fv.ad.info.req.reader.TableAyantDroitReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@EnableBatchProcessing
@Profile({"local", "production"})
public class BatchConfig extends CommonBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    TableAyantDroitReader tableAyantDroitReader;


    @Bean
    public Job jobGenerationADInfoREQ(Step stepPrepareAndGenerateREQ) {
        return jobBuilderFactory.get("jobGenerationADInfoREQ")
                .listener(listener())
                .start(stepPrepareAndGenerateREQ)
                .build();
    }

    @Bean
    public JobListener listener(){
        return new JobListener();
    }

    @Bean
    public Step stepPrepareAndGenerateREQ(CsvFileItemWriter<AyantDroit> databaseCsvItemWriter) {
        return stepBuilderFactory.get("stepPrepareAndGenerateREQ").<AyantDroit, AyantDroit>chunk(2000)
                .reader(tableAyantDroitReader)
                .writer(databaseCsvItemWriter)
                .listener(stepListener())
                .build();
    }

    @Bean
    public StepExecutionListener stepListener(){
        return new FlagDemiInterfaceStepListener();
    }

}
