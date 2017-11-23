package fr.sacem.priam.model.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benmerzoukah on 03/10/2017.
 */
public enum  FamillePriam {
    
    COPIE_PRIVEE("COPIEPRIV"),

    CMS("UC");
    
    private final String code;
    
    FamillePriam(String code) {
	  this.code = code;
    }
    
    public String getCode() {
	  return code;
    }
    
    public static List<String> getCodes() {
	  List<String> codes = new ArrayList<>();
	  for(FamillePriam entry : values()) {
		codes.add(entry.code);
	  }
	  
	  return codes;
    }

    public static List<String> authorizedFamillesByRole(List<String> roleList) {
        List<String> codes = new ArrayList<>();
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
}
