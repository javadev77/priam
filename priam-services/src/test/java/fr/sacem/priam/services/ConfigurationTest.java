package fr.sacem.priam.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by benmerzoukah on 28/06/2017.
 */
@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.services", "fr.sacem.priam.common"})
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
    public LigneProgrammeService ligneProgrammeService() {
	  return new LigneProgrammeService();
    }
    
    @Bean
    public FelixDataService felixDataService() {
	  return new FelixDataService();
    }
    
	
}
