package fr.sacem.priam.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by benmerzoukah on 19/04/2017.
 */
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

  private static final String STATIC_INDEX_HTML_RESOURCES = "/index.html";

  @Autowired
  private ResourceLoader resourceLoader;

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    Resource resource = this.resourceLoader.getResource(STATIC_INDEX_HTML_RESOURCES);
    if (resource.exists()) {
      ///LOGGER.info("Adding welcome page: {}", STATIC_INDEX_HTML_RESOURCES);
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
    if (!registry.hasMappingForPattern("/static/**")) {
      registry.addResourceHandler("/static/**")
        .addResourceLocations("/static/");
    }
  }
}
