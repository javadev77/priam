package fr.sacem.priam.batch.fv.adclesprotection.rep;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.fv.adclesprotection.rep.config.ConfigurationPriamLocal;
import fr.sacem.priam.batch.fv.adclesprotection.rep.config.ConfigurationPriamProd;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */

@SpringBootApplication
public class App {

    private static  final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriamLocal.class, ConfigurationPriamProd.class);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jobADClesProtectionREP");
        Admap admap = (Admap) context.getBean("admap");


        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();

            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("input.archives", new JobParameter(admap.getInputFile()));
            jobParametersMap.put("output.archives", new JobParameter(admap.getOutputFile()));
            jobParametersMap.put("pattern.file.name", new JobParameter(admap.getPatternFileName()));

            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            LOGGER.error(String.format("Exception %s", e));
        }

        LOGGER.info("Done");

    }
}
