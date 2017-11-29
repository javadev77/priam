package fr.sacem.priam.security.config;

import fr.sacem.fwk.config.Environment;
import fr.sacem.fwk.config.FrmwkEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

/**
 * Created by embouazzar on 23/08/2017.*/


public class AjaxLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(AjaxLogoutSuccessHandler.class);

  @PostConstruct
  public void configureLogoutRedirection(){
    String defaultTargetUrl = Environment.getParameter(FrmwkEnv.SSO_URL_LOGOUT);
    if(hasText(defaultTargetUrl) &&
      UrlUtils.isValidRedirectUrl(defaultTargetUrl)){
      setDefaultTargetUrl(defaultTargetUrl);
      setAlwaysUseDefaultTargetUrl(true);
    }else if(LOGGER.isWarnEnabled()){
      LOGGER.warn("No sso url logout configured !");
    }
  }

  @Override
  public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
    throws IOException, ServletException {
    this.handle(httpServletRequest, httpServletResponse, authentication);
  }
}
