package fr.sacem.priam.rest.cms.api;

import fr.sacem.domain.Admap;
import fr.sacem.priam.model.dao.jpa.FichierCatalogueOctavDao;
import fr.sacem.priam.model.dao.jpa.SareftjLibutilDao;
import fr.sacem.priam.model.dao.jpa.cms.TraitementEligibiliteCMSDao;
import fr.sacem.priam.model.domain.CatalogueOctav;
import fr.sacem.priam.model.domain.FichierCatalogueOctav;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import fr.sacem.priam.model.domain.cms.TraitementEligibiliteCMS;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.api.LigneProgrammeResource;
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.cms.LigneProgrammeCMSService;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by benmerzoukah on 07/12/2017.
 */
@RestController
@RequestMapping("/app/rest/")
public class LigneProgrammeCMSRessource extends LigneProgrammeResource {

    private static  final Logger LOGGER = LoggerFactory.getLogger(LigneProgrammeCMSRessource.class);

    @Autowired
    @Qualifier("ligneProgrammeCMSService")
    LigneProgrammeCMSService ligneProgrammeCMSService;

    LigneProgrammeService ligneProgrammeService;

    @Autowired
    @Qualifier("jobLauncher")
    JobLauncher jobLauncher;

    @Autowired
    Job jobEligibiliteOctav;

    @Autowired
    Admap admap;

    @Autowired
    TraitementEligibiliteCMSDao traitementEligibiliteCMSDao;

    @Autowired
    private SareftjLibutilDao sareftjLibutilDao;

    @Autowired
    private FichierCatalogueOctavDao fichierCatalogueOctavDao;

    @Autowired
    public LigneProgrammeCMSRessource(@Qualifier("ligneProgrammeCMSService") LigneProgrammeService ligneProgrammeService) {
        this.ligneProgrammeService = ligneProgrammeService;
    }


    @RequestMapping(value = "ligneProgramme/selection/compteurs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getDurDifProgramme(@RequestParam(value = "numProg") String numProg, @RequestParam(value = "statut") String statut) {
        return ligneProgrammeCMSService.calculerCompteurs(numProg,statut);
    }


    @RequestMapping(value = "programme/eligibilite/tmt/{numProg}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TraitementEligibiliteCMS getLastFinished(@PathVariable(value = "numProg") String numProg) {
        PageRequest pageRequest = new PageRequest(0, 1);

        Page<TraitementEligibiliteCMS> finishedTmtByNumProg = traitementEligibiliteCMSDao.findFinishedTmtByNumProg(numProg, pageRequest);

        return finishedTmtByNumProg !=null && !finishedTmtByNumProg.getContent().isEmpty() ? finishedTmtByNumProg.getContent().get(0) : null;

    }


    @Override
    public LigneProgrammeService getLigneProgrammeService() {
        return ligneProgrammeService;
    }


    @RequestMapping(value = "ligneProgramme/selection/ajoutOeuvre",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public SelectionDto ajouterOeuvreManuel(@RequestBody LigneProgrammeCMS input, UserDTO userDTO) {
        input.setUtilisateur(userDTO.getUserId());

        ligneProgrammeCMSService.ajouterOeuvreManuel(input);

        return new SelectionDto();
    }

    @RequestMapping(value = "ligneProgramme/selection/isEligible",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean isEligible(@RequestBody CatalogueOctav catalogueOctav) {
        return ligneProgrammeCMSService.isEligible(catalogueOctav.getIde12(), catalogueOctav.getTypeCMS());
    }

    /*@RequestMapping(value = "ligneProgramme/selection/catalogueOctav",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FichierCatalogueOctav getLastCatalogueOctav() {
        PageRequest pageRequest = new PageRequest(0, 1);


        Page<FichierCatalogueOctav> lastFichierOctav = fichierCatalogueOctavDao.findLastFichierOctav(pageRequest);


        return lastFichierOctav !=null && !lastFichierOctav.getContent().isEmpty() ? lastFichierOctav.getContent().get(0) : null;

    }*/

    @Override
    public List<String> modifierSelectionTemporaire(@RequestBody ValdierSelectionProgrammeInput input) {
        super.modifierSelectionTemporaire(input);
        String numProg = input.getNumProg();
        if (!input.getSelected().isEmpty()) {
            ligneProgrammeCMSService.modifierPointsTemporaire(numProg, input.getSelected(), Boolean.TRUE);
        }
        if (!input.getUnselected().isEmpty()) {
            ligneProgrammeCMSService.modifierPointsTemporaire(numProg, input.getUnselected(), Boolean.FALSE);
        }

        return new ArrayList<>();
    }

}
