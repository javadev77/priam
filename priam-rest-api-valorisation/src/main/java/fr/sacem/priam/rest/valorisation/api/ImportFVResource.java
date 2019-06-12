package fr.sacem.priam.rest.valorisation.api;

import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.dao.jpa.fv.ImportProgrammeFVDao;
import fr.sacem.priam.model.domain.StatutImportProgramme;
import fr.sacem.priam.model.domain.fv.ImportProgrammeFV;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.sacem.priam.security.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/app/rest/")
public class ImportFVResource {
    private static Logger logger = LoggerFactory.getLogger(ImportFVResource.class);

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    private ImportProgrammeFVDao importProgrammeFVDao;

    @Autowired
    Job jobServiceImport;

    @Autowired
    JobLauncher jobLauncher;



    @PostMapping("programme/import")
    public ResponseEntity<String> importProgramme(@RequestParam("file") MultipartFile file, @RequestParam("numProg") String numProg, UserDTO userDTO) throws IOException {
        if (file == null) {
            throw new RuntimeException("You must select the a file for uploading");
        }

        InputStream inputStream = file.getInputStream();
        String originalName = file.getOriginalFilename();
        String name = file.getName();
        String contentType = file.getContentType();
        long size = file.getSize();

        logger.info("inputStream: " + inputStream);
        logger.info("originalName: " + originalName);
        logger.info("name: " + name);
        logger.info("contentType: " + contentType);
        logger.info("size: " + size);

        // Do processing with uploaded file data in Service layer

        // delete the previous file
        importProgrammeFVDao.deleteLogsByNumProg(numProg);
        importProgrammeFVDao.deleteByNumProg(numProg);

        ImportProgrammeFV importProgrammeFV = new ImportProgrammeFV();

        importProgrammeFV.setDateCreation(new Date());
        importProgrammeFV.setFilename(file.getName());
        importProgrammeFV.setContent(file.getBytes());
        importProgrammeFV.setProgramme(programmeDao.findOne(numProg));
        importProgrammeFV.setStatutImportProgramme(StatutImportProgramme.EN_COURS);

        importProgrammeFVDao.save(importProgrammeFV);

        launchJobImport(numProg, userDTO);


        return new ResponseEntity<>(originalName, HttpStatus.OK);
    }


    protected void launchJobImport(String numProg, UserDTO userDTO){
        logger.info("====== Lancement du job Import ======");

        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
            jobParametersMap.put("numProg", new JobParameter(numProg));
            jobParametersMap.put("userId", new JobParameter(userDTO.getUserId()));

            JobParameters jobParameters = new JobParameters(jobParametersMap);

            jobLauncher.run(jobServiceImport, jobParameters);

        } catch (Exception e) {
            logger.error("Error d'exécution du Batch Export", e);
        }

        logger.info("====== Fin de l'export ======");
    }



    @RequestMapping(value = "programme/import/{numProg}/log",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<String> getLogs(@PathVariable(name = "numProg") String numProg) {
        return importProgrammeFVDao.getLogs(numProg);
    }

}
