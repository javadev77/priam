package fr.sacem.priam.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by benmerzoukah on 03/10/2017.
 */
public enum TypeUtilisationPriam {
    
    COPIE_PRIVEE_SONORE_PHONO("CPRIVSONPH"),
    
    COPIE_PRIVEE_SONORE_RADIO("CPRIVSONRD"),

    SONOFRA("SONOFRA"),

    SONOANT("SONOANT"),

    FD01("FD01"),

    FD02("FD02"),

    FD03("FD03"),

    FD04("FD04"),

    FD05("FD05"),

    FD06("FD06"),

    FD07("FD07"),

    FD09("FD09"),

    FD10("FD10"),

    FD11("FD11"),

    FD12("FD12"),

    FD13("FD13"),

    FD14("FD14");

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

    public static List<String> getCodesValorisation() {
        return getCodes().stream().filter(code -> code.startsWith("FD")).collect(Collectors.toList());
    }
}
