package fr.sacem.priam.batch.fv.octav.req.config;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.listener.FlagDemiInterfaceStepListener;
import fr.sacem.priam.batch.fv.octav.req.reader.OctavCtnuReqReader;
import fr.sacem.priam.batch.common.fv.writer.CsvFileItemWriter;
import fr.sacem.priam.batch.common.domain.OctavCtnu;
import fr.sacem.priam.batch.fv.octav.req.listener.JobListener;
import fr.sacem.priam.batch.fv.octav.req.processor.OctavCtnuReqProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by embouazzar on 20/12/2018.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {


    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;



    @Bean
    public Job jobGenerationCtnuREQ(Step stepPrepareAndGenerateREQ) {
        return jobBuilderFactory.get("jobGenerationCtnuREQ")
                .listener(listener())
                .start(stepPrepareAndGenerateREQ)
                .build();
    }

    @Bean
    public JobListener listener(){
        return new JobListener();
    }

    @Bean
    public Step stepPrepareAndGenerateREQ(CsvFileItemWriter<OctavCtnu> databaseCsvItemWriter, OctavCtnuReqReader octavCtnuReqReader) {
        return stepBuilderFactory.get("stepPrepareAndGenerateREQ").
                <LigneProgrammeFV, OctavCtnu>chunk(2000)
                .reader(octavCtnuReqReader)
                .processor(octavCtnuProcessor())
                .writer(databaseCsvItemWriter)
                .listener(stepListener())
                .build();
    }

    @Bean
    public ItemProcessor<LigneProgrammeFV, OctavCtnu> octavCtnuProcessor() {
        return new OctavCtnuReqProcessor();
    }

    @Bean
    public StepExecutionListener stepListener(){
        return new FlagDemiInterfaceStepListener();
    }
}
