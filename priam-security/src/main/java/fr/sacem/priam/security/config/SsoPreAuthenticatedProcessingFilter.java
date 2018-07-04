package fr.sacem.priam.security.config;

import fr.sacem.fwk.config.Environment;
import fr.sacem.fwk.config.FrmwkEnv;
import fr.sacem.fwk.security.SecurityManager;
import fr.sacem.fwk.security.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.Assert;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by embouazzar on 23/08/2017.
 */

public class SsoPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter{

  public static final String REQUEST_COOKIE_SSO_USER = Environment.getParameter("sso.openam.cookie.name");
  private static final String N_A = "guest";

  private static final Logger LOGGER = LoggerFactory.getLogger(SsoPreAuthenticatedProcessingFilter.class);

  private final SecurityManager securityManager;
  private final String priamVersion;

  public SsoPreAuthenticatedProcessingFilter(SecurityManager securityManager, AuthenticationManager authenticationManager) {
    this.securityManager = securityManager;
    this.priamVersion = Environment.getParameter(FrmwkEnv.PROJECT_VERSION);
    setAuthenticationManager(authenticationManager);

    //// Forcer la vérification du changement de valeur du cookie SSO
    // AbstractPreAuthenticatedProcessingFilter#checkForPrincipalChanges default value: false
    setCheckForPrincipalChanges(true);

    //// S'assurer d'un comportement prédictible sur l'invalidation de session
    // AbstractPreAuthenticatedProcessingFilter#invalidateSessionOnPrincipalChange default value: true
    setInvalidateSessionOnPrincipalChange(true);
  }

  @Override
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    Assert.notNull(securityManager, "An SecurityManager must be set");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
    if(LOGGER.isDebugEnabled()){
      LOGGER.debug("Pre-Authorizing: " + ((HttpServletRequest) servletRequest).getRequestURI());
    }


    Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

    // NV 20160104 - Ajouter des traces pour détecter les changements de session SSO
    if(LOGGER.isDebugEnabled()){
      String currentSsoId = currentUser != null ? currentUser.getName() : null;
      String cookieSsoId = getPreAuthenticatedPrincipal((HttpServletRequest) servletRequest);

      if (currentSsoId == null || !currentSsoId.equals(cookieSsoId)) {
        LOGGER.debug("SSO ID has changed to " + cookieSsoId + " and new TokenProfile will be requested");
      }
    }

    // Pré-positionner le profil actuel Spring vers le Framework
    if (currentUser != null) {
      setCurrentUserProfile(currentUser);
      if(servletResponse instanceof HttpServletResponse){
        ((HttpServletResponse) servletResponse).setHeader("X-PRIAM-VER", priamVersion);
      }
    }

    try {
      super.doFilter(servletRequest, servletResponse, chain);
    } finally {
      cleanCurrentUserProfile();
    }
  }

 /*
  * Forcer le nettoyage du profil utilisateur (dans la variable threadlocal du framework Sacem).
  */

  private void cleanCurrentUserProfile() {
    SecurityManager.setCurrentUser(null);
    MDC.remove("userId");
  }

/**
   * Definir le profil de l'utilisateur courrant (dans la variable threadlocal du framework Sacem).
   *
   * @param authResult Authentication result.
   */

  private void setCurrentUserProfile(Authentication authResult) {
    if(authResult instanceof SsoAuthenticationToken){
      SsoAuthenticationToken ssoAuth = (SsoAuthenticationToken) authResult;
      UserProfile userProfile = ssoAuth.getUserProfile();

      // NV 20160104 - Ajouter des traces pour détecter les changements de profils
      if(LOGGER.isDebugEnabled()){
        String currentSsoId = ssoAuth.getName();
        String userId = userProfile.getUserId();
        LOGGER.debug("setCurrentUserProfile: {UserId=" + userId + ", SSO=" + currentSsoId + "}");
      }

      SecurityManager.setCurrentUser(userProfile);
      MDC.put("userId", userProfile.getUserId());
    }
  }


/**
  * For SACEM SSO, returns the sso cookie value
  */

  @Override
  protected String getPreAuthenticatedPrincipal(HttpServletRequest httpServletRequest) {
    Cookie[] cookies = httpServletRequest.getCookies();
    if(cookies != null){
      for(Cookie cookie : cookies){
        if(cookie.getName().equalsIgnoreCase(REQUEST_COOKIE_SSO_USER)) {
          return cookie.getValue();
        }
      }
    }
    return securityManager.isSsoEnable() ? null : N_A;
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest httpServletRequest) {
    return REQUEST_COOKIE_SSO_USER;
  }

}
