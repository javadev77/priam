package fr.sacem.priam.common;

/**
 * Created by belwidanej on 17/08/2017.
 */
public enum TypeUtilisationEnum {

    PRIME("PRIME"),
    ENCOURAGEMENT("ENCOURG"),
    FONDS_DE_VALORISATION("VALORIS"),
    COPIE_PRIVEE_SONORE("COPRIVSON"),
    COPIE_PRIVEE_AUDIOVISUELLE("CPRIVAUDV"),
    COPIE_PRIVEE_AUDIOV_LITT("CPRIVAUDPL"),
    COPIE_PRIVEE_SONORE_PHONO("CPRIVSONPH"),
    COPIE_PRIVEE_SONORE_RADIO("CPRIVSONRD");

    private final String code;

    TypeUtilisationEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
