package fr.sacem.priam.batch.common.fv.util;

/**
 * Created by embouazzar on 21/12/2018.
 */
public enum EtapeEnrichissementEnum {

    //Service OCTAV CTNU
    TO_SRV_OCTAV_CTNU("TO_SRV_OCTAV_CTNU"),
    IN_SRV_OCTAV_CTNU("IN_SRV_OCTAV_CTNU"),
    DONE_SRV_OCTAV_CTNU("DONE_SRV_OCTAV_CTNU"),

    //Service INFO OEUVRE OCTAV
    TO_SRV_INFO_OEUVRE("TO_SRV_INFO_OEUVRE"),
    IN_SRV_INFO_OEUVRE("IN_SRV_INFO_OEUVRE"),
    DONE_SRV_INFO_OEUVRE("DONE_SRV_INFO_OEUVRE"),


    //Service Ayant Droit Clés Pers OCTAV
    TO_SRV_AD_CLES_PROTECTION("TO_SRV_AD_CLES_PROTECTION"),
    IN_SRV_AD_CLES_PROTECTION("IN_SRV_AD_CLES_PROTECTION"),
    DONE_SRV_AD_CLES_PROTECTION("DONE_SRV_AD_CLES_PROTECTION"),

    //Service Ayant Droit Clés Pers OCTAV
    TO_SRV_AD_INFO("TO_SRV_AD_INFO"),
    IN_SRV_AD_INFO("IN_SRV_AD_INFO"),
    DONE_SRV_AD_INFO("DONE_SRV_AD_INFO");


    private final String code;

    EtapeEnrichissementEnum(String code) {
        this.code = code;
    }

    public static EtapeEnrichissementEnum getValue(String code) {
        for( EtapeEnrichissementEnum etape : EtapeEnrichissementEnum.values()) {
            if(code.equals(etape.getCode())) {
                return etape;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

}
