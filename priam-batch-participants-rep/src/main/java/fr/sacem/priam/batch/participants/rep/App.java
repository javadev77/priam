package fr.sacem.priam.batch.participants.rep;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.participants.rep.config.ConfigurationPriamLocal;
import fr.sacem.priam.batch.participants.rep.config.ConfigurationPriamProd;

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
 * Created by benmerzoukah on 16/05/2018.
 *
 * To be completed for Spring Batch developpement
 */
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private static final String PATTERN_TYPE_CMS_FR = "FRA";
    private static final String PATTERN_TYPE_CMS_ANF = "ANF";

    public static void main(String[] args) {

        if(args.length > 0) {

            String typeCMS = args[0];
            ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriamLocal.class, ConfigurationPriamProd.class);

            JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
            Job job = (Job) context.getBean("jobParticipantRep");
            Admap admap = (Admap) context.getBean("admap");

            try {
                Map<String, JobParameter> jobParametersMap = new HashMap<>();
                jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
                jobParametersMap.put("input.archives", new JobParameter(admap.getInputFile()));
                jobParametersMap.put("output.archives", new JobParameter(admap.getOutputFile()));
                jobParametersMap.put("typeCMS", new JobParameter(typeCMS));

                StringBuilder pattern = new StringBuilder(admap.getPatternFileName());
                JobParameter patternFile;
                if(typeCMS.equals(PATTERN_TYPE_CMS_FR)){
                    patternFile = new JobParameter(pattern.insert(23, PATTERN_TYPE_CMS_FR).toString());
                } else {
                    patternFile = new JobParameter(pattern.insert(23, PATTERN_TYPE_CMS_ANF).toString());
                }

                jobParametersMap.put("pattern.file.name", patternFile);

                JobParameters jobParameters = new JobParameters(jobParametersMap);
                JobExecution execution = jobLauncher.run(job, jobParameters);

                LOGGER.info("Exit Status : " + execution.getStatus());

            } catch (Exception e) {
                LOGGER.error("Error execution", e);
            }

            LOGGER.info("Done");
        } else {
            LOGGER.error("paramètre pattern typeCMS non renseigné dans le script shell FRA ou ANF");
            System.exit(1);
        }
    }
}
