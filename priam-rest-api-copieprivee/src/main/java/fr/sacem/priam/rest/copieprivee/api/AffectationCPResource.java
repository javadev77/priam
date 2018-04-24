package fr.sacem.priam.rest.copieprivee.api;

import com.google.common.base.Strings;
import fr.sacem.priam.common.TypeLog;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.rest.copieprivee.journal.annotation.LogFichier;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.FichierService;
import fr.sacem.priam.services.ProgrammeService;
import fr.sacem.priam.services.utils.AffectationUtil;
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
public class AffectationCPResource {
    private static Logger LOGGER = LoggerFactory.getLogger(AffectationCPResource.class);

    @Autowired
    private FichierService fichierService;

    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    AffectationUtil affectationUtil;

    @Autowired
    private ProgrammeService programmeService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job jobAffectationCP;

    @RequestMapping(value = "programme/affectation",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @LogFichier(event = TypeLog.AFFECTATION_DESAFFECTATION)
    public ProgrammeDto affecterFichiers (@RequestBody AffectationDto affectationDto, UserDTO currentUser) {

        ProgrammeDto programmeDto = null;
        String numProg=affectationDto.getNumProg();
        List<Fichier> fichiers = affectationUtil.getFichiersAffectes(affectationDto);
        if(!Strings.isNullOrEmpty(numProg)){
            fichierService.majFichiersAffectesAuProgramme(numProg, fichiers, currentUser.getDisplayName());
            programmeDto = programmeViewDao.findByNumProg(numProg);
        }

       // launchJobAffectation(programmeDto, currentUser);


        return programmeDto;
    }

    private void launchJobAffectation(ProgrammeDto programmeDto, UserDTO userDTO) {
        //lancer le job
        LOGGER.info("====== Lancement du job Affectation CP ======");

        try {

            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("numProg", new JobParameter(programmeDto.getNumProg()));
            jobParametersMap.put("userId", new JobParameter(userDTO.getUserId()));


//            jobParametersMap.put("listNomFichier", new JobParameter(listNomFichiersAvantAffectation));

            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(jobAffectationCP, jobParameters);


        } catch (Exception e) {
            LOGGER.error("Error d'exécution du Batch Affectation CP", e);
        }

        LOGGER.info("====== Fin de Traitement ======");
    }


    @RequestMapping(value = "programme/toutDesaffecter",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @LogFichier(event = TypeLog.ALL_DESAFFECTATION)
    public ProgrammeDto deaffecterFichiers (@RequestBody String numProg, UserDTO userDTO){
        LOGGER.info("deaffecterFichiers() ==> numProg=" + numProg);
        ProgrammeDto programmeDto = null;
        if(!Strings.isNullOrEmpty(numProg)){
            programmeService.toutDeaffecter(numProg, userDTO.getDisplayName());
            programmeDto = programmeViewDao.findByNumProg(numProg);
        }

        return programmeDto;
    }



}
