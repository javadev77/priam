package fr.sacem.priam.batch.fv.config;

import fr.sacem.priam.batch.common.dao.FichierRepository;
import fr.sacem.priam.batch.common.dao.FichierRepositoryImpl;
import static fr.sacem.priam.common.constants.EnvConstants.PRIAM_DB_JNDI;
import static java.lang.Boolean.TRUE;
import javax.sql.DataSource;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.common.constants.EnvConstants;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */

@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.fv", "fr.sacem.priam.batch.common", "fr.sacem.priam.model.dao.*"})
@Profile({"dev", "prod","re7"})
@PropertySource("classpath:config/application-batch.properties")
@ImportResource(value = "classpath:config/job-affectation-fv.xml")
public class BatchConfigProd {

    private String outputDirectory = String.valueOf(EnvConstants.PRIAM_EXPORT_PROGRAMME_FV);

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
        jndiDataSourceLookup.setResourceRef(TRUE);
        DataSource dataSource = jndiDataSourceLookup.getDataSource(String.valueOf(PRIAM_DB_JNDI));

        return dataSource;
    }

    @Bean
    public Admap admap(){
        Admap admap = new Admap();
        admap.setOutputFile(outputDirectory);
        return admap;
    }

    @Bean
    @Primary
    public FichierRepository fichierRepositoryFV() {
        FichierRepositoryImpl fichierRepository = new FichierRepositoryImpl();
        fichierRepository.setDataSource(dataSource());
        fichierRepository.setNomTableLigneProgrammeLog("PRIAM_IMPORT_PROGRAMME_FV_LOG");

        return fichierRepository;
    }

}
