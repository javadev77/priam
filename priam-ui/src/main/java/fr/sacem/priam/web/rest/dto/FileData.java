package fr.sacem.priam.web.rest.dto;

/**
 * Created by benmerzoukah on 28/04/2017.
 */
public class FileData {
    private String nomFichier;
    private String famille;
    private String typeUtilisation;
    private String dateDebutChgt;
    private String dateFinChgt;
    private Integer nbLignes;
    private String statut;
    
   
  
  public FileData(String nomFichier, String famille, String typeUtilisation, String dateDebutChgt, String dateFinChgt, Integer nbLignes, String statut) {
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
    
    public void setDateDebutChgt(String dateDebutChgt) {
      this.dateDebutChgt = dateDebutChgt;
    }
    
    public void setDateFinChgt(String dateFinChgt) {
      this.dateFinChgt = dateFinChgt;
    }
    
    public void setNbLignes(Integer nbLignes) {
      this.nbLignes = nbLignes;
    }
    
    public void setStatut(String statut) {
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
    
    public String getDateDebutChgt() {
      return dateDebutChgt;
    }
    
    public String getDateFinChgt() {
      return dateFinChgt;
    }
    
    public Integer getNbLignes() {
      return nbLignes;
    }
    
    public String getStatut() {
      return statut;
    }
}
