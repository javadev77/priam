package fr.sacem.priam.ui.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Configuration
@EnableJpaRepositories(basePackages = "fr.sacem.priam.ui.dao")
@EnableTransactionManagement
public class JpaConfiguration {
  
    @Autowired
    private Environment environment;
  
   
    @Value("${datasource.priam.maxPoolSize:10}")
    private int maxPoolSize;
  
  
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("fr.sacem.priam.ui.domain");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }
  
  /*
    * Configure HikariCP pooled DataSource.
    */
    @Bean
    public DataSource dataSource() {
      DataSourceProperties dataSourceProperties = dataSourceProperties();
      DataSource dataSource =  DataSourceBuilder
        .create(dataSourceProperties.getClassLoader())
        .driverClassName(dataSourceProperties.getDriverClassName())
        .url(dataSourceProperties.getUrl())
        .username(dataSourceProperties.getUsername())
        .password(dataSourceProperties.getPassword())
        .build();
     
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


    private Properties additionalProperties() {
//        Map<String, Object>  properties = new HashMap<>();
//        //properties.put("hibernate.hbm2ddl.auto", "validate");
//        properties.put("hibernate.show_sql", "true");
//        properties.put("hibernate.dialect", MySQL5InnoDBDialect.class.getName());
//        properties.put("hibernate.bytecode.use_reflection_optimizer", true);
//        properties.put("hibernate.jdbc.batch_size", 30);
//        properties.put("hibernate.connection.zeroDateTimeBehavior", "convertToNull");
//        return properties;
  
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.priam.hibernate.dialect"));
        //properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("datasource.priam.hibernate.hbm2ddl.method"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("datasource.priam.hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("datasource.priam.hibernate.format_sql"));
        if(StringUtils.isNotEmpty(environment.getRequiredProperty("datasource.priam.defaultSchema"))){
          properties.put("hibernate.default_schema", environment.getRequiredProperty("datasource.priam.defaultSchema"));
        }
        return properties;
    }

}
