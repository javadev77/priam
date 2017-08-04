package fr.sacem.priam.ui.rest.dto;

import com.google.common.base.MoreObjects;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jbelwidane on 02/08/2017.
 */
public class ValdierSelectionProgrammeInput {

  private String numProg;
  private boolean deselectAll;
  private boolean all;
  private Set<Long> unselected = new HashSet<>();
  private Set<Long> selected = new HashSet<>();

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

  public Set<Long> getUnselected() {
    return unselected;
  }

  public void setUnselected(Set<Long> unselected) {
    this.unselected = unselected;
  }

  public Set<Long> getSelected() {
    return selected;
  }

  public void setSelected(Set<Long> selected) {
    this.selected = selected;
  }

  public boolean isDeselectAll() { return deselectAll; }

  public void setDeselectAll(boolean deselectAll) { this.deselectAll = deselectAll; }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).toString();
  }
}
