package fr.sacem.priam.model.config;

import org.hibernate.dialect.MySQLInnoDBDialect;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Configuration
@EnableJpaRepositories(basePackages = "fr.sacem.priam.model.dao.jpa")
@EnableTransactionManagement
public class JpaConfiguration {
    
    @Autowired
    private Environment environment;
    
    @Value("${priam.db.jndi:jdbc/priamMariaDbDataSource}")
    private String priamDatasourceJndi;
    
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("fr.sacem.priam.model.domain");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(additionalProperties());
        return em;
    }
    
    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
        jndiDataSourceLookup.setResourceRef(Boolean.TRUE);
        DataSource dataSource = jndiDataSourceLookup.getDataSource(priamDatasourceJndi);
        
        return dataSource;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.priam")
    public DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
    }
    
    private  Map<String, Object> additionalProperties() {
        Map<String, Object> properties = new HashMap<>();
        
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.dialect", MySQLInnoDBDialect.class.getName());
        properties.put("hibernate.bytecode.use_reflection_optimizer", true);
        properties.put("hibernate.jdbc.batch_size", 50);
        properties.put("hibernate.order_inserts", true);
        properties.put("hibernate.order_updates", true);
        properties.put("hibernate.jdbc.batch_versioned_data", true);
        properties.put("hibernate.connection.zeroDateTimeBehavior", "convertToNull");
        
        return properties;
    }
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}