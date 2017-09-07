package fr.sacem.priam.ui.config;

import fr.sacem.fwk.config.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

//import static fr.sacem.fwk.config.Environment.getParameter;

/**
 * Created by benmerzoukah on 19/04/2017.
 */
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    private static final String STATIC_INDEX_HTML_RESOURCES = "/index.html";
    private static final Logger LOGGER = LoggerFactory.getLogger(MvcConfig.class);

    @Autowired
    private ResourceLoader resourceLoader;

    //@Value( "${webapp.mode}" )
    private String webappMode;

    @Value("${vuejs.server.url}")
    private String vuejsDevServerUrl;


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        Resource resource = this.resourceLoader.getResource(STATIC_INDEX_HTML_RESOURCES);
        if (resource.exists()) {
            LOGGER.info("Adding welcome page: {}", STATIC_INDEX_HTML_RESOURCES);
            // Use forward: prefix so that no view resolution is done
            registry.addViewController("/").setViewName("forward:/index.html");
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**")
                  .addResourceLocations("/");
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //TODO Ajouter un check en fonction du mode de l'application

      webappMode = Environment.getParameter("webapp.mode");

        if("dev".equalsIgnoreCase(webappMode)) {
            registry.addMapping("/app/rest/**")
                    .allowedMethods("PUT", "DELETE", "GET", "POST")
                    .allowedOrigins(vuejsDevServerUrl);
        }

    }
  
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
       argumentResolvers.add(new UserDTOHandlerMethodArgumentResolver());
    }
}
