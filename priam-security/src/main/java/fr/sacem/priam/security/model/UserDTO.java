package fr.sacem.priam.security.model;

import java.io.Serializable;
import java.util.*;

import static fr.sacem.priam.model.util.FamillePriam.CMS;
import static fr.sacem.priam.model.util.FamillePriam.COPIE_PRIVEE;
import static fr.sacem.priam.model.util.TypeUtilisationPriam.SONOANT;
import static fr.sacem.priam.model.util.TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO;
import static fr.sacem.priam.model.util.TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO;

/**
 * Created by embouazzar on 23/08/2017.
 */
public class UserDTO implements Serializable{

  public static final UserDTO GUEST = new UserDTO(){

    @Override
    public void setDisplayName(String displayName) {}

    @Override
    public void setRights(Set<String> rights) {}

    @Override
    public String getDisplayName() {
      return "Guest";
    }

    @Override
    public String getUserId() {
      return "guest";
    }

    @Override
    public Set<String> getRights() {
      return Collections.emptySet();
    }

    @Override
    public List<String> getRoleList() {
      return Arrays.asList("Gest_CP");
    }
  };

    private String userId;

    private Set<String> rights;

    private String displayName;

    private List<String> roleList;


    public UserDTO() {
    }

    public UserDTO(String userId) {
      this.userId = userId;
      this.rights = new HashSet<>();
    }

    public String getUserId() {
      return userId;
    }

    public Set<String> getRights() {
      return rights;
    }

    public void setRights(Set<String> rights) {
      this.rights = rights;
    }

    public void setDisplayName(String displayName) {
      this.displayName = displayName;
    }

    public String getDisplayName() {
      return displayName;
    }

    public List<String> getRoleList() {
      return roleList;
    }

    public void setRoleList(List<String> roleList) {

      this.roleList = roleList;
    }

    public List<String> authorizedTypeUtilisations() {
      List<String> codes = new ArrayList<>();
      List<String> roleList = getRoleList();
      if(roleList != null && !roleList.isEmpty()) {
        for(String role : roleList) {
          switch (role) {
            case "Gest_CP" :
              codes.add(COPIE_PRIVEE_SONORE_PHONO.getCode());
              codes.add(COPIE_PRIVEE_SONORE_RADIO.getCode());
              break;
            case "Gest_CMS" :
              codes.add(SONOANT.getCode());
              break;
          }
        }
      }

      return codes;
    }

  public List<String> authorizedFamilles() {
    List<String> codes = new ArrayList<>();
    List<String> roleList = getRoleList();
    if(roleList != null && !roleList.isEmpty()) {
      for(String role : roleList) {
        switch (role) {
          case "Gest_CP" :
            codes.add(COPIE_PRIVEE.getCode());
            break;
          case "Gest_CMS" :
            codes.add(CMS.getCode());
            break;
        }
      }
    }

    return codes;
  }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      UserDTO userDTO = (UserDTO) o;

      return !(userId != null ? !userId.equals(userDTO.userId) : userDTO.userId != null);

    }

    @Override
    public int hashCode() {
      return userId != null ? userId.hashCode() : 0;
    }

}
