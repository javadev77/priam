package fr.sacem.priam.security.model;

import java.io.Serializable;
import java.util.*;

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
