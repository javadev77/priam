package fr.sacem.priam.model.domain.saref;

import javax.persistence.*;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Entity
@Table(name = "SAREFTJ_LIBFAMILTYPUTIL")
@IdClass(SareftjLibfamiltyputilPK.class)
public class SareftjLibfamiltyputil {
    
    private String code;
    private String libelle;
    private String lang;
    private SareftrFamiltyputil sareftrFamiltyputil;
    /*
    private String cdelng;
    private String cdefamiltyputil;
    private String libfamiltyputil;
    private String libabrgfamiltyputil;
    private java.sql.Timestamp datcre;
    private String usercre;
    private java.sql.Timestamp datmaj;
    private String usermaj;
    */
    @Id
    @Column(name = "CDEFAMILTYPUTIL")
    public String getCode() {
        return code;
    }
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = SareftrFamiltyputil.class)
    @JoinColumn(name = "CDEFAMILTYPUTIL", insertable = false, updatable = false)
    public SareftrFamiltyputil getSareftrFamiltyputil() {
        return sareftrFamiltyputil;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name = "LIBABRGFAMILTYPUTIL")
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
    
    public void setSareftrFamiltyputil(SareftrFamiltyputil sareftrFamiltyputil) {
        this.sareftrFamiltyputil = sareftrFamiltyputil;
    }
}
