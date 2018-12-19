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
    COPIE_PRIVEE_SONORE_RADIO("CPRIVSONRD", "COPIEPRIV"),
    CMS_FRA("SONOFRA","UC"),
    CMS_ANT("SONOANT","UC"),
    FV_FONDS_01("FD01","FDSVAL"),
    FV_FONDS_02("FD02","FDSVAL"),
    FV_FONDS_03("FD03","FDSVAL"),
    FV_FONDS_04("FD04","FDSVAL"),
    FV_FONDS_05("FD05","FDSVAL"),
    FV_FONDS_06("FD06","FDSVAL"),
    FV_FONDS_07("FD07","FDSVAL"),
    FV_FONDS_09("FD09","FDSVAL"),
    FV_FONDS_10("FD10","FDSVAL"),
    FV_FONDS_11("FD11","FDSVAL"),
    FV_FONDS_12("FD12","FDSVAL"),
    FV_FONDS_13("FD13","FDSVAL");


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
