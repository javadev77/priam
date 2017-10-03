package fr.sacem.priam.model.domain.saref;

import javax.persistence.*;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Entity
@Table(name = "SAREFFT_LIBTYPUTIL")
@IdClass(SareftjLibtyputilPK.class)
public class SareftjLibtyputil {
    
    private String code;
    private String libelle;
    private String lang;
    /*
  private String cdelng;
  private String cdetyputil;
  private String libtyputil;
  private String libabrgtyputil;
  private java.sql.Timestamp datcre;
  private String usercre;
  private java.sql.Timestamp datmaj;
  private String usermaj;
  private String libfeuiltyputil;
     */

    private SareftrTyputil sareftrTyputil;
    
    @Id
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = SareftrTyputil.class)
    @JoinColumn(name = "CODE", insertable = false, updatable = false)
    public SareftrTyputil getSareftrTyputil() {
        return sareftrTyputil;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name = "LIBELLE")
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    @Id
    @Column(name = "CDELNG")
    public String getLang() {
        return lang;
    }
    
    public void setLang(String cdelng) {
        this.lang = cdelng;
    }
    
    public void setSareftrTyputil(SareftrTyputil sareftrTyputil) {
        this.sareftrTyputil = sareftrTyputil;
    }
}
