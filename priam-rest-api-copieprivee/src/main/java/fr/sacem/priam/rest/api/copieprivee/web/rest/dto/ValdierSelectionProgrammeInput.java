package fr.sacem.priam.rest.api.copieprivee.web.rest.dto;

import com.google.common.base.MoreObjects;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jbelwidane on 02/08/2017.
 */
public class ValdierSelectionProgrammeInput {

  private String numProg;
  private boolean deselectAll;
  private boolean all;
  private Set<Map<String, String>> unselected = new HashSet<>();
  private Set<Map<String, String>> selected = new HashSet<>();

  public ValdierSelectionProgrammeInput() {

  }

  public String getNumProg() {
    return numProg;
  }

  public void setNumProg(String numProg) {
    this.numProg = numProg;
  }

  public boolean isAll() {
    return all;
  }

  public void setAll(boolean all) {
    this.all = all;
  }

  public Set<Map<String, String>> getUnselected() {
    return unselected;
  }

  public void setUnselected(Set<Map<String, String>> unselected) {
    this.unselected = unselected;
  }

  public Set<Map<String, String>> getSelected() {
    return selected;
  }

  public void setSelected(Set<Map<String, String>> selected) {
    this.selected = selected;
  }

  public boolean isDeselectAll() { return deselectAll; }

  public void setDeselectAll(boolean deselectAll) { this.deselectAll = deselectAll; }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).toString();
  }
}
