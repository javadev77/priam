package fr.sacem.priam.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fandis on 04/08/2017.
 */
@Entity
@Table(name="SAREFTJ_LIBUTIL")
public class LibelleUtilisateur implements Serializable {

    @Id
    @Column(name = "CDEUTIL")
    private String cdeUtil;
    @Column(name = "CDELNG")
    private String cdeLng;
    @Column(name = "LIBUTIL")
    private String libUtil;
    @Column(name = "LIBABRGUTIL")
    private String libAbrgUtil;
    @Column(name = "DATECRE")
    private Date dateCre;
    @Column(name = "USERCRE")
    private String userCre;
    @Column(name = "DATEMAJ")
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

    public LibelleUtilisateur(String cdeUtil, String cdeLng, String libUtil, String libAbrgUtil, Date dateCre, String userCre, Date dateMaj, String userMaj, String libFeuillet) {
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
}
