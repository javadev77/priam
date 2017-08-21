package fr.sacem.priam.model.domain;

import java.io.Serializable;

/**
 * Created by belwidanej on 21/08/2017.
 */
public class TerritoirePK implements Serializable {

    private String cdeLng;
    private Integer cdePaysIso4N;

    public String getCdeLng() { return cdeLng; }

    public void setCdeLng(String cdeLng) { this.cdeLng = cdeLng; }

    public Integer getCdePaysIso4N() { return cdePaysIso4N; }

    public void setCdePaysIso4N(Integer cdePaysIso4N) { this.cdePaysIso4N = cdePaysIso4N; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TerritoirePK that = (TerritoirePK) o;

        if (!cdeLng.equals(that.cdeLng)) return false;
        return cdePaysIso4N.equals(that.cdePaysIso4N);
    }

    @Override
    public int hashCode() {
        int result = cdeLng.hashCode();
        result = 31 * result + cdePaysIso4N.hashCode();
        return result;
    }
}
