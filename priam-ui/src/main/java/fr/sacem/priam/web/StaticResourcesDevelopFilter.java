package fr.sacem.priam.web;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by benmerzoukah on 19/04/2017.
 */
public class StaticResourcesDevelopFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(StaticResourcesDevelopFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
      String contextPath = ((HttpServletRequest) servletRequest).getContextPath();
      String requestURI = httpRequest.getRequestURI();

      requestURI = StringUtils.substringAfter(requestURI, contextPath);

      if (StringUtils.equals("/", requestURI)) {
        requestURI = "/index.html";
      }

      logger.info("requestURI = " + requestURI);

      servletRequest.getRequestDispatcher(requestURI).forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
