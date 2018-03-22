package fr.sacem.priam.rest.common.api;


import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.dto.JournalDto;
import fr.sacem.priam.services.JournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * Created by monfleurm on 15/03/2018.
 */
@RestController
@RequestMapping("/app/rest/")
public class JournalResource {
    private static Logger logger = LoggerFactory.getLogger(ProgrammeResource.class);

    @Autowired
    private JournalService journalService;

    @RequestMapping(value = "journal/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Journal> rechercheEvenements(Pageable pageable) {
        return journalService.findAllEvents(pageable);
    }

    @RequestMapping(value = "journal/searchEvent",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Journal> rechercheEvenementsByNumProg(@RequestBody String numProg, Pageable pageable){
        return journalService.findJournalByNumProg(numProg, pageable);
    }

    /*@RequestMapping(value = "programme/numProg/{numProg}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Journal> findByNumProg(@PathVariable("numProg") String numProg, Pageable pageable) {

        return  journalService.findByNumProg(numProg, pageable);
    }*/
}
