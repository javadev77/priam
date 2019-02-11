package fr.sacem.priam.rest.common.api;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.TypeRepart;
import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.rest.common.api.dto.ProgrammeCritereRecherche;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.ProgrammeService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    private ProgrammeViewDao programmeViewDao;

    @RequestMapping(value = "programme/search",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProgrammeDto> rechercheProgramme(@RequestBody ProgrammeCritereRecherche input, UserDTO currentUser, Pageable pageable) {
        logger.info("input criteria : " + input);
        ProgrammeCriteria criteria = new ProgrammeCriteria();

        if(input.getStatutCode() == null || input.getStatutCode().isEmpty()) {
            criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        } else {
            criteria.setStatut(Lists.transform(input.getStatutCode(), StatutProgramme::valueOf));
        }

        String codeFamille = input.getFamille();
        if (codeFamille != null && !"ALL".equals(codeFamille)) {
          criteria.setSareftrFamiltyputil(Lists.newArrayList(codeFamille));
        } else {
            criteria.setSareftrFamiltyputil(currentUser.authorizedFamilles());
        }

        String codeTypeUtil = input.getTypeUtilisation();
        if (codeTypeUtil != null && !"ALL".equals(codeTypeUtil)) {
          criteria.setTypeUtilisation(Lists.newArrayList(codeTypeUtil));
        } else {
            criteria.setTypeUtilisation(currentUser.authorizedTypeUtilisations());
        }

        criteria.setNumProg(Strings.emptyToNull(input.getNumProg()));
        criteria.setNom(Strings.emptyToNull(input.getNom()));

        String codeTypeRepart = input.getTypeRepart();
        if (codeTypeRepart != null && !"ALL".equals(codeTypeRepart)) {
          criteria.setTypeRepart(TypeRepart.valueOf(codeTypeRepart));
        }

        String rionTheorique = input.getRionTheorique();
        if (rionTheorique != null && !"ALL".equals(rionTheorique)) {
          criteria.setSareftrRionTheorique(Integer.valueOf(rionTheorique));
        }

        String rionPaiement = input.getRionPaiement();
        if (rionPaiement != null && !"ALL".equals(rionPaiement)) {
          criteria.setSareftrRionPaiement(Integer.valueOf(rionPaiement));
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
     public Programme save (@RequestBody ProgrammeDto programmeDto, UserDTO userDTO){
        programmeDto.setUsercre(userDTO.getDisplayName());
        return programmeService.addProgramme(programmeDto, userDTO);
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
     public Programme updateProgramme(@RequestBody ProgrammeDto programmeDto, UserDTO userDTO){
         /*programmeDto.setUsermaj(userDTO.getDisplayName());*/
         return programmeService.updateProgramme(programmeDto, userDTO);
     }

     @RequestMapping(value = "programme/abandon",
                      method = RequestMethod.PUT,
                      consumes = MediaType.APPLICATION_JSON_VALUE,
                      produces = MediaType.APPLICATION_JSON_VALUE)
     public Programme abandonnerProgramme(@RequestBody ProgrammeDto programmeDto, UserDTO userDTO) {
         /*programmeDto.setUsermaj(userDTO.getDisplayName());*/
          return programmeService.abandonnerProgramme(programmeDto, userDTO);
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


    @PostMapping("programme/import")
    public ResponseEntity<String> importProgramme(@RequestParam("file") MultipartFile file) throws IOException {

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

        return new ResponseEntity<>(originalName, HttpStatus.OK);
    }




}
