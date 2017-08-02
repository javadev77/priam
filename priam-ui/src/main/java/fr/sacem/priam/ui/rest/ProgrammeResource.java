package fr.sacem.priam.ui.rest;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.TypeRepart;
import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.services.FichierService;
import fr.sacem.priam.services.ProgrammeService;
import fr.sacem.priam.ui.rest.dto.ProgrammeCritereRecherche;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by benmerzoukah on 06/06/2017.
 */
@RestController
@RequestMapping("/app/rest/")
public class ProgrammeResource {
    private static Logger logger = LoggerFactory.getLogger(ProgrammeResource.class);

    @Autowired
    private ProgrammeService programmeService;
    @Autowired
    private FichierService fichierService;
    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @RequestMapping(value = "programme/search",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProgrammeDto> rechercheProgramme(@RequestBody ProgrammeCritereRecherche input, Pageable pageable) {
        logger.info("input criteria : " + input);
        ProgrammeCriteria criteria = new ProgrammeCriteria();

        if(input.getStatutCode() == null || input.getStatutCode().isEmpty()) {
            criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        } else {
            criteria.setStatut(Lists.transform(input.getStatutCode(), StatutProgramme::valueOf));
        }

        String codeFamille = input.getFamille();
        if (codeFamille != null && !"ALL".equals(codeFamille)) {
          criteria.setFamille(codeFamille);
        }

        String codeTypeUtil = input.getTypeUtilisation();
        if (codeTypeUtil != null && !"ALL".equals(codeTypeUtil)) {
          criteria.setTypeUtilisation(codeTypeUtil);
        }

        criteria.setNumProg(Strings.emptyToNull(input.getNumProg()));
        criteria.setNom(Strings.emptyToNull(input.getNom()));

        String codeTypeRepart = input.getTypeRepart();
        if (codeTypeRepart != null && !"ALL".equals(codeTypeRepart)) {
          criteria.setTypeRepart(TypeRepart.valueOf(codeTypeRepart));
        }

        String rionTheorique = input.getRionTheorique();
        if (rionTheorique != null && !"ALL".equals(rionTheorique)) {
          criteria.setRionTheorique(Integer.valueOf(rionTheorique));
        }

        String rionPaiement = input.getRionPaiement();
        if (rionPaiement != null && !"ALL".equals(rionPaiement)) {
          criteria.setRionPaiement(Integer.valueOf(rionPaiement));
        }
        criteria.setDateCreationDebut(input.getDateCreationDebut());
        criteria.setDateCreationFin(input.getDateCreationFin());

        return programmeService.findProgrammeByCriteria(criteria, pageable);
    }


     @RequestMapping(value = "programme/nom/{nom}",
                     method = RequestMethod.GET,
                     produces = MediaType.APPLICATION_JSON_VALUE)
     public Boolean getProgrammeByNom(@PathVariable("nom") String nom) {
        List<Programme> programmes = programmeService.serachProgrammeByNom(nom);

        return programmes != null && !programmes.isEmpty();
     }

     @RequestMapping(value = "programme/",
                     method = RequestMethod.POST,
                     consumes = MediaType.APPLICATION_JSON_VALUE,
                     produces = MediaType.APPLICATION_JSON_VALUE)
     public Programme save (@RequestBody ProgrammeDto programmeDto){
        return programmeService.addProgramme(programmeDto);
     }

     @RequestMapping(value = "programme/numProg/{numProg}",
                     method = RequestMethod.GET,
                     produces = MediaType.APPLICATION_JSON_VALUE)
     public ProgrammeDto findByNumProg(@PathVariable("numProg") String numProg) {

          return  programmeViewDao.findByNumProg(numProg);
     }

     @RequestMapping(value = "programme/",
                     method = RequestMethod.PUT,
                     consumes = MediaType.APPLICATION_JSON_VALUE,
                     produces = MediaType.APPLICATION_JSON_VALUE)
     public Programme updateProgramme(@RequestBody ProgrammeDto programmeDto){

          return programmeService.updateProgramme(programmeDto);
     }

     @RequestMapping(value = "programme/abandon",
                      method = RequestMethod.PUT,
                      consumes = MediaType.APPLICATION_JSON_VALUE,
                      produces = MediaType.APPLICATION_JSON_VALUE)
     public Programme abandonnerProgramme(@RequestBody ProgrammeDto programmeDto) {
          return programmeService.abandonnerProgramme(programmeDto);
     }

    @RequestMapping(value = "programme/affectation",
                    method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto affecterFichiers (@RequestBody AffectationDto affectationDto) {

        ProgrammeDto programmeDto = null;
        String numProg=affectationDto.getNumProg();
        List<Fichier> fichiers = affectationDto.getFichiers();

        if(!Strings.isNullOrEmpty(numProg)){
            fichierService.majFichiersAffectesAuProgramme(numProg, fichiers);
            programmeDto = programmeViewDao.findByNumProg(numProg);
        }

        return programmeDto;
    }


    @RequestMapping(value = "programme/numprog/autocomplete",
                  method = RequestMethod.GET,
                  produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllNumProgForAutocmplete() {
        return programmeViewDao.findAllNumProgByCriteria();
    }


    @RequestMapping(value = "programme/nomprog/autocomplete",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllNomProgForAutocmplete() {
        return programmeService.findAllNomProgByCriteria();
    }

    @RequestMapping(value = "programme/toutDesaffecter",
                    method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgrammeDto deaffecterFichiers (@RequestBody String numProg){
        logger.info("deaffecterFichiers() ==> numProg=" + numProg);
        ProgrammeDto programmeDto = null;
        if(!Strings.isNullOrEmpty(numProg)){
          programmeService.toutDeaffecter(numProg);
          programmeDto = programmeViewDao.findByNumProg(numProg);
        }

        return programmeDto;
    }

}
