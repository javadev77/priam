package fr.sacem.priam.batch.felix;

import fr.sacem.priam.batch.felix.config.BatchFelixConfigLocal;
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
 * Created by benmerzoukah on 27/07/2018.
 */
public class AppBatch {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AppBatch.class);;

    public static void main(String[] args) {
        LOGGER.info("Lancement du Batch Affectation CP ");

        ApplicationContext context = new AnnotationConfigApplicationContext(BatchFelixConfigLocal.class);


        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job jobAffectationCP = (Job) context.getBean("jobFelixRepart");

        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("numProg", new JobParameter("180014"));
            JobParameters jobParameters = new JobParameters(jobParametersMap);

            JobExecution execution = jobLauncher.run(jobAffectationCP, jobParameters);
            LOGGER.info("Exit Status : " + execution.getStatus());
        } catch (Exception e) {
            LOGGER.error("Error execution", e);
        }

        LOGGER.info("Fin de Traitement ");
    }
}
