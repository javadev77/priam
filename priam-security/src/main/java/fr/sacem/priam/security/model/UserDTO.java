package fr.sacem.priam.security.model;

import com.google.common.collect.Lists;
import static fr.sacem.priam.model.util.FamillePriam.CMS;
import static fr.sacem.priam.model.util.FamillePriam.COPIE_PRIVEE;
import static fr.sacem.priam.model.util.FamillePriam.VALORISATION;
import static fr.sacem.priam.model.util.TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO;
import static fr.sacem.priam.model.util.TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO;
import static fr.sacem.priam.model.util.TypeUtilisationPriam.SONOANT;
import static fr.sacem.priam.model.util.TypeUtilisationPriam.SONOFRA;
import static fr.sacem.priam.model.util.TypeUtilisationPriam.getCodesValorisation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return "GUEST";
      }

      @Override
      public String getUserId() {
        return "GUEST";
      }

      @Override
      public Set<String> getRights() {
        return Collections.emptySet();
      }

      @Override
      public List<String> getRoleList() {
        return Lists.newArrayList(ROLE_GEST_CP, ROLE_GEST_CMS);
      }
    };


    public static final String ROLE_GEST_CP = "Gest_CP";
    public static final String ROLE_GEST_CMS = "Gest_CMS";
    public static final String ROLE_ADM = "ADM";
    private static final String ROLE_GEST_FV = "Gest_FV";

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
          if(ROLE_GEST_CP.equals(role) || ROLE_ADM.equals(role)) {
              codes.add(COPIE_PRIVEE_SONORE_PHONO.getCode());
              codes.add(COPIE_PRIVEE_SONORE_RADIO.getCode());
          }

          if(ROLE_GEST_CMS.equals(role) || ROLE_ADM.equals(role)) {
              codes.add(SONOFRA.getCode());
              codes.add(SONOANT.getCode());
          }

          if(ROLE_GEST_FV.equals(role) || ROLE_ADM.equals(role)) {
              codes.addAll(getCodesValorisation());
          }
        }
      }

      if(codes.isEmpty()) {
          codes.add(COPIE_PRIVEE_SONORE_PHONO.getCode());
          codes.add(COPIE_PRIVEE_SONORE_RADIO.getCode());
          codes.add(SONOFRA.getCode());
          codes.add(SONOANT.getCode());
          codes.addAll(getCodesValorisation());
      }


        return codes;
    }

  public List<String> authorizedFamilles() {
    List<String> codes = new ArrayList<>();
    List<String> roleList = getRoleList();
    if(roleList != null && !roleList.isEmpty()) {
      for(String role : roleList) {
          if(ROLE_GEST_CP.equals(role) || ROLE_ADM.equals(role)) {
              codes.add(COPIE_PRIVEE.getCode());
          }
          if(ROLE_GEST_CMS.equals(role) || ROLE_ADM.equals(role)) {
              codes.add(CMS.getCode());
          }

          if(ROLE_GEST_FV.equals(role) || ROLE_ADM.equals(role)) {
              codes.add(VALORISATION.getCode());
          }
      }
    }

    if(codes.isEmpty()) {
        codes.add(COPIE_PRIVEE.getCode());
        codes.add(CMS.getCode());
        codes.add(VALORISATION.getCode());
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
