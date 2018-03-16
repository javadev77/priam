package fr.sacem.priam.rest.common.api;

import fr.sacem.priam.common.constants.EnvConstants;
import fr.sacem.priam.common.exception.TechnicalException;
import fr.sacem.priam.model.dao.jpa.FichierFelixDao;
import fr.sacem.priam.model.domain.FichierFelix;
import fr.sacem.priam.model.domain.StatutFichierFelix;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.FelixDataCMSService;
import fr.sacem.priam.services.FelixDataService;
import fr.sacem.priam.services.FelixDataServiceAbstract;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

/**
 * Created by benmerzoukah on 21/08/2017.
 */
@RestController
@RequestMapping("/app/rest/repartition")
public class RepartitionResource {

    private static Logger logger = LoggerFactory.getLogger(RepartitionResource.class);



   @Autowired
   private FelixDataService felixDataCPService;


    @Autowired
    private FelixDataCMSService felixDataCMSService;


    @Autowired
    private FichierFelixDao fichierFelixDao;

    @Autowired
    @Qualifier(value = "configAdmap")
    Map<String, String> configAdmap;


    @RequestMapping(value = "validateFelixData/{numProg}",
                  method = RequestMethod.GET,
                  produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto runAsyncCreateFichierFelix(@PathVariable("numProg") String numProg) throws IOException {
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

        getFelixDataService(numProg).runAsyncCreateFichierFelix(numProg);

        return new ProgrammeDto();
    }


    @RequestMapping(value = "fichierfelix/{numProg}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
    public FichierFelix getFichierFelix(@PathVariable("numProg") String numProg) {
        FichierFelix fichierFelix = fichierFelixDao.findByNumprog(numProg);

        return fichierFelix;
    }


    @RequestMapping(value = "/downloadFichierFelixError",
                   method = RequestMethod.POST)
    public void generateFelixDataWithErrors(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = request.getParameter("filename");
        String numProg = request.getParameter("numProg");


        generateFelixCsvData(response, numProg, filename);
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
        if(fichierFelix != null ) {
            if(fichierFelix.getLogs() == null || fichierFelix.getLogs().isEmpty()) {
                generateFelixCsvData(response, numProg, fichierFelix.getNomFichier());
            }
        }
    }

    @RequestMapping(value = "/generateFelixData",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public void generateFelixData(@RequestBody ProgrammeDto programme, UserDTO userDto) throws TechnicalException {
        String numProg = programme.getNumProg();
        FichierFelix ff = fichierFelixDao.findByNumprog(numProg);
        if(ff != null) {
            ff.setStatut(StatutFichierFelix.EN_COURS_ENVOI);
            fichierFelixDao.save(ff);
            fichierFelixDao.flush();

        }
        getFelixDataService(numProg).asyncSendFichierFelix(programme, userDto.getDisplayName());

    }
    private FelixDataServiceAbstract getFelixDataService(String numProg) {
        if (felixDataCPService.getFamilleUtil(numProg).equals("UC")) {
            return felixDataCMSService;
        } else {
            return felixDataCPService;
        }
    }
}
