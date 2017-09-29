package fr.sacem.priam.model.domain.saref;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Fandi
 * @since 27/09/17.
 */
public class SareftjLibterPK implements Serializable {

    private String cdelng;
    private int cdepaysiso4N;

    @Column(name = "CDELNG", insertable = false, updatable = false)
    @Id
    public String getCdelng() {
        return cdelng;
    }

    public void setCdelng(String cdelng) {
        this.cdelng = cdelng;
    }

    @Column(name = "CDEPAYSISO4N", insertable = false, updatable = false)
    @Id
    public int getCdepaysiso4N() {
        return cdepaysiso4N;
    }

    public void setCdepaysiso4N(int cdepaysiso4N) {
        this.cdepaysiso4N = cdepaysiso4N;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SareftjLibterPK that = (SareftjLibterPK) o;

        if (cdepaysiso4N != that.cdepaysiso4N) return false;
        if (cdelng != null ? !cdelng.equals(that.cdelng) : that.cdelng != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cdelng != null ? cdelng.hashCode() : 0;
        result = 31 * result + cdepaysiso4N;
        return result;
    }
}
