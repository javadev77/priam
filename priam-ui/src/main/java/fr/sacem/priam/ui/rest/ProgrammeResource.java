package fr.sacem.priam.ui.rest;

import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.services.ProgrammeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by benmerzoukah on 06/06/2017.
 */
@RestController
@RequestMapping("/app/rest/programme")
public class ProgrammeResource {

    @Autowired
    private ProgrammeService programmeService;

    @RequestMapping(value = "/search",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProgrammeDto> rechercheProgramme(Pageable pageable) {

        return programmeService.findProgrammeByCriteria(pageable);
    }

}
