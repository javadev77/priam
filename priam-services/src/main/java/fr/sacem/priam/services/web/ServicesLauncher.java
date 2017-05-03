package fr.sacem.priam.services.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by benmerzoukah on 03/05/2017.
 */
@SpringBootApplication
public class ServicesLauncher extends SpringBootServletInitializer {
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServicesLauncher.class);
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServicesLauncher.class, args);
    }
}
