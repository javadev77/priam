package fr.sacem.priam.model.util;

import java.util.ArrayList;
import java.util.List;

public enum EtatOeuvreEnum {

    AUTOMATIQUE("AUTOMATIQUE", "Automatique"),

    CORRIGE("CORRIGE", "Corrigé"),

    MANUEL("MANUEL", "Manuel");

    private final String code;
    private final String libelle;

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    EtatOeuvreEnum(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static EtatOeuvreEnum getValue(String code) {

        for( EtatOeuvreEnum etatOeuvreEnum : EtatOeuvreEnum.values()) {
            if(code.equals(etatOeuvreEnum.getCode())) {
                return etatOeuvreEnum;
            }
        }
        return null;
    }

    public static List<String> getCodes() {
        List<String> codes = new ArrayList<>();
        for(EtatOeuvreEnum entry : values()) {
            codes.add(entry.code);
        }
        return codes;
    }
}
