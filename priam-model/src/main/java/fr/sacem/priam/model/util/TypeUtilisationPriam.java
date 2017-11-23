package fr.sacem.priam.model.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benmerzoukah on 03/10/2017.
 */
public enum TypeUtilisationPriam {
    
    COPIE_PRIVEE_SONORE_PHONO("CPRIVSONPH"),
    
    COPIE_PRIVEE_SONORE_RADIO("CPRIVSONRD"),

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

    public static List<String> authorizedTypeUtilisationsByRole(List<String> roleList) {
        List<String> codes = new ArrayList<>();
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
}
