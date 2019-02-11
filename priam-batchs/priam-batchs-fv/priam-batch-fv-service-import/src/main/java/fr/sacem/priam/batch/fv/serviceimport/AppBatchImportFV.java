package fr.sacem.priam.batch.fv.serviceimport;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
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
public class AppBatchImportFV {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppBatchImportFV.class);

    public static void main(String[] args) {
        LOGGER.info("Lancement du Batch Import FV ");

        ApplicationContext context = new AnnotationConfigApplicationContext();

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job jobAffectationFv = (Job) context.getBean("jobImportFv");

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
