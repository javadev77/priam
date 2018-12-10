package fr.sacem.priam.batch.fv.affectation;

import fr.sacem.priam.batch.fv.affectation.config.BatchConfigLocal;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by benmerzoukah on 30/11/2017.
 */
public class AppBatchAffectationFV {

    private static  final Logger LOGGER = LoggerFactory.getLogger(AppBatchAffectationFV.class);

    public static void main(String[] args) {
        LOGGER.info("Lancement du Batch Affectation FV ");

        ApplicationContext context = new AnnotationConfigApplicationContext(BatchConfigLocal.class);


        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job jobAffectationFv = (Job) context.getBean("jobAffectationFv");


        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("numProg", new JobParameter("180043"));
            JobParameters jobParameters = new JobParameters(jobParametersMap);

            JobExecution execution = jobLauncher.run(jobAffectationFv, jobParameters);
            LOGGER.info("Exit Status : " + execution.getStatus());
        } catch (Exception e) {
            LOGGER.error("Error execution", e);
        }

        LOGGER.info("Fin de Traitement ");
    }
}
