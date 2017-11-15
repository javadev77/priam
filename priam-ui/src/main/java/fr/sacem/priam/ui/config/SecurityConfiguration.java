package fr.sacem.priam.ui.config;

import fr.sacem.fwk.config.Environment;
import fr.sacem.fwk.security.SecurityManager;
import fr.sacem.priam.ui.security.AjaxLogoutSuccessHandler;
import fr.sacem.priam.ui.security.Http401UnauthorizedEntryPoint;
import fr.sacem.priam.ui.security.SsoAuthenticationProvider;
import fr.sacem.priam.ui.security.SsoPreAuthenticatedProcessingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;

import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by embouazzar on 23/08/2017.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);
  private static final String STATIC_RESOURCES_REGEX = ".*(/static/.*)|/(error|404).*";

  @Override
  protected void  configure(HttpSecurity http) throws Exception {

    String webappMode = Environment.getParameter("webapp.mode");
    if("dev".equalsIgnoreCase(webappMode)) {
      http.cors().and();
    }
    http.csrf().disable()
      .exceptionHandling()
      .accessDeniedPage("/404.html")
      .authenticationEntryPoint(authenticationEntryPoint())
      .and()
      .logout()
      .invalidateHttpSession(true)
      .deleteCookies(SsoPreAuthenticatedProcessingFilter.REQUEST_COOKIE_SSO_USER)
      .logoutSuccessHandler(logoutSuccessHandler())
      .and()
      .addFilter(ssoPreAuthenticatedProcessingFilter())
      .sessionManagement().maximumSessions(10).sessionRegistry(sessionRegistry()).and().sessionFixation().migrateSession()
      .and()
      .authorizeRequests()
      .accessDecisionManager(affirmativeBased())
      .regexMatchers(STATIC_RESOURCES_REGEX).permitAll()
      .anyRequest().authenticated();

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
      .regexMatchers(STATIC_RESOURCES_REGEX);
  }

  @Bean(name = "sessionRegistry")
  @Primary
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  /**
   * Should not use @Bean annotation, to avoid double filter registration
   * @return
   * @throws Exception
   */
  private SsoPreAuthenticatedProcessingFilter ssoPreAuthenticatedProcessingFilter() throws Exception {
    return new SsoPreAuthenticatedProcessingFilter(securityManager(), authenticationManagerBean());
  }

  @Bean
  @Primary
  public AuthenticationEntryPoint authenticationEntryPoint() {
      return new Http401UnauthorizedEntryPoint();
  }

  @Bean
  @Primary
  public LogoutSuccessHandler logoutSuccessHandler() {
    return new AjaxLogoutSuccessHandler();
  }

  @Bean
  @Primary
  public AccessDecisionManager affirmativeBased() {
    List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
    decisionVoters.add(roleVoter());
    decisionVoters.add(new WebExpressionVoter());
    decisionVoters.add(new AuthenticatedVoter());
    return new AffirmativeBased(decisionVoters);
  }

  @Bean
  @Primary
  public RoleVoter roleVoter() {
    RoleVoter e = new RoleVoter();
    e.setRolePrefix("");
    return e;
  }

  @Bean
  @Primary
  public SecurityManager securityManager() throws Exception {
    // Chercher le securityManager si la securite doit etre prise en compte
    if (SecurityManager.getInstance().isSsoEnable()) {
      return SecurityManager.getInstance(true);
    } else {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Securité non activée");
      }
      return SecurityManager.getInstance();
    }
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(ssoAuthenticationProvider());
  }

  @Bean
  @Primary
  @Order(0)
  public AuthenticationProvider ssoAuthenticationProvider() {
    return new SsoAuthenticationProvider();
  }

}

