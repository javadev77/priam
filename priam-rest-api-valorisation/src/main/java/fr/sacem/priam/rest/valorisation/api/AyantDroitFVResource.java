package fr.sacem.priam.rest.valorisation.api;

import fr.sacem.priam.batch.common.dao.AyantDroitDao;
import fr.sacem.priam.model.dao.jpa.fv.AyantDroitProgrammeFVDao;
import fr.sacem.priam.model.domain.criteria.AyantDroitCriteria;
import fr.sacem.priam.model.domain.dto.AyantDroitDto;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.services.dto.AyantDroitProgrammeCritereRecherche;
import fr.sacem.priam.services.fv.AyantDroitFVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/app/rest/")
@Profile({"dev", "prod","re7"})
public class AyantDroitFVResource {

    @Autowired
    @Qualifier("ayantDroitFVService")
    private AyantDroitFVService ayantDroitFVService;


    @RequestMapping(value = "ayantDroit/search",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Page<? extends AyantDroitDto> findAyantDroitByCritere(@RequestBody AyantDroitProgrammeCritereRecherche ayantDroit, Pageable pageable){

        AyantDroitCriteria criteria = new AyantDroitCriteria();

        criteria.setNumProg(ayantDroit.getNumProg());
        criteria.setIde12(ayantDroit.getIde12());
        criteria.setCoad(ayantDroit.getCoad());

        if(ayantDroit.getTitre() != null && !ayantDroit.getTitre().isEmpty()) {
            criteria.setTitre(ayantDroit.getTitre());
        }

        if(ayantDroit.getParticipant() != null && !ayantDroit.getParticipant().isEmpty())
            criteria.setParticipant(ayantDroit.getParticipant());


        return ayantDroitFVService.findAyantDroitByCriteria(criteria, pageable);

    }

    @RequestMapping(value = "ayantDroit/coad",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<KeyValueDto> getListCoadByNumProg(@RequestParam(value = "q") String query, @RequestParam(value = "programme") String numProg) {

        Long coad ;

        try {
            coad = Long.parseLong(query);
        }catch (NumberFormatException ex) {
            return new ArrayList<>();
        }

        return ayantDroitFVService.getListCoadByNumProg(coad, numProg);
    }

    @RequestMapping(value = "ayantDroit/participant",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<KeyValueDto> getTitresByProgramme(@RequestParam(value = "q") String query, @RequestParam(value = "programme") String programme) {
        return ayantDroitFVService.getParticipantByNumProg(query, programme);
    }

}
