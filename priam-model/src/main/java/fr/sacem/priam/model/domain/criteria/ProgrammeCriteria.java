package fr.sacem.priam.model.domain.criteria;

import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.TypeRepart;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by benmerzoukah on 12/06/2017.
 */
public class ProgrammeCriteria {
    private String numProg;
    private String nom;
    private String sareftrFamiltyputil;
    private String typeUtilisation;
    private Integer sareftrRionTheorique;
    private Date dateCreationDebut;
    private Date dateCreationFin;
    private TypeRepart typeRepart;
    private List<StatutProgramme> statut = Arrays.asList(StatutProgramme.values());
    private Integer sareftrRionPaiement;
    
    public ProgrammeCriteria() {
    
    }
    
    public String getNumProg() {
        return numProg;
    }
    
    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getSareftrFamiltyputil() {
        return sareftrFamiltyputil;
    }
    
    public void setSareftrFamiltyputil(String sareftrFamiltyputil) {
        this.sareftrFamiltyputil = sareftrFamiltyputil;
    }
    
    public String getTypeUtilisation() {
        return typeUtilisation;
    }
    
    public void setTypeUtilisation(String typeUtilisation) {
        this.typeUtilisation = typeUtilisation;
    }
    
    public Integer getSareftrRionTheorique() {
        return sareftrRionTheorique;
    }
    
    public void setSareftrRionTheorique(Integer sareftrRionTheorique) {
        this.sareftrRionTheorique = sareftrRionTheorique;
    }
    
    public Date getDateCreationDebut() {
        return dateCreationDebut;
    }
    
    public void setDateCreationDebut(Date dateCreationDebut) {
        this.dateCreationDebut = dateCreationDebut;
    }
    
    public Date getDateCreationFin() {
        return dateCreationFin;
    }
    
    public void setDateCreationFin(Date dateCreationFin) {
        this.dateCreationFin = dateCreationFin;
    }
    
    public TypeRepart getTypeRepart() {
        return typeRepart;
    }
    
    public void setTypeRepart(TypeRepart typeRepart) {
        this.typeRepart = typeRepart;
    }
    
    public List<StatutProgramme> getStatut() {
        return statut;
    }
    
    public void setStatut(List<StatutProgramme> statut) {
        this.statut = statut;
    }
    
    public Integer getSareftrRionPaiement() {
        return sareftrRionPaiement;
    }
    
    public void setSareftrRionPaiement(Integer sareftrRionPaiement) {
        this.sareftrRionPaiement = sareftrRionPaiement;
    }
}
