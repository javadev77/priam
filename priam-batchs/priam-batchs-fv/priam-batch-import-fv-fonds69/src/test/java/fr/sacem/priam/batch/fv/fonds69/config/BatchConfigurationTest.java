package fr.sacem.priam.batch.fv.fonds69.config;


import fr.sacem.priam.batch.common.dao.FichierRepositoryImpl;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.listener.importPenef.JobCompletionNotificationLigneProgrammeFVImportPenefListener;
import fr.sacem.priam.batch.common.listener.importPenef.StepArchiveFlatFileListener;
import fr.sacem.priam.batch.common.service.importPenef.*;
import fr.sacem.priam.batch.common.util.DoublePropertyEditor;
import fr.sacem.priam.batch.common.util.LocalDatePropertyEditor;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamFileFVItemReader;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamLigneProgrammeFVSQLMapper;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamLineFVMapper;
import fr.sacem.priam.batch.fv.fonds69.dao.LigneProgrammeFVDaoTest;
import fr.sacem.priam.batch.fv.fonds69.service.importPenef.LigneProgrammeFVProcessorTest;
import org.omg.CORBA.PUBLIC_MEMBER;
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
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.beans.PropertyEditor;
import java.util.HashMap;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

/**
 * Created by embouazzar on 29/11/2018.
 */
@Configuration
@EnableBatchProcessing
//@Profile("test")
public class BatchConfigurationTest {

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
    @StepScope
    public ZipMultiResourceFvFonds69ItemReader<LigneProgrammeFV> multiResourceItemReader() {
        ZipMultiResourceFvFonds69ItemReader<LigneProgrammeFV> resourceItemReader = new ZipMultiResourceFvFonds69ItemReader<>();

        resourceItemReader.setDelegate(reader());
        resourceItemReader.setStrict(false);
        resourceItemReader.setUtilFile(utilFile());
        resourceItemReader.setTypeFichier("type.fichier");
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
    public LigneProgrammeFVProcessorTest processor() {return new LigneProgrammeFVProcessorTest(); }

    @Bean
    public Job archiveFlatFileReaderJob(JobCompletionNotificationLigneProgrammeFVImportPenefListener listener) {
        return jobBuilderFactory.get("archiveFlatFileReaderJob")
                .listener(listener)
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
    public Step archiveFlatFileReaderStep(StepArchiveFlatFileListener listener) {
        return stepBuilderFactory.get("archiveFlatFileReaderStep").<LigneProgrammeFV, LigneProgrammeFV>chunk(2000)
                .reader(multiResourceItemReader())
                .processor(processor())
                .writer(writer())
                .listener(listener)
                .build();
    }

    @Bean
    public Step copyFlatFileReaderStep() {
        return stepBuilderFactory.get("copyFlatFileReaderStep").<LigneProgrammeFV, LigneProgrammeFV>chunk(2000)
                .reader(readerStep2)
                .writer(writerStep2())
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
    FichierBatchService fichierBatchService(){
        FichierBatchService fichierBatchService = new FichierBatchServiceImpl(fichierRepository());
        return fichierBatchService;
    }

    @Bean
    FichierRepositoryImpl fichierRepository(){
        FichierRepositoryImpl fichierRepository = new FichierRepositoryImpl();
        fichierRepository.setNomTableFichier(env.getProperty("nom.table.fichier"));
        fichierRepository.setNomTableLigneProgramme(env.getProperty("nom.table.ligneprogramme"));
        fichierRepository.setNomTableLigneProgrammeLog(env.getProperty("nom.table.fichierlog"));
        fichierRepository.setTypeFichier(env.getProperty("type.fichier"));
        //fichierRepository.setDataSource(dataSource);
        return fichierRepository;
    }

    @Bean
    public UtilFile utilFile(){
        UtilFile utilFile = new UtilFile(fichierBatchService());
        return utilFile;
    }

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {
        return new JobLauncherTestUtils();
    }

    @Bean
    public LigneProgrammeFVDaoTest ligneProgrammeFVDaoTest() {
        LigneProgrammeFVDaoTest ligneProgrammeFVDaoTest = new LigneProgrammeFVDaoTest();
        ligneProgrammeFVDaoTest.setDataSource(dataSource);
        return ligneProgrammeFVDaoTest;
    }


}
