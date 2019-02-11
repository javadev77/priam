//package fr.sacem.priam.batch.fv.serviceimport.config;
//
//import fr.sacem.priam.batch.common.fv.config.AsyncBatchConfig;
//import static fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum.IN_SRV_AD_INFO;
//import fr.sacem.priam.model.domain.fv.AyantDroitPers;
//import javax.sql.DataSource;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecutionListener;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created with IntelliJ IDEA.
// * @author Yegor Bugayenko (yegor@tpc2.com)
// * @version $Id$
// * @since 1.0
// */
//@Configuration
//@EnableBatchProcessing
//public class BatchConfig extends AsyncBatchConfig {
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//
//    @Autowired
//    DataSource dataSource;
//
//
//    @Bean
//    public Job jobImportFV(Step stepImportFichier) {
//        return jobBuilderFactory.get("jobImportFV")
//            .listener(listener())
//            .start(stepImportFichier)
//            .build();
//    }
//
//    @Bean
//    public JobExecutionListener listener() {
//        return null;
//    }
//
//    @Bean
//    public Step stepImportFichier() {
//        return stepBuilderFactory.get("stepImportFichier").chunk(2000)
//            .reader(multiResourceItemReader())
//            .processor(processor())
//            .writer(writer(0L))
//            .build();
//    }
//
//    @Bean
//    @StepScope
//    public JdbcBatchItemWriter<AyantDroitPers> writer(@Value("#{jobExecutionContext['idFichier']}") Long idFichier) {
//        JdbcBatchItemWriter<AyantDroitPers> writer = new JdbcBatchItemWriter<>();
//        writer.setItemSqlParameterSourceProvider(
//            new BeanPropertyItemSqlParameterSourceProvider<>());
//        writer.setSql("UPDATE PRIAM_AYANT_DROIT_PERS AYP " +
//            "INNER JOIN PRIAM_AYANT_DROIT AD on AYP.NUMPERS = AD.NUMPERS " +
//            "INNER JOIN PRIAM_LIGNE_PROGRAMME_FV LPF on AD.ID_FV = LPF.id " +
//            "INNER JOIN PRIAM_FICHIER F on LPF.ID_FICHIER = F.ID " +
//            "SET PRENOM =:prenom, " +
//            "AYP.NOM =:nom, " +
//            "ANNEE_NAISSANCE =:anneeNaissance, " +
//            "ANNEE_DECES = :anneeDeces, " +
//            "INDICSACEM =:indicSacem, " +
//            "SOUS_ROLE =:sousRole " +
//            "WHERE AYP.NUMPERS =:numPers " +
//            "AND LPF.ID_FICHIER =" + idFichier + " " +
//            "AND F.STATUT_ENRICHISSEEMNT = '" + IN_SRV_AD_INFO.getCode() +"'");
//        writer.setDataSource(dataSource);
//        return writer;
//    }
//}
