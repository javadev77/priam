package fr.sacem.priam.batch.penef.cms.fra;

import fr.sacem.priam.batch.penef.cms.fra.config.ConfigurationPriamLocal;
import fr.sacem.priam.batch.penef.cms.fra.config.ConfigurationPriamProd;
import fr.sacem.domain.Admap;
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
 * Created by fandis on 23/05/2017.
 */
public class App {

    private static  final Logger LOGGER = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) {
        

        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriamLocal.class,ConfigurationPriamProd.class);

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("archiveFlatFileReaderJob");
        Admap admap =(Admap) context.getBean("admap");
        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            System.out.println("inputDirectory "+ admap.getInputFile());
            System.out.println("inputDirectory "+ admap.getOutputFile());
            jobParametersMap.put("input.archives", new JobParameter(admap.getInputFile()));
            jobParametersMap.put("output.archives", new JobParameter(admap.getOutputFile()));
            jobParametersMap.put("idFichier", new JobParameter(0L));
            JobParameters jobParameters = new JobParameters(jobParametersMap);
            JobExecution execution = jobLauncher.run(job, jobParameters);
            System.out.println("Exit Status : " + execution.getStatus());

        } catch (Exception e) {
            LOGGER.error("Error execution", e);
        }
    
        LOGGER.info("Done");

    }
}
