package fr.sacem.priam.rest.common.api.dto;

/**
 * Created by benmerzoukah on 30/06/2017.
 */
public class AffectationCriteria extends InputChgtCriteria {
    private String numProg;
  
    public AffectationCriteria() {
      super();
    }
  
    public String getNumProg() {
      return numProg;
    }
  
    public void setNumProg(String numProg) {
      this.numProg = numProg;
    }
}
