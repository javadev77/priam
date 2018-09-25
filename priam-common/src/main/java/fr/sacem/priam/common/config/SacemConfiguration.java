package fr.sacem.priam.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * @author <a href="mailto:xchen@palo-it.com">Xi CHEN</a>
 * @since 17/11/14.
 */
@Configuration
public class SacemConfiguration {

    @Autowired
    private Environment env;

    @Bean(name = "propertyConfigurer")
    public static PropertyPlaceholderConfigurer placeholderConfigurer(){
        Properties properties = new Properties(); // here we can set pure technical property default value
        properties.setProperty("endpoints.health.sensitive", "false");

        HomerPropertyPlaceholderConfigurer configurer = new HomerPropertyPlaceholderConfigurer();
        configurer.setProperties(properties);
        configurer.setFileEncoding("UTF-8");
        configurer.setIgnoreResourceNotFound(true);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        return configurer;
    }


}
