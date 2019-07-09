package fr.sacem.priam.rest.valorisation.api;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.dto.FileDto;
import fr.sacem.priam.model.domain.fv.EnrichissementLog;
import fr.sacem.priam.services.FichierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/rest/enrichissement/fichier")
public class FichierEnrichissementResource {

    private static Logger logger = LoggerFactory.getLogger(FichierEnrichissementResource.class);

    @Autowired
    FichierService fichierService;

    @Autowired
    FichierDao fichierDao;

    @RequestMapping(value = "/{idFichier}/log",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<EnrichissementLog> getEnrichissementLog(@PathVariable(name = "idFichier") Long idFichier, Pageable pageable) {
        return fichierService.getEnrichissementLog(idFichier, pageable);
    }

    @RequestMapping(value = "/relancer",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FileDto relancerEnrichissement(@RequestBody FileDto fileDtoBody) {
        Long idFichier = fileDtoBody.getId();
        fichierService.relancerEnrichissement(idFichier);
        FileDto fileDto = fichierDao.findById(idFichier);
        return fileDto;
    }

}
