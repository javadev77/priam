package fr.sacem.priam.security.config;

import fr.sacem.fwk.security.Attribute;
import fr.sacem.fwk.security.UserProfile;
import fr.sacem.priam.common.constants.RoleRight;
import fr.sacem.priam.security.model.UserDTO;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * Created by embouazzar on 23/08/2017.
 */

public class SsoAuthenticationToken extends AbstractAuthenticationToken {

  private UserDTO user;
  private String ssoToken;
  private String principal;
  private Object credentials;

  private UserProfile userProfile;

  public SsoAuthenticationToken(UserProfile userProfile, Set<GrantedAuthority> authorities, Authentication auth) {
    super(authorities);
    this.ssoToken = auth.getName();
    this.credentials = auth.getCredentials();
    this.setDetails(auth.getDetails());
    this.setAuthenticated(userProfile != null);
    updateUserProfile(userProfile);
  }

  /**
  *
  * @return sacem sso token (cookie value)
  */

  public String getSsoToken() {
    return ssoToken;
  }

  /**
   *
   * @return homere user object
  */

  public UserDTO getUser() {
    return user;
  }

  @Override
  public String getPrincipal() {
    return this.principal;
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  public UserProfile getUserProfile() {
    return userProfile;
  }


  public void updateUserProfile(UserProfile userProfile) {
    if(userProfile != null){
      //available attributes: telephonenumber, mail, displayname, preferredlanguage, givenname, sacemcivilite,
      String displayName = "";
      Attribute attr = userProfile.getAttribute("displayname");
      if(attr != null){
        Object value = attr.getValue();
        displayName = value == null ? "" : String.valueOf(value);
      }
      this.user = new UserDTO(userProfile.getUserId());
      this.user.setRoleList(userProfile.getRoleList());
      this.user.setDisplayName(displayName);
      for(GrantedAuthority ga : getAuthorities()){
        RoleRight r = ((SsoAuthority)ga).getRight();
        user.getRights().add(r.name());
      }
      this.principal = userProfile.getUserId();
    }
    this.userProfile = userProfile;
  }

}
