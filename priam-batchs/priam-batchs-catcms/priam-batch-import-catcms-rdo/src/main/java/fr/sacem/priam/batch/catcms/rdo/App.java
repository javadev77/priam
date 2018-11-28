package fr.sacem.priam.batch.catcms.rdo;

import fr.sacem.priam.batch.catcms.rdo.config.ConfigurationPriamProd;
import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.catcms.rdo.config.ConfigurationPriamLocal;
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

public class App {

    private static  final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriamLocal.class, ConfigurationPriamProd.class);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jobImportCatalogueCMSRDO");
        Admap admap =(Admap) context.getBean("admap");

        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("input.archives", new JobParameter(admap.getInputFile()));
            jobParametersMap.put("output.archives", new JobParameter(admap.getOutputFile()));
            JobParameters jobParameters = new JobParameters(jobParametersMap);
            JobExecution execution = jobLauncher.run(job, jobParameters);

        } catch (Exception e) {
            LOGGER.error("Error execution", e);
        }

        LOGGER.info("Done");

    }
}
