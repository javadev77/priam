package fr.sacem.priam.fv.octav.info.oeuvre.req.config;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.listener.JobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Created by embouazzar on 20/12/2018.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    /*@Bean
    public Job jobGenerationREQ(@Autowired JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("jobGenerationREQ")
                .listener(listener())
                .start(stepPrepareAndGenerateREQ())
                .build();
    }*/

    @Bean
    public JobListener listener(){
        JobListener listener = new JobListener();
        listener.setEtapeEnrichissement("OCTAV_INFO_OEUVRE");
        return listener;
    }

    /*@Bean
    public Step stepPrepareAndGenerateREQ(@Autowired StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("archiveFlatFileReaderStep").<LigneProgrammeFV, LigneProgrammeFV>chunk(2000)
                .reader(multiResourceItemReader())
                .processor(processor())
                .writer(writer())
                .listener(listener)
                .build();
    }*/



}
