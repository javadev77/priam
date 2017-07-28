package fr.sacem.priam.ui.rest;

import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.domain.LigneProgramme;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.Ide12Dto;
import fr.sacem.priam.services.LigneProgrammeService;
import fr.sacem.priam.ui.rest.dto.LigneProgrammeCritereRecherche;
import fr.sacem.priam.ui.rest.dto.SelectionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fandis on 18/07/2017.
 */
@RestController
@RequestMapping("/app/rest/")
public class LigneProgrammeResource {

  @Autowired
  private LigneProgrammeService ligneProgrammeService;


  @Autowired
  private LigneProgrammeDao ligneProgrammeDao;

  private static Logger logger = LoggerFactory.getLogger(LigneProgrammeResource.class);

  @RequestMapping(value = "ligneProgramme/numprog",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  Page<SelectionDto> findLigneProgrammeByProgrammeId(@RequestBody String numProg, Pageable pageable){

    Page<LigneProgramme> ligneProgrammes = ligneProgrammeDao.findLigneProgrammeByProgrammeId(numProg,pageable);
    Page<SelectionDto> dtoPage =ligneProgrammes.map(convert());
    logger.info("Nbr de lignes programmes pour le programme :"+numProg+" est de :"+ ligneProgrammes.getTotalElements());

    return dtoPage;
  }

  @RequestMapping(value = "ligneProgramme/search",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  Page<SelectionDto> findLigneProgrammeByCritere(@RequestBody LigneProgrammeCritereRecherche ligneProgramme, Pageable pageable){

    LigneProgrammeCriteria criteria = new LigneProgrammeCriteria();

    criteria.setNumProg(ligneProgramme.getNumProg());
    criteria.setIde12(ligneProgramme.getIde12());

    Page<LigneProgramme> ligneProgrammes = ligneProgrammeService.findLigneProgrammeByCriteria(criteria, pageable);

    Page<SelectionDto> dtoPage =ligneProgrammes.map(convert());
    logger.info("Nbr de lignes programmes pour le programme :"+ligneProgramme.getNumProg()+" est de :"+ ligneProgrammes.getTotalElements());

    return dtoPage;
  }

  private Converter<LigneProgramme, SelectionDto> convert() {
    return source -> {
      SelectionDto selectionDto=new  SelectionDto();
      selectionDto.setRoleParticipant1(source.getRoleParticipant1());
      selectionDto.setIde12(source.getIde12());
      selectionDto.setAjout(source.getAjout());
      selectionDto.setDuree(source.getDurDif());
      selectionDto.setNomParticipant1(source.getNomParticipant1());
      selectionDto.setUtilisateur(source.getUtilisateur());
      selectionDto.setQuantite("");
      selectionDto.setTitreOeuvre(source.getTitreOeuvre());
      return selectionDto;
    };
  }

  @RequestMapping(value = "ligneProgramme/ide12",
    method = RequestMethod.GET,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Ide12Dto> getListIDE12ByProgramme(@RequestParam(value = "q") String query, @RequestParam(value = "programme") String programme) {

    Long ide12 ;

    try {
      ide12 = Long.parseLong(query);
    }catch (NumberFormatException ex) {
      return new ArrayList<>();
    }

    return ligneProgrammeService.getListIDE12ByProgramme(ide12, programme);
  }

}
