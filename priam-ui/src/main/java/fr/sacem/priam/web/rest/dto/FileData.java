package fr.sacem.priam.web.rest.dto;

import java.util.Date;

/**
 * Created by benmerzoukah on 28/04/2017.
 */
public class FileData {
    private String nomFichier;
    private String famille;
    private String typeUtilisation;
    private Date dateDebutChgt;
    private Date dateFinChgt;
    private Integer nbLignes;
    private StatutFichier statut;
    
   
  
  public FileData(String nomFichier, String famille, String typeUtilisation, Date dateDebutChgt, Date dateFinChgt, Integer nbLignes, StatutFichier statut) {
    this.nomFichier = nomFichier;
    this.famille = famille;
    this.typeUtilisation = typeUtilisation;
    this.dateDebutChgt = dateDebutChgt;
    this.dateFinChgt = dateFinChgt;
    this.nbLignes = nbLignes;
    this.statut = statut;
  }
  
  public void setNomFichier(String nomFichier) {
      this.nomFichier = nomFichier;
    }
    
    public void setFamille(String famille) {
      this.famille = famille;
    }
    
    public void setTypeUtilisation(String typeUtilisation) {
      this.typeUtilisation = typeUtilisation;
    }
    
    public void setDateDebutChgt(Date dateDebutChgt) {
      this.dateDebutChgt = dateDebutChgt;
    }
    
    public void setDateFinChgt(Date dateFinChgt) {
      this.dateFinChgt = dateFinChgt;
    }
    
    public void setNbLignes(Integer nbLignes) {
      this.nbLignes = nbLignes;
    }
    
    public void setStatut(StatutFichier statut) {
      this.statut = statut;
    }
    
    public String getNomFichier() {
      
      return nomFichier;
    }
    
    public String getFamille() {
      return famille;
    }
    
    public String getTypeUtilisation() {
      return typeUtilisation;
    }
    
    public Date getDateDebutChgt() {
      return dateDebutChgt;
    }
    
    public Date getDateFinChgt() {
      return dateFinChgt;
    }
    
    public Integer getNbLignes() {
      return nbLignes;
    }
    
    public StatutFichier getStatut() {
      return statut;
    }
}
