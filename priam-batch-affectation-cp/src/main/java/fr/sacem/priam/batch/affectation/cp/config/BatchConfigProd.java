package fr.sacem.priam.batch.affectation.cp.config;

import org.springframework.context.annotation.*;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
@Configuration
@ComponentScan(basePackages = "fr.sacem.priam.batch.affectation.cp.*")
@ImportResource(value = "classpath:config/job-configuration.xml")
@Profile({"prod", "re7", "dev"})
@PropertySource("classpath:config/application-batch.properties")
public class BatchConfigProd {

}
