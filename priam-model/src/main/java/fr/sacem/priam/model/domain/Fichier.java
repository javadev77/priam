package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Entity
@Table(name = "PRIAM_FICHIER")
public class Fichier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOM")
    private String nomFichier;
    
    @ManyToOne
    @JoinColumn(name = "CDEFAMILTYPUTIL")
    private Famille famille;
    
    @ManyToOne
    @JoinColumn(name = "CDETYPUTIL")
    private TypeUtilisation typeUtilisation;
    
    @Column(name = "DATE_DEBUT_CHGT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebutChargt;
    
    @Column(name = "DATE_FIN_CHGT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFinChargt;
    
    @Column(name = "NB_LIGNES")
    private Long nbLignes;
    
    @Enumerated(EnumType.STRING)
    @Column(name="STATUT_CODE", nullable = false)
    private Status statut;
    
    public  Fichier() {
    }
    
    public Long getId() {
        return id;
    }
    
    public String getNomFichier() {
        return nomFichier;
    }
    
    public Famille getFamille() {
        return famille;
    }
    
    public TypeUtilisation getTypeUtilisation() {
        return typeUtilisation;
    }
    
    public Date getDateDebutChargt() {
        return dateDebutChargt;
    }
    
    public Date getDateFinChargt() {
        return dateFinChargt;
    }
    
    public Long getNbLignes() {
        return nbLignes;
    }
    
    public Status getStatut() {
        return statut;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }
    
    public void setFamille(Famille famille) {
        this.famille = famille;
    }
    
    public void setTypeUtilisation(TypeUtilisation typeUtilisation) {
        this.typeUtilisation = typeUtilisation;
    }
    
    public void setDateDebutChargt(Date dateDebutChargt) {
        this.dateDebutChargt = dateDebutChargt;
    }
    
    public void setDateFinChargt(Date dateFinChargt) {
        this.dateFinChargt = dateFinChargt;
    }
    
    public void setNbLignes(Long nbLignes) {
        this.nbLignes = nbLignes;
    }
    
    public void setStatut(Status statut) {
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