//package fr.sacem.priam.batch.fv.affectation.config;
//
//import javax.sql.DataSource;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JdbcPagingItemReader;
//import org.springframework.batch.item.database.PagingQueryProvider;
//import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
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
//public class BatchAffectationFvConfig {
//
//
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//
//    @Autowired
//    private DataSource dataSource;
//
//
//    @Bean
//    public Job jobAffectationFv() {
//        return jobBuilderFactory.get("jobAffectationFv")
//            .start(stepOne())
//            .build();
//    }
//
//    private Step stepOne() {
//        return stepBuilderFactory.get("stepOne").chunk(2000)
//            .reader(tableFVReader())
//            .writer(writer())
//            .build();
//    }
//
//    @Bean
//    @StepScope
//    private ItemReader<?> tableFVReader() {
//        JdbcPagingItemReader reader = new JdbcPagingItemReader();
//        reader.setDataSource(dataSource);
//        reader.setQueryProvider(queryProvider());
//
//        return null;
//    }
//
//    @Bean
//    @StepScope
//    private PagingQueryProvider queryProvider() {
//        SqlPagingQueryProviderFactoryBean sql = new SqlPagingQueryProviderFactoryBean();
//        sql.setSelectClause("SELECT fv.*");
//        sql.setFromClause("FROM PRIAM_LIGNE_PROGRAMME_FV fv\n" +
//            "INNER JOIN PRIAM_FICHIER f ON fv.ID_FICHIER=f.ID \n" +
//            "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG");
//        sql.setWhereClause("WHERE p.NUMPROG='#{jobParameters[numProg]}' \n" +
//            "AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' \n" +
//            "GROUP BY fv.IDE12 \n" +
//            ") ");
//        sql.setDatabaseType("MYSQL");
//        sql.setDataSource(dataSource);
//        sql.setSortKey("ide12");
//        PagingQueryProvider object = null;
//        try {
//
//            object = sql.getObject();
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//
//        return object;
//    }
//
//    private ItemWriter<? super Object> writer() {
//        return null;
//    }
//
//
//}
