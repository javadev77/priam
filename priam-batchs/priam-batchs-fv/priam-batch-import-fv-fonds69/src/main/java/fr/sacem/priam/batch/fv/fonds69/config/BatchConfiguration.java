package fr.sacem.priam.batch.fv.fonds69.config;

import fr.sacem.priam.batch.common.dao.FichierRepositoryImpl;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.listener.importPenef.JobCompletionNotificationLigneProgrammeFVImportPenefListener;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchServiceImpl;
import fr.sacem.priam.batch.common.service.importPenef.LigneProgrammeFVProcessor;
import fr.sacem.priam.batch.common.service.importPenef.ZipMultiResourceFvFonds69ItemReader;
import fr.sacem.priam.batch.common.util.DoublePropertyEditor;
import fr.sacem.priam.batch.common.util.LocalDatePropertyEditor;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamFileFVItemReader;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamLineFVMapper;
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
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.beans.PropertyEditor;
import java.util.HashMap;

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
    public LigneProgrammeFVProcessor processor() {return new LigneProgrammeFVProcessor(); }

    @Bean
    public Job archiveFlatFileReaderJob(JobCompletionNotificationLigneProgrammeFVImportPenefListener listener) {
        return jobBuilderFactory.get("archiveFlatFileReaderJob")
                .listener(listener)
                .start(archiveFlatFileReaderStep())
                .build();
    }

    public Step archiveFlatFileReaderStep() {
        return stepBuilderFactory.get("archiveFlatFileReaderStep").<LigneProgrammeFV, LigneProgrammeFV>chunk(2000)
                .reader(multiResourceItemReader())
                .processor(processor())
                .writer(writer())
                .build();
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

}
