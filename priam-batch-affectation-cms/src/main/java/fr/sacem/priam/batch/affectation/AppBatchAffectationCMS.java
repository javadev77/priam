package fr.sacem.priam.batch.affectation;

import fr.sacem.domain.Admap;
import fr.sacem.priam.batch.affectation.config.BatchConfig;
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
 * Created by benmerzoukah on 30/11/2017.
 */
public class AppBatchAffectationCMS {

    private static  final Logger LOGGER = LoggerFactory.getLogger(AppBatchAffectationCMS.class);

    public static void main(String[] args) {
        LOGGER.info("Lancement du Batch Affectation CMS ");

        ApplicationContext context = new AnnotationConfigApplicationContext(BatchConfig.class);

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job jobEligibiliteOctav = (Job) context.getBean("jobEligibiliteOctav");
        Admap admap =(Admap) context.getBean("admap");
        try {

            Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("input.catalog.octav", new JobParameter(admap.getInputFile()));
            jobParametersMap.put("archives.catalog.octav", new JobParameter(admap.getOutputFile()));
            jobParametersMap.put("numProg", new JobParameter("170040"));
            JobParameters jobParameters = new JobParameters(jobParametersMap);

            JobExecution execution = jobLauncher.run(jobEligibiliteOctav, jobParameters);
            LOGGER.info("Exit Status : " + execution.getStatus());
        } catch (Exception e) {
            LOGGER.error("Error execution", e);
        }

        LOGGER.info("Fin de Traitement ");
    }
}
