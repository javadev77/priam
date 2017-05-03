package fr.sacem.priam.web.rest.dto;

import java.util.List;

/**
 * Created by benmerzoukah on 28/04/2017.
 */
public class InputChgtCriteria {
    private String familleCode;
    private String typeUtilisationCode;
    private List<String> statutCode;
    
    public InputChgtCriteria() {
    
    }
    
    public String getFamilleCode() {
        return familleCode;
    }
    
    public void setFamilleCode(String familleCode) {
        this.familleCode = familleCode;
    }
  
    public String getTypeUtilisationCode() {
        return typeUtilisationCode;
    }
  
    public void setTypeUtilisationCode(String typeUtilisationCode) {
        this.typeUtilisationCode = typeUtilisationCode;
    }
  
    public List<String> getStatutCode() {
        return statutCode;
    }
  
    public void setStatutCode(List<String> statutCode) {
        this.statutCode = statutCode;
    }
}
