package fr.sacem.priam.batch.common.fv.util;

public enum EtapeEnrichissementLogEnum {

    LOG_IN_SRV_CTNU("LOG_IN_SRV_CTNU","Service CTNU - Envoyé"),
    LOG_DONE_SRV_CTNU("LOG_DONE_SRV_CTNU","Service CTNU - Traité"),
    LOG_ERROR_SRV_CTNU("LOG_ERROR_SRV_CTNU","Service CTNU - En erreur"),

    LOG_IN_SRV_INFO_OEUVRE("LOG_SRV_INFO_OEUVRE", "Service Info Oeuvre - Envoyé"),
    LOG_DONE_SRV_INFO_OEUVRE("LOG_SRV_INFO_OEUVRE", "Service Info Oeuvre - Traité"),
    LOG_ERROR_SRV_INFO_OEUVRE("LOG_SRV_INFO_OEUVRE", "Service Info Oeuvre - En erreur"),

    LOG_IN_SRV_AD_CLE("LOG_IN_SRV_AD_CLE", "Service AD Clé - Envoyé"),
    LOG_DONE_SRV_AD_CLE("LOG_DONE_SRV_AD_CLE", "Service AD Clé - Traité"),
    LOG_ERROR_SRV_AD_CLE("LOG_ERROR_SRV_AD_CLE", "Service AD Clé - En erreur"),

    LOG_IN_SRV_AD_INFOS("LOG_IN_SRV_AD_INFOS", "Service AD Infos - Envoyé"),
    LOG_DONE_SRV_AD_INFOS("LOG_DONE_SRV_AD_INFOS", "Service AD Infos - Traité"),
    LOG_ERROR_SRV_AD_INFOS("LOG_ERROR_SRV_AD_INFOS", "Service AD Infos - En erreur");

    private final String code;
    private final String libelle;

    EtapeEnrichissementLogEnum(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static EtapeEnrichissementLogEnum getValue(String code) {

        for( EtapeEnrichissementLogEnum etapeEnrichissementLogEnum : EtapeEnrichissementLogEnum.values()) {
            if(code.equals(etapeEnrichissementLogEnum.getCode())) {
                return etapeEnrichissementLogEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }
}
