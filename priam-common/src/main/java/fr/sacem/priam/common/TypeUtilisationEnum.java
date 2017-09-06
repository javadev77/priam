package fr.sacem.priam.common;

/**
 * Created by belwidanej on 17/08/2017.
 */
public enum TypeUtilisationEnum {

    PRIME("PRIME", null),
    ENCOURAGEMENT("ENCOURG", null),
    FONDS_DE_VALORISATION("VALORIS", null),
    COPIE_PRIVEE_SONORE("COPRIVSON", null),
    COPIE_PRIVEE_AUDIOVISUELLE("CPRIVAUDV", "COPIEPRIV"),
    COPIE_PRIVEE_AUDIOV_LITT("CPRIVAUDPL", "COPIEPRIV"),
    COPIE_PRIVEE_SONORE_PHONO("CPRIVSONPH", "COPIEPRIV"),
    COPIE_PRIVEE_SONORE_RADIO("CPRIVSONRD", "COPIEPRIV");

    private final String code;
    private final String codeFamille;

    TypeUtilisationEnum(String code, String codeFamille) {
        this.code = code;
        this.codeFamille = codeFamille;
    }

    public static TypeUtilisationEnum getValue(String code) {

        for( TypeUtilisationEnum typeUtilisation : TypeUtilisationEnum.values()) {
            if(code.equals(typeUtilisation.getCode())) {
                return typeUtilisation;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getCodeFamille() { return codeFamille; }
}
