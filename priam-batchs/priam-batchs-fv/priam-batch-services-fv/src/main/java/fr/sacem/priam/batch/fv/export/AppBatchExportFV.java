package fr.sacem.priam.batch.fv.export;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.fv.config.BatchConfigLocal;
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

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AppBatchExportFV {

    private static  final Logger LOGGER = LoggerFactory.getLogger(AppBatchExportFV.class);

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(BatchConfigLocal.class);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jobExportFV");
        Admap admap =(Admap) context.getBean("admap");

        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("outputCsvFile", new JobParameter(admap.getOutputFile()));
            jobParametersMap.put("numProg", new JobParameter(String.valueOf(190006L)));
            JobParameters jobParameters = new JobParameters(jobParametersMap);
            JobExecution execution = jobLauncher.run(job, jobParameters);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.info(String.format("Exit Status : %1$s", execution.getStatus()));
            }
        }  catch (Exception e ) {
            LOGGER.error(String.format("Exception %s", e));
        }
    }

}
