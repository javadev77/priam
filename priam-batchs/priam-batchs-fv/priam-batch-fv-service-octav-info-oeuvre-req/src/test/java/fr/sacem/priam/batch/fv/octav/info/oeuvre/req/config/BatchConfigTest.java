package fr.sacem.priam.batch.fv.octav.info.oeuvre.req.config;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.listener.FlagDemiInterfaceStepListener;
import fr.sacem.priam.batch.common.fv.writer.CsvFileItemWriter;
import fr.sacem.priam.batch.fv.octav.info.oeuvre.req.listener.JobListener;
import fr.sacem.priam.batch.fv.octav.info.oeuvre.req.reader.InfoOeuvreReqReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 * Created by embouazzar on 20/12/2018.
 */
@Configuration
@EnableBatchProcessing
@Profile("test")
public class BatchConfigTest {


    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    InfoOeuvreReqReader infoOeuvreReqReader;


    @Bean
    public Job jobGenerationREQ(Step stepPrepareAndGenerateREQ) {
        return jobBuilderFactory.get("jobGenerationREQ")
                .listener(listener())
                .start(stepPrepareAndGenerateREQ)
                .build();
    }

    @Bean
    public JobListener listener(){
        return new JobListener();
    }

    @Bean
    public Step stepPrepareAndGenerateREQ(CsvFileItemWriter<LigneProgrammeFV> databaseCsvItemWriter) {
        return stepBuilderFactory.get("stepPrepareAndGenerateREQ").<LigneProgrammeFV, LigneProgrammeFV>chunk(2000)
                .reader(infoOeuvreReqReader)
                .writer(databaseCsvItemWriter)
                .listener(stepListener())
                .build();
    }

    @Bean
    public StepExecutionListener stepListener(){
        return new FlagDemiInterfaceStepListener();
    }

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {
        return new JobLauncherTestUtils();
    }

}
