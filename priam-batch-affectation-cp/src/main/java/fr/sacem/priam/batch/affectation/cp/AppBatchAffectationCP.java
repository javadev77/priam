package fr.sacem.priam.batch.affectation.cp;

import fr.sacem.domain.Admap;
import fr.sacem.priam.batch.affectation.cp.config.BatchConfigLocal;
import fr.sacem.priam.batch.affectation.cp.config.BatchConfigProd;
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
public class AppBatchAffectationCP {

    private static  final Logger LOGGER = LoggerFactory.getLogger(AppBatchAffectationCP.class);

    public static void main(String[] args) {
        LOGGER.info("Lancement du Batch Affectation CP ");

        ApplicationContext context = new AnnotationConfigApplicationContext(BatchConfigLocal.class, BatchConfigProd.class);


        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job jobAffectationCP = (Job) context.getBean("jobAffectationCP");

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
