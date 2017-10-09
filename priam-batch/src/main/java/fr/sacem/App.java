package fr.sacem;

import fr.sacem.priam.common.constants.EnvConstants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fandis on 23/05/2017.
 */
public class App {
    @Resource
    private static Environment env;

    public static void main(String[] args) {

        String[] springConfig =
                {
                        "/config/job-configuration.xml",
                };

        ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        //DataSource dataSource=(DataSource) context.getBean("dataSource");
        Job job = (Job) context.getBean("archiveFlatFileReaderJob");
        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            String inputDirectory = String.valueOf(EnvConstants.PENEF_ZIP_IN);
            String outputDirectory = String.valueOf(EnvConstants.PENEF_ZIP_ARCHIVES);
            String resources = String.valueOf(EnvConstants.BATCH_CONFIG_PROPERTIES);
            System.out.println("inputDirectory "+ inputDirectory);
            System.out.println("inputDirectory "+ inputDirectory);
            System.out.println("resources "+ resources);
            jobParametersMap.put("input.archives", new JobParameter(inputDirectory));
            jobParametersMap.put("output.archives", new JobParameter(outputDirectory));
            JobParameters jobParameters = new JobParameters(jobParametersMap);
            JobExecution execution = jobLauncher.run(job, jobParameters);
            System.out.println("Exit Status : " + execution.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Done");

    }
}
