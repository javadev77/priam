package fr.sacem.priam.rest.valorisation.api;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;

import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.domain.dto.DesaffectationDto;
import fr.sacem.priam.model.domain.dto.FileDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.utils.AffectationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by embouazzar on 10/12/2018.
 */
@RestController
@RequestMapping("/app/rest/")
public class AffectationFVResource {
    private static Logger LOGGER = LoggerFactory.getLogger(AffectationFVResource.class);

    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    AffectationUtil affectationUtil;

    @Autowired
    private FichierDao fichierDao;

    @RequestMapping(value = "programme/affectation",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto affecterFichiers (@RequestBody AffectationDto affectationDto, UserDTO currentUser) {

        String numProg = affectationDto.getNumProg();

        ProgrammeDto programmeDto =  programmeViewDao.findByNumProg(numProg);

        return programmeDto;
    }

    @RequestMapping(value = "programme/toutDesaffecter",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto desaffecterFichiers (@RequestBody DesaffectationDto desaffectationDto, UserDTO userDTO){


        return new ProgrammeDto();
    }

    @RequestMapping(value = "allFichiersAffectesByNumprog/{numProg}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileDto> findFichiersAffectesByIdProgramme(@PathVariable("numProg") String numProg) {
        return fichierDao.findFichiersAffecteByIdProgramme(numProg, Status.AFFECTE);
    }

}
