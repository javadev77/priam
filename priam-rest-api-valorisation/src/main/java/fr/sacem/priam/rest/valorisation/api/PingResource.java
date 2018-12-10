package fr.sacem.priam.rest.valorisation.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by benmerzoukah on 22/11/2018.
 */
@RestController
@RequestMapping("/app/rest/ping")
public class PingResource {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity ping() {
        return ResponseEntity.ok("Hello Fonds de Valo !!");
    }
}
