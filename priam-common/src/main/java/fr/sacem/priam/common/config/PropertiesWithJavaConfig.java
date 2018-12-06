package fr.sacem.priam.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by benmerzoukah on 16/06/2017.
 */
@Configuration
@PropertySource("classpath:project.properties")
public class PropertiesWithJavaConfig {
  
  @Bean
  public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
      return new PropertySourcesPlaceholderConfigurer();
  }
}
