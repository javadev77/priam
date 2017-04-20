package fr.sacem.priam.web;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

/**
 * Created by benmerzoukah on 19/04/2017.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer {

  @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
        //initStaticResourcesDevelopFilter(servletContext, disps);
    }

    private void initStaticResourcesDevelopFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {

      FilterRegistration.Dynamic staticResourcesProductionFilter =
        servletContext.addFilter("staticResourcesDevelopFilter",
            new StaticResourcesDevelopFilter());

      staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true, "/");
      staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true, "/index.html");
      staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true, "/static/*");
      staticResourcesProductionFilter.setAsyncSupported(true);
    }
}
