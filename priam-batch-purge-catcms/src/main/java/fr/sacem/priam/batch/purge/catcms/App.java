package fr.sacem.priam.batch.purge.catcms;

import fr.sacem.priam.batch.purge.catcms.config.ConfigurationPriamLocal;
import fr.sacem.priam.batch.purge.catcms.config.ConfigurationPriamProd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
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
        Job job = (Job) context.getBean("jobPurgeCatalogue");

        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("param.annee.fr", new JobParameter((Long)context.getBean("paramAnneeFr")));
            jobParametersMap.put("param.annee.anf", new JobParameter((Long)context.getBean("paramAnneeAnf")));

            JobParameters jobParameters = new JobParameters(jobParametersMap);
            JobExecution execution = jobLauncher.run(job, jobParameters);

            LOGGER.info("Exit Status : " + execution.getStatus());
        } catch (Exception e) {
            LOGGER.error("Error execution", e);
        }
        LOGGER.info("Done");

    }
}
