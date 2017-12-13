package fr.sacem.priam.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Created by benmerzoukah on 28/06/2017.
 */
@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.services", "fr.sacem.priam.common", "fr.sacem.priam.model"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "fr.sacem.priam.common.web.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "fr.sacem.priam.model.config.*")
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
    public FelixDataService felixDataService() {
	  return new FelixDataService();
    }
    
	
}
