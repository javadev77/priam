package fr.sacem.priam.batch.fv.affectation.config;

import static fr.sacem.priam.common.constants.EnvConstants.PRIAM_DB_JNDI;
import static java.lang.Boolean.TRUE;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */

@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.fv.affectation","fr.sacem.priam.batch.common"})
@Profile({"dev", "prod","re7"})
@PropertySource("classpath:config/application-batch.properties")
@ImportResource(value = "classpath:config/job-affectation-fv.xml")
public class BatchConfigProd {

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
        jndiDataSourceLookup.setResourceRef(TRUE);
        DataSource dataSource = jndiDataSourceLookup.getDataSource(String.valueOf(PRIAM_DB_JNDI));

        return dataSource;
    }



}
