package fr.sacem.priam.ui.security;

import fr.sacem.priam.common.constants.RoleRight;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by embouazzar on 23/08/2017.
 */

public class SsoAuthority implements GrantedAuthority{

  private static final Map<RoleRight, SsoAuthority> MAP;

  static{
    Map<RoleRight, SsoAuthority> temp = new HashMap<>();
    for (RoleRight roleRight : RoleRight.values()) {
      temp.put(roleRight, new SsoAuthority(roleRight));
    }
    MAP = Collections.unmodifiableMap(temp);
  }

  public static SsoAuthority valueOf(RoleRight roleRight){
    return MAP.get(roleRight);
  }

  private final RoleRight right;

  protected SsoAuthority() {
    this(RoleRight.READPRG);
  }

  private SsoAuthority(RoleRight right) {
    this.right = right;
    Assert.notNull(right, "An RoleRight must be set");
  }

  @Override
  public String getAuthority() {
    return right.name();
  }

  public RoleRight getRight() {
    return right;
  }

  @Override
  public String toString() {
    return "SsoAuthority{" + right + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SsoAuthority that = (SsoAuthority) o;

    return right.equals(that.right);

  }

  @Override
  public int hashCode() {
    return right != null ? right.hashCode() : 0;
  }

}
