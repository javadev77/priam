package fr.sacem.priam.rest.cms.api;

import com.google.common.base.Strings;
import fr.sacem.domain.Admap;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.dao.jpa.cms.TraitementEligibiliteCMSDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.FichierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by benmerzoukah on 16/11/2017.
 */
@RestController
@RequestMapping("/app/rest/")
public class AffectationCMSResource {
    private static Logger LOGGER = LoggerFactory.getLogger(AffectationCMSResource.class);

    @Autowired
    private FichierService fichierService;

    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    private TraitementEligibiliteCMSDao traitementEligibiliteCMSDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job jobEligibiliteOctav;

    @Autowired
    Admap admap;

    @RequestMapping(value = "programme/affectation",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto affecterFichiers (@RequestBody AffectationDto affectationDto, UserDTO currentUser) {

        ProgrammeDto programmeDto = null;
        String numProg = affectationDto.getNumProg();
        List<Fichier> fichiers = affectationDto.getFichiers();

        if(!Strings.isNullOrEmpty(numProg)){
            fichierService.majFichiersAffectesAuProgramme(numProg, fichiers, currentUser.getDisplayName());
            programmeDto = programmeViewDao.findByNumProg(numProg);
        }

        //lancer le job
        LOGGER.info("Lancement du Batch Affectation CMS ");


        try {

            Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("input.catalog.octav", new JobParameter(admap.getInputFile()));
            jobParametersMap.put("archives.catalog.octav", new JobParameter(admap.getOutputFile()));
            jobParametersMap.put("numProg", new JobParameter(numProg));

            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(jobEligibiliteOctav, jobParameters);

        } catch (Exception e) {
            LOGGER.error("Error execution", e);
        }

        LOGGER.info("Fin de Traitement ");

        return programmeDto;
    }



}
