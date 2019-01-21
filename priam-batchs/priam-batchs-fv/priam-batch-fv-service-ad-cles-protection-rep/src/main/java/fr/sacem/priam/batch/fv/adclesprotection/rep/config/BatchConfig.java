package fr.sacem.priam.batch.fv.adclesprotection.rep.config;

import fr.sacem.priam.batch.common.fv.config.CommonBatchConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
@EnableBatchProcessing
@Configuration
public class BatchConfig extends CommonBatchConfig {

}
