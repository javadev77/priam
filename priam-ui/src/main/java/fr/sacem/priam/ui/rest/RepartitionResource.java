package fr.sacem.priam.ui.rest;

import fr.sacem.priam.model.dao.jpa.ProgrammeDao;
import fr.sacem.priam.model.domain.dto.FichierFelixError;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.services.FelixDataService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by benmerzoukah on 21/08/2017.
 */
@RestController
@RequestMapping("/app/rest/repartition")
public class RepartitionResource {
  
    private static Logger logger = LoggerFactory.getLogger(RepartitionResource.class);
    
    @Autowired
    private FelixDataService felixDataService;
    
    @Autowired
    private ProgrammeDao programmeDao;
    
    @RequestMapping(value = "validateFelixData/{numProg}",
                  method = RequestMethod.GET,
                  produces = MediaType.APPLICATION_JSON_VALUE)
    public FichierFelixError validateFelixData(@PathVariable("numProg") String numProg) throws IOException {
        felixDataService.generateEtValidateDonneesRepartition(numProg);
        FichierFelixError fichierFelixWithErrors = felixDataService.createFichierFelixWithErrors(numProg);
        
        return fichierFelixWithErrors;
    }
  
    @RequestMapping(value = "/downloadFichierFelixError",
                   method = RequestMethod.POST)
    public void generateFelixDataWithErrors(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tmpFilename = request.getParameter("tmpFilename");
        String filename = request.getParameter("filename");
        String numProg = request.getParameter("numProg");
      
        //Programme programme = programmeDao.findOne(numProg);
  
        generateFelixCsvData(response, tmpFilename, filename);
    }
  
    private void generateFelixCsvData(HttpServletResponse response, String tmpFilename, String filename) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + tmpFilename);
        
        try(InputStream in = new FileInputStream(file); OutputStream output = response.getOutputStream()) {
            response.setContentLength((int)file.length());
            IOUtils.copy(in, output);
        } catch (Exception e) {
           logger.error("Erreur de telechargement de fichier", e);
        } finally {
        //Delete tmp file
        /*if (file.exists()) {
          FileUtils.forceDelete(file);
        }*/
      
        }
    }
  
    @RequestMapping(value = "/downloadFichierFelix",
                    method = RequestMethod.POST)
    public void downloadFichierFelixRepartitionABlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String numProg = request.getParameter("numProg");
        
        FichierFelixError fichierFelixError = felixDataService.createFichierFelixWithErrors(numProg);
        if(fichierFelixError.getErrors() == null || fichierFelixError.getErrors().isEmpty()) {
            generateFelixCsvData(response, fichierFelixError.getTmpFilename(), fichierFelixError.getFilename());
        }
    }
  
    @RequestMapping(value = "/generateFelixData",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public void generateFelixData(@RequestBody ProgrammeDto programme) {
        FichierFelixError fichierFelixError = null;
        try {
            fichierFelixError = felixDataService.createFichierFelixWithErrors(programme.getNumProg());
            if(fichierFelixError.getErrors() == null || fichierFelixError.getErrors().isEmpty()) {
  
              File file = new File(System.getProperty("java.io.tmpdir") + File.separator + fichierFelixError.getTmpFilename());
              file.renameTo(new File(System.getProperty("java.io.tmpdir") + File.separator + fichierFelixError.getFilename()));
              //FileUtils.moveFileToDirectory(file, );
              
            }
    
        } catch (IOException e) {
          logger.error("Erreur lors de la generation du fichier Felix", e);
        }
    }
}
