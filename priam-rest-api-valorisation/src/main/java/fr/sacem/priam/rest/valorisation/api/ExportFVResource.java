package fr.sacem.priam.rest.valorisation.api;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.common.exception.TechnicalException;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.dao.jpa.fv.ExportProgrammeFVDao;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutEligibilite;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.security.model.UserDTO;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/rest/")
@Profile({"dev", "prod","re7"})
public class ExportFVResource {

    @Autowired
    Job jobExportFV;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    ProgrammeDao programmeDao;

    @Autowired
    private ExportProgrammeFVDao exportProgrammeFVJpaDao;

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
            jobParametersMap.put("priam.export.programme.fv", new JobParameter(admap.getOutputFile()));

            jobParametersMap.put("numProg", new JobParameter(numProg));
            jobParametersMap.put("userId", new JobParameter(userDTO.getUserId()));

            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(jobExportFV, jobParameters);

        } catch (Exception e) {
            LOGGER.error("Error d'exécution du Batch Export", e);
        }

        LOGGER.info("====== Fin de l'export ======");
    }

    @RequestMapping(value = "programme/downloadExport",
            method = RequestMethod.POST)
    public void downloadExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String numProg = request.getParameter("numProg");

        String path = exportProgrammeFVJpaDao.getFilepathByNumProg(numProg);
        String nameExport = FilenameUtils.getName(path);
        File file = new File(path);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + nameExport);
        if(!file.exists())
            throw new TechnicalException(String.format("Le fichier %s n'existe pas", nameExport));
        try(FileInputStream in = new FileInputStream(file); OutputStream output = response.getOutputStream()) {
            IOUtils.copy(in, output);
        } catch (Exception e) {
            LOGGER.error("Erreur de telechargement de l'export", e);
            throw e;
        }
    }

    @RequestMapping(value = "programme/statut/{numProg}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto majStatutProgramme(@PathVariable("numProg") String numProg){
        ProgrammeDto programmeDto = null;
        TransactionStatus ts = transactionManager.getTransaction(new DefaultTransactionDefinition());
        Programme programme = programmeDao.findByNumProg(numProg);
        programme.setStatut(StatutProgramme.EN_COURS);
        programmeDao.save(programme);
        transactionManager.commit(ts);
        programmeDto = programmeViewDao.findByNumProg(numProg);

        return programmeDto;
    }

}
