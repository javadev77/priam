package fr.sacem.priam.model.domain.saref;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * @author Fandi
 * @since 27/09/17.
 */
@Entity
@IdClass(SareftjLibterPK.class)
public class SareftjLibter {

    private String cdelng;
    private int cdepaysiso4N;
    private String nompaysabr;
    private String nompays;
    private Timestamp datcre;
    private String usercre;
    private Timestamp datmaj;
    private String usermaj;
    @Id
    @Column(name = "CDELNG", insertable = false, updatable = false)
    public String getCdelng() {
        return cdelng;
    }

    public void setCdelng(String cdelng) {
        this.cdelng = cdelng;
    }

    @Id
    @Column(name = "CDEPAYSISO4N", insertable = false, updatable = false)
    public int getCdepaysiso4N() {
        return cdepaysiso4N;
    }

    public void setCdepaysiso4N(int cdepaysiso4N) {
        this.cdepaysiso4N = cdepaysiso4N;
    }

    @Basic
    @Column(name = "NOMPAYSABR", insertable = false, updatable = false)
    public String getNompaysabr() {
        return nompaysabr;
    }

    public void setNompaysabr(String nompaysabr) {
        this.nompaysabr = nompaysabr;
    }

    @Basic
    @Column(name = "NOMPAYS", insertable = false, updatable = false)
    public String getNompays() {
        return nompays;
    }

    public void setNompays(String nompays) {
        this.nompays = nompays;
    }

    @Basic
    @Column(name = "DATCRE", insertable = false, updatable = false)
    public Timestamp getDatcre() {
        return datcre;
    }

    public void setDatcre(Timestamp datcre) {
        this.datcre = datcre;
    }

    @Basic
    @Column(name = "USERCRE", insertable = false, updatable = false)
    public String getUsercre() {
        return usercre;
    }

    public void setUsercre(String usercre) {
        this.usercre = usercre;
    }

    @Basic
    @Column(name = "DATMAJ", insertable = false, updatable = false)
    public Timestamp getDatmaj() {
        return datmaj;
    }

    public void setDatmaj(Timestamp datmaj) {
        this.datmaj = datmaj;
    }

    @Basic
    @Column(name = "USERMAJ", insertable = false, updatable = false)
    public String getUsermaj() {
        return usermaj;
    }

    public void setUsermaj(String usermaj) {
        this.usermaj = usermaj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SareftjLibter that = (SareftjLibter) o;

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
