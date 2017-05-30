package fr.sacem.priam.model.domain;

import javax.persistence.*;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Entity
@Table(name = "PRIAM_LIBTYPUTIL")
@IdClass(LibelleTypeUtilisationPK.class)
public class LibelleTypeUtilisation {
    
    private String code;
    private String libelle;
    private String lang;
    
    private TypeUtilisation typeUtilisation;
    
    @Id
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = TypeUtilisation.class)
    @JoinColumn(name = "CODE", insertable = false, updatable = false)
    public TypeUtilisation getTypeUtilisation() {
        return typeUtilisation;
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
    
    public void setTypeUtilisation(TypeUtilisation typeUtilisation) {
        this.typeUtilisation = typeUtilisation;
    }
}
