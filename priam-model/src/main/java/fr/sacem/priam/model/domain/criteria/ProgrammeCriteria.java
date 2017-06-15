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
    private String famille;
    private String typeUtilisation;
    private Integer rionTheorique;
    private Date dateCreationDebut;
    private Date dateCreationFin;
    private TypeRepart typeRepart;
    private List<StatutProgramme> statut = Arrays.asList(StatutProgramme.values());
    private Integer rionPaiement;
    
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
    
    public String getFamille() {
        return famille;
    }
    
    public void setFamille(String famille) {
        this.famille = famille;
    }
    
    public String getTypeUtilisation() {
        return typeUtilisation;
    }
    
    public void setTypeUtilisation(String typeUtilisation) {
        this.typeUtilisation = typeUtilisation;
    }
    
    public Integer getRionTheorique() {
        return rionTheorique;
    }
    
    public void setRionTheorique(Integer rionTheorique) {
        this.rionTheorique = rionTheorique;
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
    
    public Integer getRionPaiement() {
        return rionPaiement;
    }
    
    public void setRionPaiement(Integer rionPaiement) {
        this.rionPaiement = rionPaiement;
    }
}
