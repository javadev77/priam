package fr.sacem.priam.batch.fv.octav.info.oeuvre.rep.config;

import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import fr.sacem.priam.batch.common.dao.FichierRepository;
import fr.sacem.priam.batch.common.dao.FichierRepositoryImpl;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.reader.CsvRepReader;
import fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchServiceImpl;
import fr.sacem.priam.batch.common.util.DoublePropertyEditor;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamFileFVItemReader;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamLineFVMapper;
import fr.sacem.priam.batch.fv.octav.info.oeuvre.rep.listener.JobListener;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.IN_SRV_INFO_OEUVRE;

/**
 * Created by embouazzar on 27/12/2018.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    FichierJdbcDao fichierJdbcDao;

    @Bean
    public Job jobInfoOeuvreREP(Step stepOctavInfosOeuvresREP) {
        return jobBuilderFactory.get("jobInfoOeuvreREP")
                .listener(listener())
                .start(stepOctavInfosOeuvresREP)
                .build();
    }

    @Bean
    public JobListener listener(){
        return new JobListener(new UtilFile());
    }

    @Bean
    public Step stepOctavInfosOeuvresREP() {
        return stepBuilderFactory.get("stepOctavInfosOeuvresREP").<LigneProgrammeFV, LigneProgrammeFV>chunk(2000)
                .reader(multiResourceItemReader())
                .processor(ligneProgrammeFV -> {
                    if(Integer.valueOf(ligneProgrammeFV.getStatut()) >= 0){
                        return ligneProgrammeFV;
                    }
                    return null;
                })
                .writer(writer())
                .build();
    }


    @Bean
    public CsvRepReader<LigneProgrammeFV> multiResourceItemReader(){
        CsvRepReader<LigneProgrammeFV> resourceItemReader = new CsvRepReader<>();
        resourceItemReader.setEtapeEnrichissement(IN_SRV_INFO_OEUVRE);
        resourceItemReader.setFichierJdbcDao(fichierJdbcDao);
        resourceItemReader.setUtilFile(utilFile());
        resourceItemReader.setDelegate(flatFileItemReader());
        resourceItemReader.setStrict(false);

        return resourceItemReader;
    }

    @Bean
    public PriamFileFVItemReader flatFileItemReader(){
        PriamFileFVItemReader flatFileItemReader = new PriamFileFVItemReader();
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public PriamLineFVMapper lineMapper(){
        PriamLineFVMapper lineMapper = new PriamLineFVMapper();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"ide12", "cdeTypIde12", "titreOeuvre", "labelValo", "dureeDeposee", "taxOri", "indicRepart", "genreOeuvre", "paysOri", "statut"});
        lineTokenizer.setDelimiter(";");
        BeanWrapperFieldSetMapper<LigneProgrammeFV> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        Map<Double, DoublePropertyEditor> map = new HashMap<>();
        fieldSetMapper.setCustomEditors(map);
        fieldSetMapper.setTargetType(LigneProgrammeFV.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public JdbcBatchItemWriter<LigneProgrammeFV> writer() {
        JdbcBatchItemWriter<LigneProgrammeFV> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("UPDATE PRIAM_LIGNE_PROGRAMME_FV SET titreOeuvre =:titreOeuvre," +
                "labelValo =:labelValo, " +
                "dureeDeposee =:dureeDeposee, " +
                "taxOri =:taxOri, " +
                "indicRepart = :indicRepart, " +
                "genreOeuvre =:genreOeuvre, " +
                "paysOri =:paysOri, " +
                "statut =:statut " +
                "WHERE ide12=:ide12");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public UtilFile utilFile(){
        UtilFile utilFile = new UtilFile(fichierBatchService());

        return utilFile;
    }

    @Bean
    public FichierBatchService fichierBatchService(){
        FichierBatchService fichierBatchService = new FichierBatchServiceImpl(fichierRepository());

        return fichierBatchService;
    }

    @Bean
    @Primary
    public FichierRepository fichierRepository(){
        return new FichierRepositoryImpl();
    }

}
