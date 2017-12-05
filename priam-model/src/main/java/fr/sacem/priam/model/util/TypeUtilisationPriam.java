package fr.sacem.priam.model.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benmerzoukah on 03/10/2017.
 */
public enum TypeUtilisationPriam {
    
    COPIE_PRIVEE_SONORE_PHONO("CPRIVSONPH"),
    
    COPIE_PRIVEE_SONORE_RADIO("CPRIVSONRD"),

    SONOFRA("SONOFRA"),

    SONOANT("SONOANT");
    
    
    private final String code;
    
    TypeUtilisationPriam(String code) {
	  this.code = code;
    }
    
    public String getCode() {
	  return code;
    }
    
    public static List<String> getCodes() {
	  List<String> codes = new ArrayList<>();
	  for(TypeUtilisationPriam entry : values()) {
		codes.add(entry.code);
	  }
	  
	  return codes;
    }
}
