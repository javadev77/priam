package fr.sacem.priam.batch.felix.util;

public enum TypeRepartitionFVEnum {

    OEUVRE("O", "OEUVRE"),

    AYANT_DROIT("A", "AYANT_DROIT"),

    OEUVRE_AD("M", "OEUVRE_AD");

    private final String code;
    private final String libelle;

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    TypeRepartitionFVEnum(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

}
