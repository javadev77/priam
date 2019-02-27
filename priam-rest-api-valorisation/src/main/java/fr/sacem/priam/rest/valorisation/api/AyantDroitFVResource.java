package fr.sacem.priam.rest.valorisation.api;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/rest/")
@Profile({"dev", "prod","re7"})
public class AyantDroitFVResource {
}
