package fr.sacem.priam.model.domain.saref;

import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author Fandi
 * @since 27/09/17.
 */
@Entity
@Table(name = "SAREFTJ_LIBUTIL")
@IdClass(SareftjLibUtilPK.class)
public class SareftjLibUtil {

    private String cdelng;
    private String cdeutil;
    private String libutil;
    private String libabrgutil;
    private Date datcre;
    private String usercre;
    private Date datmaj;
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
    @Column(name = "CDEUTIL", insertable = false, updatable = false)
    public String getCdeutil() {
        return cdeutil;
    }

    public void setCdeutil(String cdeutil) {
        this.cdeutil = cdeutil;
    }

    @Basic
    @Column(name = "LIBUTIL", insertable = false, updatable = false)
    public String getLibutil() {
        return libutil;
    }

    public void setLibutil(String libutil) {
        this.libutil = libutil;
    }

    @Basic
    @Column(name = "LIBABRGUTIL", insertable = false, updatable = false)
    public String getLibabrgutil() {
        return libabrgutil;
    }

    public void setLibabrgutil(String libabrgutil) {
        this.libabrgutil = libabrgutil;
    }

    @Basic
    @Column(name = "DATCRE", insertable = false, updatable = false)
    public Date getDatcre() {
        return datcre;
    }

    public void setDatcre(Date datcre) {
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
    public Date getDatmaj() {
        return datmaj;
    }

    public void setDatmaj(Date datmaj) {
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

        SareftjLibUtil that = (SareftjLibUtil) o;

        if (cdelng != null ? !cdelng.equals(that.cdelng) : that.cdelng != null) return false;
        if (cdeutil != null ? !cdeutil.equals(that.cdeutil) : that.cdeutil != null) return false;
        if (libutil != null ? !libutil.equals(that.libutil) : that.libutil != null) return false;
        if (libabrgutil != null ? !libabrgutil.equals(that.libabrgutil) : that.libabrgutil != null) return false;
        if (datcre != null ? !datcre.equals(that.datcre) : that.datcre != null) return false;
        if (usercre != null ? !usercre.equals(that.usercre) : that.usercre != null) return false;
        if (datmaj != null ? !datmaj.equals(that.datmaj) : that.datmaj != null) return false;
        if (usermaj != null ? !usermaj.equals(that.usermaj) : that.usermaj != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cdelng != null ? cdelng.hashCode() : 0;
        result = 31 * result + (cdeutil != null ? cdeutil.hashCode() : 0);
        result = 31 * result + (libutil != null ? libutil.hashCode() : 0);
        result = 31 * result + (libabrgutil != null ? libabrgutil.hashCode() : 0);
        result = 31 * result + (datcre != null ? datcre.hashCode() : 0);
        result = 31 * result + (usercre != null ? usercre.hashCode() : 0);
        result = 31 * result + (datmaj != null ? datmaj.hashCode() : 0);
        result = 31 * result + (usermaj != null ? usermaj.hashCode() : 0);
        return result;
    }
}
