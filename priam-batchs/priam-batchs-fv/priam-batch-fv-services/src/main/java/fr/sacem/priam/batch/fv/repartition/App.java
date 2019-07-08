package fr.sacem.priam.batch.fv.repartition;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.fv.config.BatchServicesConfigLocal;
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
 * Created by benmerzoukah on 16/05/2018.
 *
 * To be completed for Spring Batch developpement
 */
@SpringBootApplication
public class App {

    private static  final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(BatchServicesConfigLocal.class);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jobRepartitionFVOeuvre");
        Admap admap =(Admap) context.getBean("admap");

        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("output.felix", new JobParameter(admap.getOutputFile()));
            jobParametersMap.put("numProg", new JobParameter("190014"));
            JobParameters jobParameters = new JobParameters(jobParametersMap);
            JobExecution execution = jobLauncher.run(job, jobParameters);
            System.out.println("Exit Status : " + execution.getStatus());

        } catch (Exception e) {
            LOGGER.error("Execution error ", e);
        }

        System.out.println("Done");

    }
}
