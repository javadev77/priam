package fr.sacem.priam.fv.octav.info.oeuvre.req;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.common.fv.util.EtapeEnrichissementEnum;
import fr.sacem.priam.fv.octav.info.oeuvre.req.config.ConfigurationPriamLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;

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
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriamLocal.class);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jobGenerationREQ");
        Admap admap =(Admap) context.getBean("admap");
        try {
        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
        jobParametersMap.put("outputCsvFile", new JobParameter(admap.getOutputFile()));
        jobParametersMap.put("idFichier", new JobParameter(677L));
        JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(job, jobParameters);
        } catch (Exception e ) {
            LOGGER.error(String.format("Exception %s", e));
        }
    }
}
