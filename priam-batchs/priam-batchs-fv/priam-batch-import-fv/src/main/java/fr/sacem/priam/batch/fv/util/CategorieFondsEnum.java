package fr.sacem.priam.batch.fv.util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by embouazzar on 11/12/2018.
 */
public enum CategorieFondsEnum {

    CAT_01("FD06","FD09"),

    CAT_02("FD12"),

    CAT_03("FD03","FD04", "FD10","FD11"),

    CAT_04("FD01","FD02", "FD05","FD07","FD13");

    private final String[] code;

    CategorieFondsEnum(String... code) {
        this.code = code;
    }

    public static CategorieFondsEnum getValue(String code) {
        for( CategorieFondsEnum categorieFondsEnum : CategorieFondsEnum.values()) {
            List<String> strings = Arrays.asList(categorieFondsEnum.getCode());
            if(strings.contains(code)) {
                return categorieFondsEnum;
            }
        }
        return null;
    }

    public String[] getCode() {
        return code;
    }
}
