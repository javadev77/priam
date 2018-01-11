package fr.sacem.priam.model.dao;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Configuration
@PropertySource("classpath:application_test.properties")
@EnableJpaRepositories(basePackages = "fr.sacem.priam.model.dao.jpa")
@Profile("test")
public class JpaConfigurationTest {

    @Value("${mariaDB4j.port}")
    private Integer dbPort;
    @Value("${app.mariaDB4j.databaseName}")
    private String dbName;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${spring.datasource.driver-class-name}")
    private String dbDriver;
    @Value("${spring.jpa.database-platform}")
    private String dbDialect;
    private static final String DB_SERVICE = "dbServiceBean";

    @Bean(name = {DB_SERVICE})
    public MariaDB4jSpringService mariaDb() {
        MariaDB4jSpringService mariaDb = new MariaDB4jSpringService();
        mariaDb.setDefaultPort(dbPort);

        return mariaDb;
    }
    @Bean
    @DependsOn("dataSource")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        try {
            em.setDataSource(dataSource(dataSourceProperties()));
            em.setPackagesToScan("fr.sacem.priam.model.domain");
            JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            em.setJpaVendorAdapter(vendorAdapter);
        }catch (ClassNotFoundException e){
            e.getMessage();
        }


        return em;
    }
    @SuppressWarnings("unchecked")
    private Class<Driver> getDriverClassByName(String className) {
        try {
            return (Class<Driver>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean(name = "dataSource")
    @DependsOn(DB_SERVICE)
    @Qualifier("testDS")
    public DataSource dataSource(DataSourceProperties dataSourceProperties) throws ClassNotFoundException {
        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(getDriverClassByName(dataSourceProperties.determineDriverClassName()));
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        try{
            mariaDb().getDB().createDB(dbName);
            mariaDb().getDB().source("init-schema_test.sql");


        }catch(ManagedProcessException e){
            e.getMessage();
        }
        return dataSource;
    }
    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
    @Bean
    @Primary
    public DataSourceProperties dataSourceProperties() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl(dbUrl);
        dataSourceProperties.setUsername(dbUserName);
        dataSourceProperties.setPassword(dbPassword);
        dataSourceProperties.setDriverClassName(dbDriver);
        dataSourceProperties.setPlatform(dbDialect);
        return dataSourceProperties;
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}

