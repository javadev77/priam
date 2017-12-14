package fr.sacem.priam.rest.cms.api;

import fr.sacem.domain.Admap;
import fr.sacem.priam.model.dao.jpa.cms.TraitementEligibiliteCMSDao;
import fr.sacem.priam.model.domain.cms.TraitementEligibiliteCMS;
import fr.sacem.priam.services.api.LigneProgrammeResource;
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.cms.LigneProgrammeCMSService;
import org.apache.maven.doxia.logging.SystemStreamLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by benmerzoukah on 07/12/2017.
 */
@RestController
@RequestMapping("/app/rest/")
public class LigneProgrammeCMSRessource extends LigneProgrammeResource {

    private static  final Logger LOGGER = LoggerFactory.getLogger(LigneProgrammeCMSRessource.class);

    @Autowired
    @Qualifier("ligneProgrammeCMSService")
    LigneProgrammeCMSService ligneProgrammeCMSService;

    LigneProgrammeService ligneProgrammeService;

    @Autowired
    @Qualifier("jobLauncher")
    JobLauncher jobLauncher;

    @Autowired
    Job jobEligibiliteOctav;

    @Autowired
    Admap admap;

    @Autowired
    TraitementEligibiliteCMSDao traitementEligibiliteCMSDao;

    @Autowired
    public LigneProgrammeCMSRessource(@Qualifier("ligneProgrammeCMSService") LigneProgrammeService ligneProgrammeService) {
        this.ligneProgrammeService = ligneProgrammeService;
    }


    @RequestMapping(value = "ligneProgramme/selection/compteurs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getDurDifProgramme(@RequestParam(value = "numProg") String numProg, @RequestParam(value = "statut") String statut) {
        return ligneProgrammeCMSService.calculerCompteurs(numProg,statut);
    }



    @RequestMapping(value = "programme/eligibilite/{numProg}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean lancerTraitementAffectationCMS(@PathVariable(value = "numProg") String numProg) {
        LOGGER.info("Lancement du Batch Affectation CMS ");



        try {

            Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("input.catalog.octav", new JobParameter(admap.getInputFile()));
            jobParametersMap.put("archives.catalog.octav", new JobParameter(admap.getOutputFile()));

            JobParameters jobParameters = new JobParameters(jobParametersMap);

            JobExecution execution = jobLauncher.run(jobEligibiliteOctav, jobParameters);
            LOGGER.info("Exit Status : " + execution.getStatus());
        } catch (Exception e) {
            LOGGER.error("Error execution", e);
        }

        LOGGER.info("Fin de Traitement ");

        return Boolean.TRUE;

    }

    @RequestMapping(value = "programme/eligibilite/tmt/{numProg}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TraitementEligibiliteCMS getLastFinished(@PathVariable(value = "numProg") String numProg) {
        PageRequest pageRequest = new PageRequest(0, 1);

        Page<TraitementEligibiliteCMS> finishedTmtByNumProg = traitementEligibiliteCMSDao.findFinishedTmtByNumProg(numProg, pageRequest);

        return finishedTmtByNumProg !=null && !finishedTmtByNumProg.getContent().isEmpty() ? finishedTmtByNumProg.getContent().get(0) : null;

    }


    @Override
    public LigneProgrammeService getLigneProgrammeService() {
        return ligneProgrammeService;
    }
}
