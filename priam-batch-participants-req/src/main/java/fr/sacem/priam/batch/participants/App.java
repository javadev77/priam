package fr.sacem.priam.batch.participants;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.participants.config.ConfigurationPriamLocal;
import fr.sacem.priam.batch.participants.config.ConfigurationPriamProd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by benmerzoukah on 16/05/2018.
 *
 * To be completed for Spring Batch developpement
 */
public class App {

    private static  final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {


        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriamLocal.class, ConfigurationPriamProd.class);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jobCsvREQ");
        Admap admap =(Admap) context.getBean("admap");

        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("outputCsvFile", new JobParameter(admap.getOutputFile()));
            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(job, jobParameters);

        } catch (Exception e) {
            LOGGER.error("Error execution", e);
        }

        LOGGER.info("Done");
    }
}
