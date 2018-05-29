package fr.sacem.priam.common.util;

import fr.sacem.frmwk.security.cmd.BusinessSecurityManager;
import fr.sacem.frmwk.security.plugins.exception.SecurityPluginException;
import fr.sacem.frmwk.security.plugins.guest.GuestUserProfileBuilder;
import fr.sacem.fwk.config.Environment;
import fr.sacem.fwk.config.FrmwkEnv;
import fr.sacem.fwk.security.*;
import org.slf4j.Logger;
import org.springframework.http.HttpRequest;

/**
 * @author <a href="mailto:xchen@palo-it.com">Xi CHEN</a>
 * @since 16/02/15.
 */
public class SsoUtils {

    /**
     * Header contenant le token wesso
     */
    public static final String WESSO_AUTHCOOKIE = "WessoAuthcookie";

    public static boolean isGrantAccessForAsset(Logger LOGGER, String wessoToken, boolean strictMode, String assetId, BusinessSecurityManager bsm)
        throws SecurityPluginException {

        if (BusinessSecurityManager.isSecurityEnable() && wessoToken != null) {
            // Obtenir le profile de l'utilisateur
            // dans ce contexte d'execution
            UserProfile userProfile = bsm.getUserProfile(wessoToken);

            StringBuilder msgBuild = new StringBuilder();
            if (userProfile instanceof TokenProfile) {
                // Tracer l'accès
                TokenProfile tokenProfile = (TokenProfile) userProfile;
                msgBuild.append("User access [").append(userProfile.getUserId())
                    .append(" from [").append(tokenProfile.getOrigin()).append(",").append(userProfile.getRoleList()).append(']');

                LOGGER.info(msgBuild.toString());
            } else {
                // Tracer l'accès
                msgBuild.append("User access [").append(userProfile.getUserId()).append(",").append(userProfile.getRoleList())
                    .append("] - Profile is not TokenProfile.");

                LOGGER.warn(msgBuild.toString());
            }

            // Stockage du profil dans la varible ThreadLocal
            CurrentUserProfile.setProfile(userProfile);

            boolean assetGranted = bsm.isAssetGranted(userProfile, assetId);
            if(assetGranted){
                LOGGER.info(msgBuild.append(" is granted").toString());
            }else{
                LOGGER.info(msgBuild.append(" is denied").toString());
            }
            return assetGranted;

        } else if (BusinessSecurityManager.isSecurityEnable() && strictMode) {

            // Pas la peine d'aller plus loin, on est en mode strict
            LOGGER.info("Access is denied");
            return false;

        } else {
            // Mode mixte ou pas de sécurité
            // Pas d'utilisateur particulier pour cette requête
            // Stockage du profil guest dans la variable ThreadLocal pour éviter
            // un NPE
            UserProfile guestProfile = GuestUserProfileBuilder.guestProfile(FrmwkEnv.DEFAULT_USER).toUserProfile(
                Environment.getParameter(FrmwkEnv.PROJECT_SECURITY_NAME));
            CurrentUserProfile.setProfile(guestProfile);

            // Tracer l'accès
            StringBuilder msgBuild = new StringBuilder();
            msgBuild.append("User access [").append(guestProfile.getUserId());

            LOGGER.info(msgBuild.toString());

            boolean assetGranted = bsm.isAssetGranted(guestProfile, assetId);
            if(assetGranted){
                LOGGER.info(msgBuild.append(" is granted").toString());
            }else{
                LOGGER.info(msgBuild.append(" is denied").toString());
            }
            return assetGranted;
        }

    }

    public static void addSsoHeaders(Logger LOGGER, HttpRequest request){
        boolean hasSso = FrmwkEnv.TRUE.equalsIgnoreCase(
            Environment.getParameter(FrmwkEnv.APP_SSO_CONTROL, FrmwkEnv.APP_SSO_CONTROL_DEFAULT));
        ApplicationProfile profile = CurrentApplicationProfile.getProfile();
        if(hasSso && profile instanceof TokenApplicationProfile){
            String rawToken = ((TokenApplicationProfile) profile).getRawToken();
            request.getHeaders().set("Set-cookie", SsoUtils.WESSO_AUTHCOOKIE + "=" + rawToken);
            request.getHeaders().set(SsoUtils.WESSO_AUTHCOOKIE, rawToken);
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("added request header {} = {}", SsoUtils.WESSO_AUTHCOOKIE, rawToken);
            }
        }
    }

    private static int count = 1 ;

    public static String getSsoToken(){

        boolean hasSso = FrmwkEnv.TRUE.equalsIgnoreCase(
            Environment.getParameter(FrmwkEnv.APP_SSO_CONTROL, FrmwkEnv.APP_SSO_CONTROL_DEFAULT));
        ApplicationProfile profile = CurrentApplicationProfile.getProfile();
        if(hasSso && profile instanceof TokenApplicationProfile){
            return ((TokenApplicationProfile) profile).getRawToken();
        }

        return ""+(count++) ;   // returns a fake token that must evolve (used in mocked mipsa web component SSO)
    }

}
