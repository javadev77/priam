package fr.sacem.priam.batch.fv.octav.rep.config;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.config.CommonBatchConfig;
import fr.sacem.priam.batch.common.fv.reader.CsvRepReader;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.batch.fv.octav.rep.listener.JobListener;
import fr.sacem.priam.batch.fv.octav.rep.mapper.OeuvreCtnuRepLineMapper;
import fr.sacem.priam.batch.fv.octav.rep.processor.OctavCtnuRepProcessor;
import fr.sacem.priam.batch.fv.octav.rep.reader.OctavRepFlatItemReader;
import fr.sacem.priam.batch.fv.octav.rep.reader.OeuvreCtnuRep;
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
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by embouazzar on 20/12/2018.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig extends CommonBatchConfig{

    public static final String NAMES = "ide12cmplx;cdetypide12cmplx;ide12;cdetypide12;cdegenreide12cmplx;cdegenreide12;titreoriginalcmplx;titrealternatifprinpalcmplx;titreoriginaloeuvreperecmplx;titrealternatifoeuvreperecmplx;duree;cdepaysorigineiso4n;anneeproduction;realisateur;acteur;producteur;numepisode;seqcuesheet;dureecontenu;statut";
    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    DataSource dataSource;

    @Bean
    public Job jobOctavCtnuRep(Step stepRep) {
        return jobBuilderFactory.get("jobOctavCtnuRep")
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
        return stepBuilderFactory.get("stepRep").<LigneProgrammeFV, LigneProgrammeFV>chunk(100)
                .reader(reader())
                .processor(processor())
                .writer(compositeItemWriter())
                .build();
    }

    @Bean
    public CsvRepReader reader() {
        CsvRepReader<OeuvreCtnuRep> lp = new CsvRepReader<>();
        OctavRepFlatItemReader delegate = new OctavRepFlatItemReader();
        OeuvreCtnuRepLineMapper lineMapper = new OeuvreCtnuRepLineMapper();
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
        beanWrapperFieldSetMapper.setTargetType(OeuvreCtnuRep.class);

        return beanWrapperFieldSetMapper;
    }

    private LineTokenizer delimitedLineTokenizer() {

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(";");
        delimitedLineTokenizer.setNames(NAMES.split(";"));

        return delimitedLineTokenizer;
    }

    @Bean
    public OctavCtnuRepProcessor processor() {
        return new OctavCtnuRepProcessor();
    }

    @Bean
    @StepScope
    public CompositeItemWriter<LigneProgrammeFV> compositeItemWriter(){
        List<ItemWriter> writers = new ArrayList<>(2);
        writers.add(createOeuvreContenu());
        writers.add(updateOeuvreContenu());
        CompositeItemWriter itemWriter  = new CompositeItemWriter();
        itemWriter.setDelegates(writers);

        return itemWriter;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<LigneProgrammeFV> createOeuvreContenu() {
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
            "ajout," +
            "ID_OEUVRE_CTNU) "+
            " VALUES (" +
            ":idFichier, " +
            ":cdeCisac, " +
            ":cdeFamilTypUtil, " +
            ":numProg, " +
            ":cdeUtil, " +
            ":cdeTypUtil, " +
            ":cdeGreDif, " +
            ":cdeModDif, " +
            ":cdetypide12cmplx, " +
            ":ide12Complx, " +
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
            "'AUTOMATIQUE'," +
            " :id)");

        writer.setDataSource(dataSource);

        return writer;
    }


    @Bean
    @StepScope
    public JdbcBatchItemWriter<LigneProgrammeFV> updateOeuvreContenu() {
        JdbcBatchItemWriter<LigneProgrammeFV> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
            new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("UPDATE PRIAM_LIGNE_PROGRAMME_FV SET isOeuvreComplex=1 WHERE id=:id");

        writer.setDataSource(dataSource);

        return writer;
    }


}
