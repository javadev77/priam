package fr.sacem.priam.batch.enrichissement.cat;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.enrichissement.cat.config.ConfigurationPriamLocal;
import fr.sacem.priam.batch.enrichissement.cat.config.ConfigurationPriamProd;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriamLocal.class, ConfigurationPriamProd.class);

        if(args.length == 1 && args[0].equals("FR") || args[0].equals("ANF")) {

            String typeCMS = args[0];
            JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
            Job job = (Job) context.getBean("jobEnrichissement");
            Admap admap = (Admap) context.getBean("admap");
            try {
                Map<String, JobParameter> jobParametersMap = new HashMap<>();
                jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
                jobParametersMap.put("input.archives", new JobParameter(admap.getInputFile()));
                jobParametersMap.put("output.archives", new JobParameter(admap.getOutputFile()));
                jobParametersMap.put("typeCMS", new JobParameter(typeCMS));

                JobParameters jobParameters = new JobParameters(jobParametersMap);
                JobExecution execution = jobLauncher.run(job, jobParameters);

                LOGGER.info("Exit Status : " + execution.getStatus());

            } catch (Exception e) {
                LOGGER.error("Error execution", e);
            }

            LOGGER.info("Done");

        } else {
            LOGGER.error("paramètre typeCMS non renseigné dans le script shell FR ou ANF");
            System.exit(1);
        }

    }
}
