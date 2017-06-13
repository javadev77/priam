package fr.sacem.priam.ui.rest.dto;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.List;

/**
 * Created by benmerzoukah on 12/06/2017.
 */
public class ProgrammeCritereRecherche {
    private String numProg;
    private String famille;
    private String typeUtilisation;
    private List<String> statutCode;
    private String nom;
    private String rionTheorique;
    private String rionPaiement;
    private String typeRepart;
    private Date dateCreationDebut;
    private Date dateCreationFin;
  
    ProgrammeCritereRecherche() {
    
    }
  
    public String getNumProg() {
      return numProg;
    }
    
    public void setNumProg(String numProg) {
      this.numProg = numProg;
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
    
    public List<String> getStatutCode() {
      return statutCode;
    }
    
    public void setStatutCode(List<String> statutCode) {
      this.statutCode = statutCode;
    }
    
    public String getNom() {
      return nom;
    }
    
    public void setNom(String nom) {
      this.nom = nom;
    }
    
    public String getRionTheorique() {
      return rionTheorique;
    }
    
    public void setRionTheorique(String rionTheorique) {
      this.rionTheorique = rionTheorique;
    }
    
    public Date getDateCreationDebut() {
      return dateCreationDebut;
    }
    
    public void setDateCreationDebut(Date dateCreationDebut) {
    this.dateCreationDebut = dateCreationDebut;
  }
    
    public String getRionPaiement() {
      return rionPaiement;
    }
    
    public void setRionPaiement(String rionPaiement) {
      this.rionPaiement = rionPaiement;
    }
    
    public String getTypeRepart() {
      return typeRepart;
    }
    
    public void setTypeRepart(String typeRepart) {
      this.typeRepart = typeRepart;
    }
    
    public Date getDateCreationFin() {
      return dateCreationFin;
    }
    
    public void setDateCreationFin(Date dateCreationFin) {
      this.dateCreationFin = dateCreationFin;
    }
  
    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this).toString();
    }
}
