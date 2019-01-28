
package fr.sacem.priam.batch.fv.adclesprotection.rep.config;

import fr.sacem.priam.batch.common.fv.config.CommonBatchConfig;
import fr.sacem.priam.batch.common.fv.reader.CsvRepReader;
import fr.sacem.priam.batch.common.fv.util.OctavDTO;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.fv.adclesprotection.rep.listener.JobListener;
import fr.sacem.priam.batch.fv.adclesprotection.rep.mapper.AdclesProtectionRepLineMapper;
import fr.sacem.priam.batch.fv.adclesprotection.rep.processor.AdclesProtectionRepProcessor;
import fr.sacem.priam.batch.fv.adclesprotection.rep.reader.AdclesProtectionRepFlatItemReader;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */

@EnableBatchProcessing
@Configuration
public class BatchConfig extends CommonBatchConfig {
    public static final String NAMES = "ide12RepCoad;cdeTypIde12RepCoad;rolad;coad;idStead;clead;cdeTypProtec;coadori;idSteOri;numPers;numCatal;statut";

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    DataSource dataSource;

    @Bean
    public Job jobADClesProtectionREP(Step stepRep) {
        return jobBuilderFactory.get("jobADClesProtectionREP")
            .listener(listener())
            .start(stepRep)
            .build();
    }


    @Bean
    public JobListener listener(){
        JobListener jobListener = new JobListener();
        jobListener.setUtilFile(new UtilFile());
        return jobListener;
    }


    @Bean
    public Step stepRep() {
        return stepBuilderFactory.get("stepRep").<OctavDTO, OctavDTO>chunk(1)
            .reader(reader())
            .processor(processor())
            .writer(classifierCompositeItemWriter())
            .build();
    }

    @Bean
    public AdclesProtectionRepProcessor processor() {
        return new AdclesProtectionRepProcessor();
    }

    @Bean
    public CsvRepReader reader() {
        CsvRepReader<OctavDTO> lp = new CsvRepReader<>();
        AdclesProtectionRepFlatItemReader delegate = new AdclesProtectionRepFlatItemReader();
        AdclesProtectionRepLineMapper lineMapper = new AdclesProtectionRepLineMapper();
        lineMapper.setLineTokenizer(delimitedLineTokenizer());
        lineMapper.setFieldSetMapper(fieldSetMapper());

        delegate.setLineMapper(lineMapper);

        lp.setDelegate(delegate);
        lp.setStrict(false);
        lp.setUtilFile(new UtilFile());

        return lp;
    }

    private BeanWrapperFieldSetMapper fieldSetMapper() {
        BeanWrapperFieldSetMapper beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper();
        beanWrapperFieldSetMapper.setTargetType(OctavDTO.class);

        return beanWrapperFieldSetMapper;
    }

    private LineTokenizer delimitedLineTokenizer() {

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(";");
        delimitedLineTokenizer.setNames(NAMES.split(";"));

        return delimitedLineTokenizer;
    }

//    @Bean
//    @StepScope
//    public CompositeItemWriter<LigneProgrammeFV> compositeItemWriter(){
//        List<ItemWriter> writers = new ArrayList<>(2);
//        writers.add(createOeuvreContenu());
//        writers.add(updateOeuvreContenu());
//        CompositeItemWriter itemWriter  = new CompositeItemWriter();
//        itemWriter.setDelegates(writers);
//
//        return itemWriter;
//    }

    @Bean
    @StepScope
    public ClassifierCompositeItemWriter<OctavDTO> classifierCompositeItemWriter() {

        ClassifierCompositeItemWriter<OctavDTO> classifierCompositeItemWriter = new ClassifierCompositeItemWriter<OctavDTO>();
        classifierCompositeItemWriter.setClassifier(new Classifier<OctavDTO, ItemWriter<? super OctavDTO>>() {
            @Override
            public ItemWriter<? super OctavDTO> classify(final OctavDTO octavDTO) {
                if(octavDTO.isNumpersExist()) { // condition
                    return ayantDroitItemWriter(); // I use compositeItemWriter to write on A and B
                } else {
                    return compositItemWriter(); // I use single JdbcBatchItemWriter to write only on A
                }
            }
        });

        return classifierCompositeItemWriter;
    }

    @Bean
    @StepScope
    public ItemWriter<OctavDTO> ayantDroitItemWriter() {
        JdbcBatchItemWriter<OctavDTO> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
            new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO PRIAM_AYANT_DROIT (IDE12REPCOAD, CDETYPIDE12REPCOAD, ROLAD, COAD, IDSTEAD, CLEAD, CDETYPPROTEC, COADORIEDTR, IDSTEORIEDTR, NUMCATAL, NUMPERS, ID_FV) " +
            "VALUES (:ide12RepCoad, :cdeTypIde12RepCoad, :rolad, :coad, :idStead, :clead, :cdeTypProtec, :coadori, :idSteOri, :numCatal, :numPers, :idOeuvreFv) ");

        writer.setDataSource(dataSource);

        return writer;
    }

    @Bean
    @StepScope
    public CompositeItemWriter<OctavDTO> compositItemWriter() {
        List<ItemWriter<? super OctavDTO>> writers = new ArrayList<>(2);
        writers.add(ayantDroitPersWriter());
        writers.add(ayantDroitItemWriter());

        CompositeItemWriter<OctavDTO> itemWriter  = new CompositeItemWriter<>();
        itemWriter.setDelegates(writers);

        return itemWriter;
    }

    @Bean
    @StepScope
    public ItemWriter<OctavDTO> ayantDroitPersWriter() {
        JdbcBatchItemWriter<OctavDTO> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
            new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO PRIAM_AYANT_DROIT_PERS (NUMPERS) VALUES (:numPers) ");

        writer.setDataSource(dataSource);

        return writer;
    }

}

