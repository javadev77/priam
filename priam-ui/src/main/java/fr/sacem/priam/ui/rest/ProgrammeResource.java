package fr.sacem.priam.ui.rest;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.TypeRepart;
import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.services.ProgrammeService;
import fr.sacem.priam.ui.rest.dto.ProgrammeCritereRecherche;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by benmerzoukah on 06/06/2017.
 */
@RestController
@RequestMapping("/app/rest/programme")
public class ProgrammeResource {
    private static Logger logger = LoggerFactory.getLogger(ProgrammeResource.class);
  
    @Autowired
    private ProgrammeService programmeService;
  
    @RequestMapping(value = "/search",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProgrammeDto> rechercheProgramme(@RequestBody ProgrammeCritereRecherche input, Pageable pageable) {
        logger.info("input criteria : " + input);
        List<StatutProgramme> status = null;
        if(input.getStatutCode() == null || input.getStatutCode().isEmpty()) {
            status = Arrays.asList(StatutProgramme.values());
        } else {
            status = Lists.transform(input.getStatutCode(), code -> StatutProgramme.valueOf(code));
        }
        
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        criteria.setStatut(status);
    
        String codeFamille = null;
        if(!"ALL".equals(input.getFamille())) {
            codeFamille = input.getFamille();
            criteria.setFamille(codeFamille);
        }
    
        String codeTypeUtil = input.getTypeUtilisation();
        if(codeTypeUtil !=null && !"ALL".equals(codeTypeUtil)) {
            criteria.setTypeUtilisation(codeTypeUtil);
        }
  
        criteria.setNumProg(Strings.emptyToNull(input.getNumProg()));
        criteria.setNom(Strings.emptyToNull(input.getNom()));
        
        String codeTypeRepart = input.getTypeRepart();
        if(codeTypeRepart != null && !"ALL".equals(codeTypeRepart)) {
            criteria.setTypeRepart(TypeRepart.valueOf(codeTypeRepart));
        }
  
        String rionTheorique = input.getRionTheorique();
        if(rionTheorique != null && !"ALL".equals(rionTheorique)) {
            criteria.setRionTheorique(Integer.valueOf(rionTheorique));
        }
  
        String rionPaiement = input.getRionPaiement();
        if(rionPaiement != null && !"ALL".equals(rionPaiement)) {
            criteria.setRionPaiement(Integer.valueOf(rionPaiement));
        }
  
        criteria.setDateCreationDebut(input.getDateCreationDebut());
        criteria.setDateCreationFin(input.getDateCreationFin());
  
        return programmeService.findProgrammeByCriteria(criteria, pageable);
    }
}
