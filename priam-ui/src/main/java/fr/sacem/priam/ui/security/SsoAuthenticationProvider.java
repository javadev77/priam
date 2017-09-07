package fr.sacem.priam.ui.security;

import fr.sacem.fwk.security.SecurityManager;
import fr.sacem.fwk.security.UserProfile;
import fr.sacem.fwk.security.exception.SecurityManagerException;
import fr.sacem.priam.common.constants.RoleRight;
import fr.sacem.priam.model.dao.jpa.RoleDao;
import fr.sacem.priam.model.domain.Role;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ref to: PreAuthenticatedAuthenticationProvider
 *
 * @author <a href="mailto:xchen@palo-it.com">Xi CHEN</a>
 * @since 12/11/14.
 */

public class SsoAuthenticationProvider implements AuthenticationProvider, InitializingBean, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(SsoAuthenticationProvider.class);

    @Autowired
    private SecurityManager securityManager;
    
    @Autowired
    private RoleDao roleDao;

    private int order = 0;
    private boolean throwExceptionWhenTokenRejected = false;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("PreAuthenticated authentication request: " + authentication);
        }

        if (authentication.getPrincipal() == null) {
            logger.debug("No pre-authenticated principal found in request.");

            if (throwExceptionWhenTokenRejected) {
                throw new BadCredentialsException("No pre-authenticated principal found in request.");
            }
            return null;
        }

        if (authentication.getCredentials() == null) {
            logger.debug("No pre-authenticated credentials found in request.");

            if (throwExceptionWhenTokenRejected) {
                throw new BadCredentialsException("No pre-authenticated credentials found in request.");
            }
            return null;
        }

        String ssoToken = authentication.getName();
        UserProfile userProfile;
        Set<GrantedAuthority> authorities;
        try {
            userProfile = securityManager.getUserProfile(ssoToken);
            if(logger.isDebugEnabled()){
                logger.debug("Got logged user profile with token {} : {}", ssoToken, ToStringBuilder.reflectionToString(userProfile));
            }
            authorities = grantAuthorities(userProfile, authentication);
            if(authorities == null || authorities.isEmpty()){
                throw new InsufficientAuthenticationException("No authorized application role.");
            }

            SecurityManager.setCurrentUser(userProfile);
        } catch (SecurityManagerException e) {
            throw new AuthenticationServiceException("authentication with security manager got error", e);
        }

        SsoAuthenticationToken token = new SsoAuthenticationToken(userProfile, authorities, authentication);
        logger.info("User {} (id={}) is logged in.", token.getUser().getDisplayName(), token.getPrincipal());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.equals(authentication)
                || SsoAuthenticationToken.class.equals(authentication);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(securityManager, "An SecurityManager must be set");
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    private Set<GrantedAuthority> grantAuthorities(UserProfile profile, Authentication authentication) {
        final Set<RoleRight> rights = new HashSet<>();
        List<String> roleList = profile.getRoleList();
        if(roleList != null){
            if(logger.isDebugEnabled()){
                logger.debug("got user with roles {}", profile.getRoleList());
            }
            List<Role> roles = roleList.isEmpty() ? null : roleDao.findByExternalIdIn(roleList);
            if(roles != null && !roles.isEmpty()){
                if(logger.isDebugEnabled()){
                    logger.debug("mapped user with roles {}", roles);
                }
                for(Role r : roles){
                    if(r.getRights() != null){
                        rights.addAll(r.getRights());
                    }
                }
                if(logger.isDebugEnabled()){
                    logger.debug("recognized user with role rights {}", rights);
                }
            }
        }

        if(rights.isEmpty() && logger.isWarnEnabled()){
            logger.warn("user id={} has any mapped application role", profile.getUserId());
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        if(!rights.isEmpty()){
            for(RoleRight right : rights){
                authorities.add(SsoAuthority.valueOf(right));
            }
            if(authentication.getAuthorities() != null){
                authorities.addAll(authentication.getAuthorities());
            }
        }

        return authorities;
    }
}
