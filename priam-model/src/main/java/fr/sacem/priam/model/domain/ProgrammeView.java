package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by benmerzoukah on 06/06/2017.
 */
@Entity
@Table(name = "PRIAM_PROG_VIEW")
public class ProgrammeView {
    private String numProg;
    private String nom;
    private Integer rionTheorique;
    private String famille;
    private String typeUtilisation;
    private TypeRepart typeRepart;
    private Date dateCreation;
    private StatutProgramme statut;
    private Integer rionPaiement;
    private Long fichiers;
    
    public ProgrammeView() {
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
    
    @Column(name = "RION_THEORIQUE")
    public Integer getRionTheorique() {
        return rionTheorique;
    }
    
    @Column(name = "CDEFAMILTYPUTIL")
    public String getFamille() {
        return famille;
    }
    
    @Column(name = "CDETYPUTIL")
    public String getTypeUtilisation() {
        return typeUtilisation;
    }
    
    @Column(name = "TYPE_REPART")
    @Enumerated(EnumType.STRING)
    public TypeRepart getTypeRepart() {
        return typeRepart;
    }
    
    @Column(name = "DATE_CREATION")
    @Temporal(TemporalType.DATE)
    public Date getDateCreation() {
        return dateCreation;
    }
    
    @Column(name = "STATUT_PROG_CODE")
    @Enumerated(EnumType.STRING)
    public StatutProgramme getStatut() {
        return statut;
    }
    
    @Column(name = "RION_PAIEMENT")
    public Integer getRionPaiement() {
        return rionPaiement;
    }
    
    @Column(name = "fichiers")
    public Long getFichiers() {
        return fichiers;
    }
    
    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setRionTheorique(Integer rionTheorique) {
        this.rionTheorique = rionTheorique;
    }
    
    public void setFamille(String famille) {
        this.famille = famille;
    }
    
    public void setTypeUtilisation(String typeUtilisation) {
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
    
    public void setRionPaiement(Integer rionPaiement) {
        this.rionPaiement = rionPaiement;
    }
    
    public void setFichiers(Long fichiers) {
        this.fichiers = fichiers;
    }
}
