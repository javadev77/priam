package fr.sacem.priam.ui.config;


import fr.sacem.frmwk.security.cmd.BusinessSecurityManager;
import fr.sacem.frmwk.security.plugins.exception.SecurityPluginException;
import fr.sacem.fwk.config.Environment;
import fr.sacem.fwk.config.FrmwkEnv;
import fr.sacem.fwk.security.exception.SecurityManagerException;
import fr.sacem.priam.common.util.SsoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="mailto:xchen@palo-it.com">Xi CHEN</a>
 * @since 16/02/15.
 */
public class InterApplicationSecurityFilter implements Filter {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InterApplicationSecurityFilter.class);

    private static final String ASSET_ID = "homer.internal.ui.notification.ws";

    /**
     * Mode de la sécurité:
     * strict => pas authentifié, pas autorisé
     */
    private boolean strictMode;

    /**
     * SecurityManager pour lapartie métier
     */
    private BusinessSecurityManager businessSecurityManager;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest pReq, ServletResponse pResp,
                         FilterChain pChain) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) pReq;
        HttpServletResponse httpResp = (HttpServletResponse) pResp;

        String wessoToken = httpReq.getHeader(SsoUtils.WESSO_AUTHCOOKIE);
        //String assetId = getRestAssetId(httpReq);

        try{
            if(SsoUtils.isGrantAccessForAsset(LOGGER, wessoToken, this.strictMode, ASSET_ID, businessSecurityManager)){
                // On passe le relais aux autres filtres/servlet
                pChain.doFilter(pReq, pResp);
            }else{
                // Le jeton WESSO n'est pas valide
                httpResp.sendError(
                    HttpServletResponse.SC_FORBIDDEN, "Not authorized");
            }
        }catch (SecurityPluginException e){
            // Le jeton WESSO n'est pas valide
            httpResp.sendError(
                HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }

    }

    @Override
    public void init(FilterConfig pConf) throws ServletException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Initialisation du filtre " + pConf.getFilterName());
        }

        // Définition du mode d'exécution
        this.strictMode =
            FrmwkEnv.TRUE.equalsIgnoreCase(Environment.getParameter(
                FrmwkEnv.APP_BUSINESS_CONTROL_STRICT,
                FrmwkEnv.APP_BUSINESS_CONTROL_STRICT_DEFAULT));

        // Chercher le securityManager si la securite doit etre prise en compte
        if (BusinessSecurityManager.isSecurityEnable()) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Securité activée en mode " + (strictMode ? "STRICT" : "MIXTE"));
                LOGGER.debug("Chargement instance de BusinessSecurityManager");
            }

            try {
                businessSecurityManager =
                    BusinessSecurityManager.getInstance(true);
            } catch (SecurityManagerException sme) {
                throw new ServletException(sme.getMessage());
            }
        } else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Securité non activée");
            }
        }
    }

    /**
     * Calcule l'assetid correspondant à la requête REST
     *
     * @param pHttpReq {@link HttpServletRequest}
     * @return assetid
     */
    private String getRestAssetId(HttpServletRequest pHttpReq) {
        String assetid = pHttpReq.getRequestURI();
        int idx = assetid.indexOf("?");
        if (idx < 0) {
            idx = assetid.length();
        }
        assetid = pHttpReq.getMethod() + assetid.substring(pHttpReq.getContextPath().length(), idx);
        return assetid;
    }
}
