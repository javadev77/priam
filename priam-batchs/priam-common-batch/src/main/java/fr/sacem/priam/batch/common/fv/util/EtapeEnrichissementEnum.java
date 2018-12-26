package fr.sacem.priam.batch.common.fv.util;

/**
 * Created by embouazzar on 21/12/2018.
 */
public enum EtapeEnrichissementEnum {

    TO_SRV_OCTAV_CTNU("TO_SRV_OCTAV_CTNU"),
    IN_SRV_OCTAV_CTNU("IN_SRV_OCTAV_CTNU"),
    DONE_SRV_OCTAV_CTNU("DONE_SRV_OCTAV_CTNU"),
    TO_SRV_INFO_OEUVRE("TO_SRV_INFO_OEUVRE"),
    IN_SRV_INFO_OEUVRE("IN_SRV_INFO_OEUVRE"),
    DONE_SRV_INFO_OEUVRE("DONE_SRV_INFO_OEUVRE");

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
