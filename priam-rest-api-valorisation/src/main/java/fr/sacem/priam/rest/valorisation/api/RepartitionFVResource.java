package fr.sacem.priam.rest.valorisation.api;

import fr.sacem.priam.common.constants.EnvConstants;
import fr.sacem.priam.model.dao.jpa.FichierFelixDao;
import fr.sacem.priam.model.domain.FichierFelix;
import fr.sacem.priam.model.domain.StatutFichierFelix;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/rest/repartition")
public class RepartitionFVResource {

    @Autowired
    Job jobRepartitionFVOeuvre;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    private FichierFelixDao fichierFelixDao;

    /*@Autowired
    Admap admap;*/

    @Autowired
    @Qualifier(value = "configAdmap")
    Map<String, String> configAdmap;

    private static Logger LOGGER = LoggerFactory.getLogger(RepartitionFVResource.class);

    @RequestMapping(value = "/generateFichierFelixFV",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto generateFichierFelix(@RequestParam(value = "numProg") String numProg, @RequestParam(value = "modeRepartition") String modeRepartition) throws IOException {
        /*String numProg = request.getParameter("numProg");*/
        FichierFelix ff = fichierFelixDao.findByNumprog(numProg);
        if(ff != null) {
            fichierFelixDao.delete(ff);
            fichierFelixDao.flush();
        }

        ff = new FichierFelix();
        ff.setDateCreation(new Date());
        ff.setStatut(StatutFichierFelix.EN_COURS);
        ff.setNumProg(numProg);
        fichierFelixDao.save(ff);
        fichierFelixDao.flush();

        launchJobRepartitionFV(numProg, modeRepartition);

        return new ProgrammeDto();
    }

    protected void launchJobRepartitionFV(String numProg, String modeRepartition){
        LOGGER.info("====== Lancement du job RepartitionFV ======");
        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("priam.felix.preprep.dir", new JobParameter(configAdmap.get(EnvConstants.FELIX_PREPREP_DIR.property())));
            jobParametersMap.put("numProg", new JobParameter(numProg));
            jobParametersMap.put("modeRepartition", new JobParameter(modeRepartition));
            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(jobRepartitionFVOeuvre, jobParameters);


        } catch (Exception e) {
            LOGGER.error("Error d'exécution du Batch RepartitionFV", e);
        }

        LOGGER.info("====== Fin du job RepartitionFV ======");

    }

}
