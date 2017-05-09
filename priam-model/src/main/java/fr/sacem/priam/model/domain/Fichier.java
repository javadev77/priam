package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Entity
@Table(name = "PRIAM_RECEPTION_FILE")
public class Fichier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOM")
    private String nom;
    
    @Column(name = "FAMILLE")
    private String famille;
    
    @Column(name = "TYPE_UTIL")
    private String typeUtilisation;
    
    @Column(name = "DATE_DEBUT_CHGT")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate dateDebutChargt;
    
    @Column(name = "DATE_FIN_CHGT")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate dateFinChargt;
    
    @Column(name = "NB_LIGNES")
    private Long nbLignes;
    
    @Column(name = "STATUT_CODE")
    private Statut statut;
    
    public Long getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getFamille() {
        return famille;
    }
    
    public String getTypeUtilisation() {
        return typeUtilisation;
    }
    
    public LocalDate getDateDebutChargt() {
        return dateDebutChargt;
    }
    
    public LocalDate getDateFinChargt() {
        return dateFinChargt;
    }
    
    public Long getNbLignes() {
        return nbLignes;
    }
    
    public Statut getStatut() {
        return statut;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setFamille(String famille) {
        this.famille = famille;
    }
    
    public void setTypeUtilisation(String typeUtilisation) {
        this.typeUtilisation = typeUtilisation;
    }
    
    public void setDateDebutChargt(LocalDate dateDebutChargt) {
        this.dateDebutChargt = dateDebutChargt;
    }
    
    public void setDateFinChargt(LocalDate dateFinChargt) {
        this.dateFinChargt = dateFinChargt;
    }
    
    public void setNbLignes(Long nbLignes) {
        this.nbLignes = nbLignes;
    }
    
    public void setStatut(Statut statut) {
        this.statut = statut;
    }
    
    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Fichier that = (Fichier) o;
        
        return id == that.id;
    }
}
