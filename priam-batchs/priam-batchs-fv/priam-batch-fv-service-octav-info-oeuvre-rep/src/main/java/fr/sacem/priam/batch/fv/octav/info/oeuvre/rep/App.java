package fr.sacem.priam.batch.fv.octav.info.oeuvre.rep;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.fv.octav.info.oeuvre.rep.config.ConfigurationPriamLocal;
import fr.sacem.priam.batch.fv.octav.info.oeuvre.rep.config.ConfigurationPriamProd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class App {

    private static  final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriamLocal.class, ConfigurationPriamProd.class);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jobInfoOeuvreREP");
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
