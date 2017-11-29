package fr.sacem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by fandis on 10/10/2017.
 */
@Configuration
@Import( {ConfigurationPriamLocal.class,ConfigurationPriamProd.class})
public class ConfigurationPriam {

}
