package fr.sacem.priam.batch.fv.octav.info.oeuvre.req;

import fr.sacem.priam.batch.common.dao.FichierJdbcDao;
import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.common.domain.Fichier;
import fr.sacem.priam.batch.fv.octav.info.oeuvre.req.config.ConfigurationPriamLocal;
import fr.sacem.priam.batch.fv.octav.info.oeuvre.req.config.ConfigurationPriamProd;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.util.CollectionUtils;

@SpringBootApplication
public class App {

    private static  final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationPriamLocal.class, ConfigurationPriamProd.class);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jobGenerationREQ");
        Admap admap =(Admap) context.getBean("admap");

        FichierJdbcDao fichierJdbcDao = (FichierJdbcDao) context.getBean("fichierJdbcDao");
        List<Fichier> fichiers =
                fichierJdbcDao.getFichiersInfoOeuvreEligible();
        if(CollectionUtils.isEmpty(fichiers)){
            LOGGER.info("Il n'y a pas de fichier à traiter");
        }
        fichiers.forEach(f -> {
            try {
                Map<String, JobParameter> jobParametersMap = new HashMap<>();
                jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
                jobParametersMap.put("outputCsvFile", new JobParameter(admap.getOutputFile()));
                jobParametersMap.put("idFichier", new JobParameter(f.getId()));
                JobParameters jobParameters = new JobParameters(jobParametersMap);

                JobExecution execution = jobLauncher.run(job, jobParameters);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.info(String.format("Exit Status : %1$s", execution.getStatus()));
                }
            } catch (Exception e ) {
                LOGGER.error(String.format("Exception %s", e));
            }
        });

    }
}
