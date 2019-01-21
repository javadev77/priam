package fr.sacem.priam.batch.fv.config;

import fr.sacem.priam.batch.common.dao.FichierRepository;
import fr.sacem.priam.batch.common.dao.FichierRepositoryImpl;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.listener.importPenef.JobCompletionNotificationLigneProgrammeImportPenefListener;
import fr.sacem.priam.batch.common.listener.importPenef.StepArchiveFlatFileListener;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchServiceImpl;
import fr.sacem.priam.batch.common.util.DoublePropertyEditor;
import fr.sacem.priam.batch.common.util.LocalDatePropertyEditor;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamFileFVItemReader;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamLigneProgrammeFVSQLMapper;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamLineFVMapper;
import fr.sacem.priam.batch.fv.processor.LigneProgrammeFVProcessor;
import fr.sacem.priam.batch.fv.reader.CopyFVItemReader;
import fr.sacem.priam.batch.fv.reader.ZipMultiResourceFvFondsItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;

import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by embouazzar on 22/11/2018.
 */
@Configuration
@EnableBatchProcessing
@EnableCaching
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    @Autowired
    private CopyFVItemReader readerStep2;

    @Bean
    public ResourcelessTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JobRepository jobRepository(ResourcelessTransactionManager transactionManager) throws Exception {
        MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(transactionManager);
        mapJobRepositoryFactoryBean.setTransactionManager(transactionManager);
        return mapJobRepositoryFactoryBean.getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        return simpleJobLauncher;
    }

    @Bean
    public Job archiveFlatFileReaderJob() {
        return jobBuilderFactory.get("archiveFlatFileReaderJob")
                .listener(listener())
                .start(archiveFlatFileReaderStep(archiveFlatFileListener()))
                .on("COMPLETED WITH SKIPS")
                .end()
                .from(archiveFlatFileReaderStep(archiveFlatFileListener()))
                .on("COMPLETED").to(copyFlatFileReaderStep())
                .from(archiveFlatFileReaderStep(archiveFlatFileListener()))
                .next(copyFlatFileReaderStep())
                .end()
                .build();
    }


    @Bean
    public JobExecutionListener listener() {
        JobCompletionNotificationLigneProgrammeImportPenefListener listener = new JobCompletionNotificationLigneProgrammeImportPenefListener();
        listener.setFichierBatchService(fichierBatchService());
        listener.setTypeFichier(env.getProperty("type.fichier"));

        return listener;
    }


    @Bean
    @StepScope
    public ZipMultiResourceFvFondsItemReader<LigneProgrammeFV> multiResourceItemReader() {
        ZipMultiResourceFvFondsItemReader<LigneProgrammeFV> resourceItemReader = new ZipMultiResourceFvFondsItemReader<>();

        resourceItemReader.setDelegate(reader());
        resourceItemReader.setStrict(false);
        resourceItemReader.setUtilFile(utilFile());
        resourceItemReader.setTypeFichier(env.getProperty("type.fichier"));

        return resourceItemReader;
    }

    @Bean
    public PriamFileFVItemReader reader(){
        PriamFileFVItemReader reader = new PriamFileFVItemReader();
        reader.setStrict(false);

        reader.setLineMapper(new PriamLineFVMapper(){{
            setLineTokenizer(new DelimitedLineTokenizer(";") {{
                setNames(new String[]{"cdeCisac",
                        "cdeFamilTypUtil",
                        "numProg",
                        "cdeUtil",
                        "cdeTypUtil",
                        "cdeGreDif",
                        "cdeModDif",
                        "cdeTypIde12",
                        "ide12",
                        "durDif",
                        "nbrDif",
                        "mt",
                        "ctna",
                        "paramCoefHor",
                        "durDifCtna",
                        "cdeLng",
                        "indDoubSsTit",
                        "tax",
                        "typMt",
                        "cdeGreIde12Cmplx",
                        "cdeGreIde12",
                        "titreOriCmplx",
                        "titreAltPppalCmplx",
                        "titreOriOeuvPereCmplx",
                        "titreAltOeuvPereCmplx",
                        "titreOeuvre",
                        "cdePaysOriIso4NCmplx",
                        "realisateurCmplx",
                        "roleParticipant1",
                        "nomParticipant1",
                        "cdeTypUtilOri",
                        "cdeFamilTypUtilOri",
                        "rioneffet"
                });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<LigneProgrammeFV>() {{
                setTargetType(LigneProgrammeFV.class);
                HashMap<String, PropertyEditor> customEditors = new HashMap<>();
                customEditors.put("java.time.LocalDate", new LocalDatePropertyEditor());
                customEditors.put("java.lang.Double", new DoublePropertyEditor());
                setCustomEditors(customEditors);
            }});
        }});

        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<LigneProgrammeFV> writer() {
        JdbcBatchItemWriter<LigneProgrammeFV> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO PRIAM_LIGNE_PROGRAMME_TRAITEMENT_BATCH ( id_fichier, " +
                                                              "cdeCisac, " +
                                                              "cdeFamilTypUtil, " +
                                                              "numProg, " +
                                                              "cdeUtil, " +
                                                              "cdeTypUtil, " +
                                                              "cdeGreDif, " +
                                                              "cdeModDif, " +
                                                              "cdeTypIde12, " +
                                                              "ide12, " +
                                                              "durDif, " +
                                                              "nbrDif, " +
                                                              "mt, " +
                                                              "ctna, " +
                                                              "paramCoefHor, " +
                                                              "durDifCtna, " +
                                                              "cdeLng, " +
                                                              "indDoubSsTit, " +
                                                              "tax, " +
                                                              "typMt, " +
                                                              "cdeGreIde12Cmplx, " +
                                                              "cdeGreIde12, " +
                                                              "titreOriCmplx," +
                                                              "titreAltPppalCmplx, " +
                                                              "titreOriOeuvPereCmplx, " +
                                                              "titreAltOeuvPereCmplx, " +
                                                              "titreOeuvre, " +
                                                              "cdePaysOriIso4NCmplx, " +
                                                              "realisateurCmplx, " +
                                                              "roleParticipant1, " +
                                                              "nomParticipant1, " +
                                                              "cdeTypUtilOri, " +
                                                              "cdeFamilTypUtilOri, " +
                                                              "libelleUtilisateur," +
                                                              "ajout) "+
                                                        " VALUES (" +
                                                              ":idFichier, " +
                                                              ":cdeCisac, " +
                                                              ":cdeFamilTypUtil, " +
                                                              ":numProg, " +
                                                              ":cdeUtil, " +
                                                              ":cdeTypUtil, " +
                                                              ":cdeGreDif, " +
                                                              ":cdeModDif, " +
                                                              ":cdeTypIde12, " +
                                                              ":ide12, " +
                                                              ":durDif, " +
                                                              ":nbrDif, " +
                                                              ":mt, " +
                                                              ":ctna, " +
                                                              ":paramCoefHor, " +
                                                              ":durDifCtna, " +
                                                              ":cdeLng, " +
                                                              ":indDoubSsTit, " +
                                                              ":tax, " +
                                                              ":typMt, " +
                                                              ":cdeGreIde12Cmplx, " +
                                                              ":cdeGreIde12, " +
                                                              ":titreOriCmplx, " +
                                                              ":titreAltPppalCmplx, " +
                                                              ":titreOriOeuvPereCmplx, " +
                                                              ":titreAltOeuvPereCmplx, " +
                                                              ":titreOeuvre, " +
                                                              ":cdePaysOriIso4NCmplx, " +
                                                              ":realisateurCmplx, " +
                                                              ":roleParticipant1, " +
                                                              ":nomParticipant1, " +
                                                              ":cdeTypUtilOri, " +
                                                              ":cdeFamilTypUtilOri, " +
                                                              ":cdeUtil, " +
                                                              "'AUTOMATIQUE' )");

        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public LigneProgrammeFVProcessor processor() {return new LigneProgrammeFVProcessor(); }



    @Bean
    public Step archiveFlatFileReaderStep(StepArchiveFlatFileListener listener) {
        return stepBuilderFactory.get("archiveFlatFileReaderStep").<LigneProgrammeFV, LigneProgrammeFV>chunk(100)
                .reader(multiResourceItemReader())
                .processor(processor())
                .writer(writer())
                .listener(listener)
                .build();
    }

    @Bean
    public Step copyFlatFileReaderStep() {
        return stepBuilderFactory.get("copyFlatFileReaderStep").<LigneProgrammeFV, LigneProgrammeFV>chunk(100)
                .reader(readerStep2)
                .writer(compositeItemWriter())
                .build();
    }

    @Bean
    public StepArchiveFlatFileListener archiveFlatFileListener(){
        return new StepArchiveFlatFileListener();
    }

    @Bean
    public PriamLigneProgrammeFVSQLMapper rowMapper() {
        return new PriamLigneProgrammeFVSQLMapper();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<LigneProgrammeFV> writerStep2() {
        JdbcBatchItemWriter<LigneProgrammeFV> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO PRIAM_LIGNE_PROGRAMME_FV ( id_fichier, " +
                "cdeCisac, " +
                "cdeFamilTypUtil, " +
                "numProg, " +
                "cdeUtil, " +
                "cdeTypUtil, " +
                "cdeGreDif, " +
                "cdeModDif, " +
                "cdeTypIde12, " +
                "ide12, " +
                "durDif, " +
                "nbrDif, " +
                "mt, " +
                "ctna, " +
                "paramCoefHor, " +
                "durDifCtna, " +
                "cdeLng, " +
                "indDoubSsTit, " +
                "tax, " +
                "typMt, " +
                "cdeGreIde12Cmplx, " +
                "cdeGreIde12, " +
                "titreOriCmplx," +
                "titreAltPppalCmplx, " +
                "titreOriOeuvPereCmplx, " +
                "titreAltOeuvPereCmplx, " +
                "titreOeuvre, " +
                "cdePaysOriIso4NCmplx, " +
                "realisateurCmplx, " +
                "roleParticipant1, " +
                "nomParticipant1, " +
                "cdeTypUtilOri, " +
                "cdeFamilTypUtilOri, " +
                "libelleUtilisateur," +
                "ajout) "+
                " VALUES (" +
                ":idFichier, " +
                ":cdeCisac, " +
                ":cdeFamilTypUtil, " +
                ":numProg, " +
                ":cdeUtil, " +
                ":cdeTypUtil, " +
                ":cdeGreDif, " +
                ":cdeModDif, " +
                ":cdeTypIde12, " +
                ":ide12, " +
                ":durDif, " +
                ":nbrDif, " +
                ":mt, " +
                ":ctna, " +
                ":paramCoefHor, " +
                ":durDifCtna, " +
                ":cdeLng, " +
                ":indDoubSsTit, " +
                ":tax, " +
                ":typMt, " +
                ":cdeGreIde12Cmplx, " +
                ":cdeGreIde12, " +
                ":titreOriCmplx, " +
                ":titreAltPppalCmplx, " +
                ":titreOriOeuvPereCmplx, " +
                ":titreAltOeuvPereCmplx, " +
                ":titreOeuvre, " +
                ":cdePaysOriIso4NCmplx, " +
                ":realisateurCmplx, " +
                ":roleParticipant1, " +
                ":nomParticipant1, " +
                ":cdeTypUtilOri, " +
                ":cdeFamilTypUtilOri, " +
                ":cdeUtil, " +
                "'AUTOMATIQUE' )");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<LigneProgrammeFV> writerStep2Copy() {
        JdbcBatchItemWriter<LigneProgrammeFV> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO PRIAM_LIGNE_PROGRAMME_FV_COPY ( id_fichier, " +
                "cdeCisac, " +
                "cdeFamilTypUtil, " +
                "numProg, " +
                "cdeUtil, " +
                "cdeTypUtil, " +
                "cdeGreDif, " +
                "cdeModDif, " +
                "cdeTypIde12, " +
                "ide12, " +
                "durDif, " +
                "nbrDif, " +
                "mt, " +
                "ctna, " +
                "paramCoefHor, " +
                "durDifCtna, " +
                "cdeLng, " +
                "indDoubSsTit, " +
                "tax, " +
                "typMt, " +
                "cdeGreIde12Cmplx, " +
                "cdeGreIde12, " +
                "titreOriCmplx," +
                "titreAltPppalCmplx, " +
                "titreOriOeuvPereCmplx, " +
                "titreAltOeuvPereCmplx, " +
                "titreOeuvre, " +
                "cdePaysOriIso4NCmplx, " +
                "realisateurCmplx, " +
                "roleParticipant1, " +
                "nomParticipant1, " +
                "cdeTypUtilOri, " +
                "cdeFamilTypUtilOri, " +
                "libelleUtilisateur," +
                "ajout) "+
                " VALUES (" +
                ":idFichier, " +
                ":cdeCisac, " +
                ":cdeFamilTypUtil, " +
                ":numProg, " +
                ":cdeUtil, " +
                ":cdeTypUtil, " +
                ":cdeGreDif, " +
                ":cdeModDif, " +
                ":cdeTypIde12, " +
                ":ide12, " +
                ":durDif, " +
                ":nbrDif, " +
                ":mt, " +
                ":ctna, " +
                ":paramCoefHor, " +
                ":durDifCtna, " +
                ":cdeLng, " +
                ":indDoubSsTit, " +
                ":tax, " +
                ":typMt, " +
                ":cdeGreIde12Cmplx, " +
                ":cdeGreIde12, " +
                ":titreOriCmplx, " +
                ":titreAltPppalCmplx, " +
                ":titreOriOeuvPereCmplx, " +
                ":titreAltOeuvPereCmplx, " +
                ":titreOeuvre, " +
                ":cdePaysOriIso4NCmplx, " +
                ":realisateurCmplx, " +
                ":roleParticipant1, " +
                ":nomParticipant1, " +
                ":cdeTypUtilOri, " +
                ":cdeFamilTypUtilOri, " +
                ":cdeUtil, " +
                "'AUTOMATIQUE' )");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    @StepScope
    public CompositeItemWriter<LigneProgrammeFV> compositeItemWriter(){
        List<ItemWriter> writers = new ArrayList<>(2);
        writers.add(writerStep2());
        writers.add(writerStep2Copy());
        CompositeItemWriter itemWriter  = new CompositeItemWriter();
        itemWriter.setDelegates(writers);

        return itemWriter;
    }

    @Bean
    public FichierBatchService fichierBatchService(){
        FichierBatchService fichierBatchService = new FichierBatchServiceImpl(fichierRepository());

        return fichierBatchService;
    }

    @Bean
    @Primary
    public FichierRepository fichierRepository(){
        FichierRepositoryImpl fichierRepository = new FichierRepositoryImpl();
        fichierRepository.setNomTableFichier(env.getProperty("nom.table.fichier"));
        fichierRepository.setNomTableLigneProgramme(env.getProperty("nom.table.ligneprogramme"));
        fichierRepository.setNomTableLigneProgrammeLog(env.getProperty("nom.table.fichierlog"));
        fichierRepository.setTypeFichier(env.getProperty("type.fichier"));

        return fichierRepository;
    }

    @Bean
    public UtilFile utilFile(){
        UtilFile utilFile = new UtilFile(fichierBatchService());

        return utilFile;
    }

}
