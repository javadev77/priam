package fr.sacem.priam.batch.fv.ad.info.rep.config;

import fr.sacem.priam.batch.common.dao.FichierRepository;
import fr.sacem.priam.batch.common.dao.FichierRepositoryImpl;
import fr.sacem.priam.batch.common.fv.config.CommonBatchConfig;
import fr.sacem.priam.batch.common.fv.reader.CsvRepReader;

import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchServiceImpl;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.fv.ad.info.rep.domain.AyantDroitPers;
import fr.sacem.priam.batch.fv.ad.info.rep.listener.JobListener;
import fr.sacem.priam.batch.fv.ad.info.rep.mapper.PriamFileADInfoItemReader;
import fr.sacem.priam.batch.fv.ad.info.rep.mapper.PriamFileADInfoMapper;
import fr.sacem.priam.batch.fv.ad.info.rep.processor.AdInfoProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.IN_SRV_AD_INFO;

@Configuration
@EnableBatchProcessing
@Profile({"local", "production"})
public class BatchConfig extends CommonBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job jobADInfoREP(Step stepOscarADInfoREP) {
        return jobBuilderFactory.get("jobADInfoREP")
                .listener(listener())
                .start(stepOscarADInfoREP)
                .build();
    }

    @Bean
    public Step stepOscarADInfoREP() {
        return stepBuilderFactory.get("stepOscarADInfoREP").<AyantDroitPers, AyantDroitPers>chunk(2000)
                .reader(multiResourceItemReader())
                .processor(processor())
                .writer(writer(0L))
                .build();
    }

    @Bean
    public JobListener listener(){
        return new JobListener(new UtilFile());
    }

    @Bean
    public AdInfoProcessor processor() {
        return new AdInfoProcessor();
    }

    @Bean
    public CsvRepReader<AyantDroitPers> multiResourceItemReader(){
        CsvRepReader<AyantDroitPers> resourceItemReader = new CsvRepReader<>();
        resourceItemReader.setUtilFile(utilFile());
        resourceItemReader.setDelegate(flatFileItemReader());
        resourceItemReader.setStrict(false);

        return resourceItemReader;
    }

    @Bean
    public PriamFileADInfoItemReader flatFileItemReader(){
        PriamFileADInfoItemReader flatFileItemReader = new PriamFileADInfoItemReader();
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public PriamFileADInfoMapper lineMapper(){
        PriamFileADInfoMapper lineMapper = new PriamFileADInfoMapper();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"numPers", "prenom", "nom", "anneeNaissance", "anneeDeces", "indicSacem", "sousRole"});
        lineTokenizer.setDelimiter(";");
        BeanWrapperFieldSetMapper<AyantDroitPers> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(AyantDroitPers.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<AyantDroitPers> writer(@Value("#{jobExecutionContext['idFichier']}") Long idFichier) {
        JdbcBatchItemWriter<AyantDroitPers> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("UPDATE PRIAM_AYANT_DROIT_PERS AYP " +
                "INNER JOIN PRIAM_AYANT_DROIT AD on AYP.NUMPERS = AD.NUMPERS " +
                "INNER JOIN PRIAM_LIGNE_PROGRAMME_FV LPF on AD.ID_FV = LPF.id " +
                "INNER JOIN PRIAM_FICHIER F on LPF.ID_FICHIER = F.ID " +
                "SET PRENOM =:prenom, " +
                "AYP.NOM =:nom, " +
                "ANNEE_NAISSANCE =:anneeNaissance, " +
                "ANNEE_DECES = :anneeDeces, " +
                "INDICSACEM =:indicSacem, " +
                "SOUS_ROLE =:sousRole " +
                "WHERE AYP.NUMPERS =:numPers " +
                "AND LPF.ID_FICHIER =" + idFichier + " " +
                "AND F.STATUT_ENRICHISSEEMNT = '" + IN_SRV_AD_INFO.getCode() +"'");
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
