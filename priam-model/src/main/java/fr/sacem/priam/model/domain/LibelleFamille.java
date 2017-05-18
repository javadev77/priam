package fr.sacem.priam.model.domain;

import javax.persistence.*;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Entity
@Table(name = "PRIAM_LIBFAMITYPUTIL")
@IdClass(LibelleFamillePK.class)
public class LibelleFamille {
    
    private String code;
    private String libelle;
    private String lang;
    
    @Id
    @Column(name = "CODE")
    public String getCode() {
        return code;
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
}
