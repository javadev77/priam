package fr.sacem.util;

public enum RionEnum {

    RION_2_ANT("RION-2"),
    RION_4_ANT("RION-4");

    private final String code;

    RionEnum(String code) {
        this.code = code;
    }

    public static RionEnum getValue(String code) {
        for( RionEnum rionEnum : RionEnum.values()) {
            if(code.equals(rionEnum.getCode())) {
                return rionEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }
}
