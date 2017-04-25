package fr.sacem.priam.web;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by benmerzoukah on 19/04/2017.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer {

  private Logger logger = LoggerFactory.getLogger(WebConfigurer.class);

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
    if (!isProductionStaticResourceAvailable(servletContext) && logger.isWarnEnabled()) {
      throw new ServletException("Production static resource is not available !");
    }
  }


  private boolean isProductionStaticResourceAvailable(ServletContext servletContext) {
    String prodIndex;
    try {
      if (logger.isDebugEnabled()) {
        logger.debug("Checking production static resources availability...");
      }
      prodIndex = IOUtils.toString(servletContext.getResourceAsStream("/index.html"));
    } catch (IOException e) {
      if (logger.isWarnEnabled()) {
        logger.warn("Can not read production index.html", e);
      }
      prodIndex = null;
    }
    return prodIndex != null;
  }
}
