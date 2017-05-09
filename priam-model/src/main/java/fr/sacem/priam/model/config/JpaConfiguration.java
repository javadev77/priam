//package fr.sacem.priam.model.config;
//
//import org.hibernate.dialect.MySQL5InnoDBDialect;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by benmerzoukah on 09/05/2017.
// */
//@Configuration
//@EnableJpaRepositories(basePackages = "fr.sacem.priam.model.dao.jpa")
//@EnableTransactionManagement
//public class JpaConfiguration {
//
//    @Value("${priam.db.jndi}")
//    private String datasourceJndi;
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan("fr.sacem.priam.model.domain");
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        em.setJpaPropertyMap(additionalProperties());
//        return em;
//    }
//
//    @Bean
//    @Primary
//    public DataSource dataSource(){
//        JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
//        dsLookup.setResourceRef(true);
//        return dsLookup.getDataSource(datasourceJndi);
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(emf);
//        return transactionManager;
//    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//
//    private Map<String, Object> additionalProperties() {
//        Map<String, Object>  properties = new HashMap<>();
//        //properties.put("hibernate.hbm2ddl.auto", "validate");
//        properties.put("hibernate.show_sql", "true");
//        properties.put("hibernate.dialect", MySQL5InnoDBDialect.class.getName());
//        properties.put("hibernate.bytecode.use_reflection_optimizer", true);
//        properties.put("hibernate.jdbc.batch_size", 30);
//        properties.put("hibernate.connection.zeroDateTimeBehavior", "convertToNull");
//        return properties;
//    }
//
//}
