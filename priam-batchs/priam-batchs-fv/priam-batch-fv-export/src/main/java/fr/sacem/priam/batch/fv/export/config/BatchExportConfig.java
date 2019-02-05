package fr.sacem.priam.batch.fv.export.config;

import fr.sacem.priam.batch.common.fv.config.CommonBatchConfig;
import fr.sacem.priam.batch.common.fv.writer.CsvFileItemWriter;
import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import fr.sacem.priam.batch.fv.export.reader.ExportCsvReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchExportConfig extends CommonBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    ExportCsvReader exportCsvReader;

    @Bean
    public Job jobExport(Step stepGenerateCsv) {
        return jobBuilderFactory.get("jobExport")
            .start(stepGenerateCsv)
            .build();
    }

    @Bean
    public Step stepGenerateCsv(CsvFileItemWriter<ExportCsvDto> databaseCsvItemWriter) {
        return stepBuilderFactory.get("stepPrepareAndGenerateREQ").<ExportCsvDto, ExportCsvDto>chunk(2000)
                .reader(exportCsvReader)
                .writer(databaseCsvItemWriter)
                .build();
    }

}
