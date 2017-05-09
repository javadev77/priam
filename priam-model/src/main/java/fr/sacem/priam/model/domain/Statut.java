package fr.sacem.priam.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Entity
@Table(name = "PRIAM_RECEPTION_FILE")
public class Statut {
    @Id
    @Column(name = "CODE")
    private String code;
    
    @Column(name = "LIBELLE")
    private String libelle;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
