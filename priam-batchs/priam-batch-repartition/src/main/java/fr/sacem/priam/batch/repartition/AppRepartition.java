package fr.sacem.priam.batch.repartition;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.repartition.config.ConfigurationPriamLocal;
import fr.sacem.priam.batch.repartition.config.ConfigurationPriamProd;
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
 * Created by fandis on 23/05/2017.
 */
public class AppRepartition {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppRepartition.class);

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriamLocal.class, ConfigurationPriamProd.class);

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        //DataSource dataSource=(DataSource) context.getBean("dataSource");
        Job job = (Job) context.getBean("csvArchiveFlatFileReaderJob");
        Admap admap =(Admap) context.getBean("admap");
        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("input.archives", new JobParameter(admap.getInputFile()));
            jobParametersMap.put("output.archives", new JobParameter(admap.getOutputFile()));
            jobParametersMap.put("pattern.file.name", new JobParameter(admap.getPatternFileName()));

            JobParameters jobParameters = new JobParameters(jobParametersMap);
            JobExecution execution = jobLauncher.run(job, jobParameters);
            System.out.println("Exit Status : " + execution.getStatus());

        } catch (Exception e) {
            LOGGER.error("Execution error ", e);
        }

        System.out.println("Done");

    }
}
