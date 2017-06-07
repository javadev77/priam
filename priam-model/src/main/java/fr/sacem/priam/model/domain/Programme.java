package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by benmerzoukah on 06/06/2017.
 */
@Entity
@Table(name = "PRIAM_PROGRAMME")
public class Programme {
    private String numProg;
    private String nom;
    private Rion rionTheorique;
    private Famille famille;
    private TypeUtilisation typeUtilisation;
    private TypeRepart typeRepart;
    private Date dateCreation;
    private StatutProgramme statut;
    private Rion rionPaiement;
    private List<Fichier> fichiers;
    
    public Programme() {
    }
    
    @Id
    @Column(name = "NUMPROG")
    public String getNumProg() {
        return numProg;
    }
    
    @Column(name = "NOM")
    public String getNom() {
        return nom;
    }
    
    @ManyToOne
    @JoinColumn(name = "RION_THEORIQUE")
    public Rion getRionTheorique() {
        return rionTheorique;
    }
    
    @ManyToOne
    @JoinColumn(name = "CDEFAMILTYPUTIL")
    public Famille getFamille() {
        return famille;
    }
    
    @ManyToOne
    @JoinColumn(name = "CDETYPUTIL")
    public TypeUtilisation getTypeUtilisation() {
        return typeUtilisation;
    }
    
    @Column(name = "TYPE_REPART")
    @Enumerated(EnumType.STRING)
    public TypeRepart getTypeRepart() {
        return typeRepart;
    }
    
    @Column(name = "DATE_CREATION")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateCreation() {
        return dateCreation;
    }
    
    @Column(name = "STATUT_PROG_CODE")
    @Enumerated(EnumType.STRING)
    public StatutProgramme getStatut() {
        return statut;
    }
    
    @ManyToOne
    @JoinColumn(name = "RION_PAIEMENT")
    public Rion getRionPaiement() {
        return rionPaiement;
    }
    
    @OneToMany(mappedBy = "programme")
    public List<Fichier> getFichiers() {
        return fichiers;
    }
    
    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setRionTheorique(Rion rionTheorique) {
        this.rionTheorique = rionTheorique;
    }
    
    public void setFamille(Famille famille) {
        this.famille = famille;
    }
    
    public void setTypeUtilisation(TypeUtilisation typeUtilisation) {
        this.typeUtilisation = typeUtilisation;
    }
    
    public void setTypeRepart(TypeRepart typeRepart) {
        this.typeRepart = typeRepart;
    }
    
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public void setStatut(StatutProgramme statut) {
        this.statut = statut;
    }
    
    public void setRionPaiement(Rion rionPaiement) {
        this.rionPaiement = rionPaiement;
    }
    
    public void setFichiers(List<Fichier> fichiers) {
        this.fichiers = fichiers;
    }
}
