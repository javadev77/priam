package fr.sacem.priam.model.domain.saref;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author Fandi
 * @since 27/09/17.
 */
public class SareftjLibUtilPK implements Serializable {
    private String cdelng;
    private String cdeutil;

    @Column(name = "CDELNG")
    @Id
    public String getCdelng() {
        return cdelng;
    }

    public void setCdelng(String cdelng) {
        this.cdelng = cdelng;
    }

    @Column(name = "CDEUTIL")
    @Id
    public String getCdeutil() {
        return cdeutil;
    }

    public void setCdeutil(String cdeutil) {
        this.cdeutil = cdeutil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SareftjLibUtilPK that = (SareftjLibUtilPK) o;

        if (cdelng != null ? !cdelng.equals(that.cdelng) : that.cdelng != null) return false;
        if (cdeutil != null ? !cdeutil.equals(that.cdeutil) : that.cdeutil != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cdelng != null ? cdelng.hashCode() : 0;
        result = 31 * result + (cdeutil != null ? cdeutil.hashCode() : 0);
        return result;
    }
}
