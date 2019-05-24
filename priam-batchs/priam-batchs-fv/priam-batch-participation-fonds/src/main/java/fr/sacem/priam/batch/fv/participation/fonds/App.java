package fr.sacem.priam.batch.fv.participation.fonds;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.fv.participation.fonds.config.BatchConfigLocal;
import fr.sacem.priam.batch.fv.participation.fonds.config.BatchConfigProd;
import org.slf4j.Logger;
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

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(BatchConfigLocal.class, BatchConfigProd.class);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jobInitialisationReferentiel");
        Admap admap =(Admap) context.getBean("admap");

        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("input.archives", new JobParameter(admap.getInputFile()));
            jobParametersMap.put("output.archives", new JobParameter(admap.getOutputFile()));
            jobParametersMap.put("pattern.file.name", new JobParameter(admap.getPatternFileName()));

            JobParameters jobParameters = new JobParameters(jobParametersMap);
            JobExecution execution = jobLauncher.run(job, jobParameters);
            LOGGER.info("Exit Status : " + execution.getStatus());
            LOGGER.info("Done");
        } catch (Exception e) {
            LOGGER.error(String.format("Exception %s", e));
        }

    }
}
