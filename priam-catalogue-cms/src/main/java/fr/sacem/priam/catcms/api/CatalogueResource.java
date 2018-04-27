package fr.sacem.priam.catcms.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/rest/")
public class CatalogueResource {

    @RequestMapping(value = "catalogue",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String test(){
        return "Hello world";
    }
}
