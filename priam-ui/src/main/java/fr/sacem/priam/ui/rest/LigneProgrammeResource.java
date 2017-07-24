package fr.sacem.priam.ui.rest;

import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.domain.LigneProgramme;
import fr.sacem.priam.ui.rest.dto.SelectionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fandis on 18/07/2017.
 */
@RestController
@RequestMapping("/app/rest/")
public class LigneProgrammeResource {
  @Autowired
  private LigneProgrammeDao ligneProgrammeDao;
  private static Logger logger = LoggerFactory.getLogger(LigneProgrammeResource.class);

  @RequestMapping(value = "ligneProgramme/numprog",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  Page<SelectionDto> findLigneProgrammeByProgrammeId(@RequestBody String numProg, Pageable pageable){
    Page<LigneProgramme> ligneProgrammes = ligneProgrammeDao.findLigneProgrammeByProgrammeId(numProg,pageable);
    Page<SelectionDto> dtoPage =ligneProgrammes.map(new Converter<LigneProgramme, SelectionDto>() {
      @Override
      public SelectionDto convert(LigneProgramme source) {
        SelectionDto selectionDto=new  SelectionDto();
        selectionDto.setRole("role1");
        selectionDto.setIde12(source.getIde12());
        selectionDto.setAjout("automatique");
        selectionDto.setDuree(100l);
        selectionDto.setParticipant("participant");
        selectionDto.setQuantite("11");
        selectionDto.setTitre("titre");
        return selectionDto;
      }
    });
    logger.info("Nbr de lignes programmes pour le programme :"+numProg+" est de :"+ ligneProgrammes.getTotalElements());

    return dtoPage;
  }
}
