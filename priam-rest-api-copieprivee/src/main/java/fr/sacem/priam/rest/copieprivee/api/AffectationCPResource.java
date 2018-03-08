package fr.sacem.priam.rest.copieprivee.api;

import com.google.common.base.Strings;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.rest.copieprivee.journal.annotation.LogFichier;
import fr.sacem.priam.rest.copieprivee.journal.annotation.TypeLog;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.FichierService;
import fr.sacem.priam.services.ProgrammeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private ProgrammeService programmeService;

    @RequestMapping(value = "programme/affectation",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @LogFichier(event = TypeLog.AFFECTATION)
    public ProgrammeDto affecterFichiers (@RequestBody AffectationDto affectationDto, UserDTO currentUser) {

        ProgrammeDto programmeDto = null;
        String numProg=affectationDto.getNumProg();
        List<Fichier> fichiers = affectationDto.getFichiers();

        if(!Strings.isNullOrEmpty(numProg)){
            fichierService.majFichiersAffectesAuProgramme(numProg, fichiers, currentUser.getDisplayName());
            programmeDto = programmeViewDao.findByNumProg(numProg);
        }

        return programmeDto;
    }


    @RequestMapping(value = "programme/toutDesaffecter",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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
