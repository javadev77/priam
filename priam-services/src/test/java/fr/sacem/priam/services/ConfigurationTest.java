package fr.sacem.priam.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.util.HashMap;
import java.util.Map;

import static fr.sacem.priam.common.constants.EnvConstants.FELIX_PREPREP_DIR;

/**
 * Created by benmerzoukah on 28/06/2017.
 */
@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.services", "fr.sacem.priam.common", "fr.sacem.priam.model"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "fr.sacem.priam.common.web.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "fr.sacem.priam.model.config.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "fr.sacem.priam.common.config.WebSocketConfig*")
})
public class ConfigurationTest {
	
    @Bean
    public ProgrammeService programmeService() {
		return new ProgrammeService();
	}
    
    @Bean
    public FichierService fichierService() {
	  return new FichierService();
    }
    
    @Bean
    public LigneProgrammeCPServiceImpl ligneProgrammeService() {
	  return new LigneProgrammeCPServiceImpl();
    }
    
    @Bean
    public FelixDataCPService felixDataService() {
	  return new FelixDataCPService();
    }

    @Bean
    public FelixDataCMSService felixDataCMSService() {
        return new FelixDataCMSService();
    }

    @Bean(name = "configAdmap")
    public Map<String, String> configAdmap() {
        Map<String, String> config = new HashMap<>();

        config.put(FELIX_PREPREP_DIR.property(), "/work");

        return config;
    }
    
	
}
