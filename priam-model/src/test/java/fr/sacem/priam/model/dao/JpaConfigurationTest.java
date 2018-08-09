package fr.sacem.priam.model.dao;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Configuration
@PropertySource("classpath:application_test.properties")
@EnableJpaRepositories(basePackages = "fr.sacem.priam.model.dao.jpa")
@Profile("test")
public class JpaConfigurationTest {

    private static Logger logger = LoggerFactory.getLogger(JpaConfigurationTest.class);

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
            Map<String, Object> properties = new HashMap<>();

            properties.put("hibernate.show_sql", true);
            properties.put("hibernate.dialect", MySQL5InnoDBDialect.class.getName());
            properties.put("hibernate.bytecode.use_reflection_optimizer", true);
            properties.put("hibernate.jdbc.batch_size", 50);
            properties.put("hibernate.order_inserts", true);
            properties.put("hibernate.order_updates", true);
            properties.put("hibernate.jdbc.batch_versioned_data", true);
            properties.put("hibernate.connection.zeroDateTimeBehavior", "convertToNull");
            properties.put("javax.persistence.lock.timeout", 2000);

            em.setJpaPropertyMap(properties);

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
    @Bean(
            name = "dataSource")
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
            logger.error(e.getMessage());
            throw new RuntimeException("Erreur lors de l'execution du script SQL", e);
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

