package fr.sacem;

import fr.sacem.priam.common.constants.EnvConstants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
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
@PropertySource(value = "classpath:/config/application.properties")
public class AppRepartition {
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
        Job job = (Job) context.getBean("csvArchiveFlatFileReaderJob");
        Properties envProperties = (Properties) context.getBean("envProperties");
        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("input.felix", new JobParameter(String.valueOf(EnvConstants.FELIX_ACQT_INPUT_DIR)));
            jobParametersMap.put("output.felix", new JobParameter(String.valueOf(EnvConstants.FELIX_ACQT_ARCHIVES_DIR)));
            JobParameters jobParameters = new JobParameters(jobParametersMap);
            JobExecution execution = jobLauncher.run(job, jobParameters);
            System.out.println("Exit Status : " + execution.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Done");

    }
}
