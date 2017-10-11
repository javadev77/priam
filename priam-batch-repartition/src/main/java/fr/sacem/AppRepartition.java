package fr.sacem;

import fr.sacem.config.ConfigurationPriam;
import fr.sacem.domain.Admap;
import fr.sacem.priam.common.constants.EnvConstants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by fandis on 23/05/2017.
 */
public class AppRepartition {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriam.class);

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        //DataSource dataSource=(DataSource) context.getBean("dataSource");
        Job job = (Job) context.getBean("csvArchiveFlatFileReaderJob");
        Admap admap =(Admap) context.getBean("admap");
        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("input.felix", new JobParameter(admap.getInputFile()));
            jobParametersMap.put("output.felix", new JobParameter(admap.getOutputFile()));
            JobParameters jobParameters = new JobParameters(jobParametersMap);
            JobExecution execution = jobLauncher.run(job, jobParameters);
            System.out.println("Exit Status : " + execution.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Done");

    }
}
