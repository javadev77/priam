package fr.sacem.priam.rest.common.api;

import fr.sacem.priam.common.constants.EnvConstants;
import fr.sacem.priam.common.exception.TechnicalException;
import fr.sacem.priam.model.dao.jpa.FichierFelixDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.FichierFelix;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutFichierFelix;
import fr.sacem.priam.security.model.UserDTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by benmerzoukah on 21/08/2017.
 */
@RestController
@RequestMapping("/app/rest/repartition")
public class RepartitionResource {

    private static Logger logger = LoggerFactory.getLogger(RepartitionResource.class);

    @Autowired
    private FichierFelixDao fichierFelixDao;

    @Autowired
    ProgrammeDao programmeDao;

    @Autowired
    @Qualifier(value = "configAdmap")
    Map<String, String> configAdmap;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job jobFelixRepart;




    @RequestMapping(value = "/generateFichierFelix",
                    method = RequestMethod.GET)
    public ResponseEntity generateFichierFelix(@RequestParam(value = "numProg") String numProg,
                                               @RequestParam(value = "modeRepartition") String modeRepartition,
                                               @RequestParam(value = "typeRepart") String typeRepart,
                                               UserDTO userDTO) {

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

        Programme prog = programmeDao.findByNumProg(numProg);

        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
        jobParametersMap.put("priam.felix.preprep.dir", new JobParameter(configAdmap.get(EnvConstants.FELIX_PREPREP_DIR.property())));
        jobParametersMap.put("numProg", new JobParameter(numProg));
        jobParametersMap.put("modeRepartition", new JobParameter(modeRepartition));
        jobParametersMap.put("typeRepart", new JobParameter(typeRepart));
        jobParametersMap.put("famille", new JobParameter(prog.getFamille().getCode()));
        jobParametersMap.put("userId", new JobParameter(userDTO.getUserId()));
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        launchJobFelixRepart(jobParameters);

        return ResponseEntity.ok().build();
    }

    protected void launchJobFelixRepart(final JobParameters jobParameters) {


        try {
            jobLauncher.run(jobFelixRepart, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobParametersInvalidException | JobInstanceAlreadyCompleteException e) {
            throw new TechnicalException("Erreur Technique lors du lancement du Job !! ", e);
        }
    }

    @RequestMapping(value = "fichierfelix/{numProg}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
    public FichierFelix getFichierFelix(@PathVariable("numProg") String numProg) {
        return fichierFelixDao.findByNumprog(numProg);
    }


   protected void generateFelixCsvData(HttpServletResponse response, String numProg, String filename) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);


        FichierFelix ff = fichierFelixDao.findByNumprog(numProg);
        String preprepDir = configAdmap.get(EnvConstants.FELIX_PREPREP_DIR.property());
        File file = new File(preprepDir + File.separator + ff.getNomFichier());
        if(!file.exists())
            throw new TechnicalException(String.format("Le fichier %s n'existe pas", ff.getNomFichier()));

        try(FileInputStream in = new FileInputStream(file); OutputStream output = response.getOutputStream()) {
            IOUtils.copy(in, output);
        } catch (Exception e) {
           logger.error("Erreur de telechargement de fichier", e);
           throw e;
        }
    }

    @RequestMapping(value = "/downloadFichierFelix",
                    method = RequestMethod.POST)
    public void downloadFichierFelixRepartitionABlanc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String numProg = request.getParameter("numProg");

        FichierFelix fichierFelix = fichierFelixDao.findByNumprog(numProg);
        if (fichierFelix == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
            generateFelixCsvData(response, numProg, fichierFelix.getNomFichier());
    }

}
