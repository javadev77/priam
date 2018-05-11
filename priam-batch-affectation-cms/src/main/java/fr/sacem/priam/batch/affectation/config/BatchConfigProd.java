package fr.sacem.priam.batch.affectation.config;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.common.constants.EnvConstants;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
@Configuration
@ComponentScan(basePackages = "fr.sacem.priam.batch.affectation.*")
@ImportResource(value = "classpath:config/job-configuration.xml")
@Profile({"prod", "dev", "re7"})
@PropertySource("classpath:config/application-batch.properties")
public class BatchConfigProd {


    private String priamDatasourceJndi;

    private String inputDirectory = String.valueOf(EnvConstants.OCTAV_ZIP_IN);
    private String outputDirectory = String.valueOf(EnvConstants.OCTAV_ZIP_ARCHIVES);


    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
        jndiDataSourceLookup.setResourceRef(Boolean.TRUE);
        this.priamDatasourceJndi = EnvConstants.PRIAM_DB_JNDI.toString();
        DataSource dataSource = jndiDataSourceLookup.getDataSource(priamDatasourceJndi);

        return dataSource;
    }

    @Bean
    public Admap admap(){
        Admap admap = new Admap();
        admap.setInputFile(inputDirectory);
        admap.setOutputFile(outputDirectory);

        return admap;
    }
}
