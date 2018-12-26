package fr.sacem.priam.batch.fv.octav.req;

import com.google.common.collect.Lists;
import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.common.domain.Fichier;
import fr.sacem.priam.batch.fv.octav.req.config.ConfigurationPriamLocal;
import fr.sacem.priam.batch.fv.octav.req.dao.FichierJdbcDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by benmerzoukah on 16/05/2018.
 *
 * To be completed for Spring Batch developpement
 */
@SpringBootApplication
public class App {

    private static  final Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static final String TO_SRV_OCTAV_CTNU = "TO_SRV_OCTAV_CTNU";
    public static final String CHARGEMENT_OK = "CHARGEMENT_OK";

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriamLocal.class);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jobGenerationREQ");
        Admap admap = (Admap) context.getBean("admap");
        FichierJdbcDao fichierJdbcDao = (FichierJdbcDao)context.getBean("fichierJdbcDao");

        List<Fichier> fichiers = fichierJdbcDao.getFichiersFvEligibleOctavCtnu(CHARGEMENT_OK,
                                                                               Lists.newArrayList("FD03", "FD04", "FD09", "FD10", "FD11"));
        fichiers.forEach(f -> {
            try {
                Map<String, JobParameter> jobParametersMap = new HashMap<>();
                jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
                jobParametersMap.put("outputCsvFile", new JobParameter(admap.getOutputFile()));
                jobParametersMap.put("idFichier", new JobParameter(f.getId()));
                JobParameters jobParameters = new JobParameters(jobParametersMap);

                jobLauncher.run(job, jobParameters);

            } catch (Exception e) {
                LOGGER.error("Error execution", e);
            }


        });

        LOGGER.info("Done");



    }
}
