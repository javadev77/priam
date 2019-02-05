package fr.sacem.priam.rest.valorisation.api;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.security.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/rest/")
public class ExportFVResource {

    @Autowired
    Job jobExport;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    Admap admap;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportFVResource.class);

    @RequestMapping(value = "programme/export/{numProg}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto exportProgramme(@PathVariable("numProg") String numProg, UserDTO userDTO){
        ProgrammeDto programmeDto = null;
        try {
            programmeDto = programmeViewDao.findByNumProg(numProg);
            launchJobExport(numProg, userDTO);
        }catch (Exception ex) {
            LOGGER.error("Erreur lors de l'export ", ex);
        }
        return programmeDto;
    }


    protected void launchJobExport(String numProg, UserDTO userDTO){
        LOGGER.info("====== Lancement du job Export ======");

        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("priam.export.programme.fv", new JobParameter(admap.getInputFile()));

            jobParametersMap.put("numProg", new JobParameter(numProg));
            jobParametersMap.put("userId", new JobParameter(userDTO.getUserId()));

            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(jobExport, jobParameters);

        } catch (Exception e) {
            LOGGER.error("Error d'exécution du Batch Export", e);
        }

        LOGGER.info("====== Fin de l'export ======");
    }

}
