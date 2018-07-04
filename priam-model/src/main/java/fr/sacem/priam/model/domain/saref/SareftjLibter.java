package fr.sacem.priam.model.domain.saref;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by belwidanej on 21/08/2017.
 */
@Entity
@Table(name="SAREFTJ_LIBTER")
@IdClass(SareftjLibterPK.class)
public class SareftjLibter implements Serializable {

    @Id
    @Column(name = "CDELNG")
    private String cdeLng;
    @Id
    @Column(name = "CDEPAYSISO4N")
    private Integer cdePaysIso4N;
    @Column(name = "NOMPAYSABR")
    private String nomPaysAbr;
    @Column(name = "NOMPAYS")
    private String nomPays;
    @Column(name = "DATCRE")
    private Date dateCre;
    @Column(name = "USERCRE")
    private String userCre;
    @Column(name = "DATMAJ")
    private Date dateMaj;
    @Column(name = "USERMAJ")
    private String userMaj;

    public String getCdeLng() { return cdeLng; }

    public void setCdeLng(String cdeLng) { this.cdeLng = cdeLng; }

    public Integer getCdePaysIso4N() { return cdePaysIso4N; }

    public void setCdePaysIso4N(Integer cdePaysIso4N) { this.cdePaysIso4N = cdePaysIso4N; }

    public String getNomPaysAbr() { return nomPaysAbr; }

    public void setNomPaysAbr(String nomPaysAbr) { this.nomPaysAbr = nomPaysAbr; }

    public String getNomPays() { return nomPays; }

    public void setNomPays(String nomPays) { this.nomPays = nomPays; }

    public Date getDateCre() { return dateCre; }

    public void setDateCre(Date dateCre) { this.dateCre = dateCre; }

    public String getUserCre() { return userCre; }

    public void setUserCre(String userCre) { this.userCre = userCre; }

    public Date getDateMaj() { return dateMaj; }

    public void setDateMaj(Date dateMaj) { this.dateMaj = dateMaj; }

    public String getUserMaj() { return userMaj; }

    public void setUserMaj(String userMaj) { this.userMaj = userMaj; }

    public SareftjLibter() { }

    public SareftjLibter(String cdeLng, Integer cdePaysIso4N, String nomPaysAbr, String nomPays, Date dateCre, String userCre, Date dateMaj, String userMaj) {
        this.cdeLng = cdeLng;
        this.cdePaysIso4N = cdePaysIso4N;
        this.nomPaysAbr = nomPaysAbr;
        this.nomPays = nomPays;
        this.dateCre = dateCre;
        this.userCre = userCre;
        this.dateMaj = dateMaj;
        this.userMaj = userMaj;
    }
}
