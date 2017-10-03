package fr.sacem.priam.model.domain.saref;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fandis on 04/08/2017. 
 */
@Entity
@Table(name="SAREFTJ_LIBUTIL")
@IdClass(SareftjLibUtilPK.class)
public class SareftjLibutil implements Serializable {
    @Id
    @Column(name = "CDEUTIL")
    private String cdeUtil;
    
    @Id
    @Column(name = "CDELNG")
    private String cdeLng;
    
    @Column(name = "LIBUTIL")
    private String libUtil;
    
    @Column(name = "LIBABRGUTIL")
    private String libAbrgUtil;
    
    @Column(name = "DATCRE")
    private Date dateCre;
    
    @Column(name = "USERCRE")
    private String userCre;
    
    @Column(name = "DATMAJ")
    private Date dateMaj;
    
    @Column(name = "USERMAJ")
    private String userMaj;
    
    @Column(name = "LIBFEUILLET")
    private String libFeuillet;

    public String getCdeUtil() {
        return cdeUtil;
    }

    public void setCdeUtil(String cdeUtil) {
        this.cdeUtil = cdeUtil;
    }

    public String getCdeLng() {
        return cdeLng;
    }

    public void setCdeLng(String cdeLng) {
        this.cdeLng = cdeLng;
    }

    public String getLibUtil() {
        return libUtil;
    }

    public void setLibUtil(String libUtil) {
        this.libUtil = libUtil;
    }

    public String getLibAbrgUtil() {
        return libAbrgUtil;
    }

    public void setLibAbrgUtil(String libAbrgUtil) {
        this.libAbrgUtil = libAbrgUtil;
    }

    public Date getDateCre() {
        return dateCre;
    }

    public void setDateCre(Date dateCre) {
        this.dateCre = dateCre;
    }

    public String getUserCre() {
        return userCre;
    }

    public void setUserCre(String userCre) {
        this.userCre = userCre;
    }

    public Date getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(Date dateMaj) {
        this.dateMaj = dateMaj;
    }

    public String getUserMaj() {
        return userMaj;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public String getLibFeuillet() {
        return libFeuillet;
    }

    public void setLibFeuillet(String libFeuillet) {
        this.libFeuillet = libFeuillet;
    }
    
    public SareftjLibutil() {  }

    public SareftjLibutil(String cdeUtil, String cdeLng, String libUtil, String libAbrgUtil, Date dateCre, String userCre, Date dateMaj, String userMaj, String libFeuillet) {
        this.cdeUtil = cdeUtil;
        this.cdeLng = cdeLng;
        this.libUtil = libUtil;
        this.libAbrgUtil = libAbrgUtil;
        this.dateCre = dateCre;
        this.userCre = userCre;
        this.dateMaj = dateMaj;
        this.userMaj = userMaj;
        this.libFeuillet = libFeuillet;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        SareftjLibutil that = (SareftjLibutil) o;
        
        if (cdeLng != null ? !cdeLng.equals(that.cdeLng) : that.cdeLng != null) return false;
        if (cdeUtil != null ? !cdeUtil.equals(that.cdeUtil) : that.cdeUtil != null) return false;
        if (libUtil != null ? !libUtil.equals(that.libUtil) : that.libUtil != null) return false;
        if (libAbrgUtil != null ? !libAbrgUtil.equals(that.libAbrgUtil) : that.libAbrgUtil != null) return false;
        if (dateCre != null ? !dateCre.equals(that.dateCre) : that.dateCre != null) return false;
        if (userCre != null ? !userCre.equals(that.userCre) : that.userCre != null) return false;
        if (dateMaj != null ? !dateMaj.equals(that.dateMaj) : that.dateMaj != null) return false;
        if (userMaj != null ? !userMaj.equals(that.userMaj) : that.userMaj != null) return false;
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = cdeLng != null ? cdeLng.hashCode() : 0;
        result = 31 * result + (cdeUtil != null ? cdeUtil.hashCode() : 0);
        result = 31 * result + (libUtil != null ? libUtil.hashCode() : 0);
        result = 31 * result + (libAbrgUtil != null ? libAbrgUtil.hashCode() : 0);
        result = 31 * result + (dateCre != null ? dateCre.hashCode() : 0);
        result = 31 * result + (userCre != null ? userCre.hashCode() : 0);
        result = 31 * result + (dateMaj != null ? dateMaj.hashCode() : 0);
        result = 31 * result + (userMaj != null ? userMaj.hashCode() : 0);
        return result;
    }
}
