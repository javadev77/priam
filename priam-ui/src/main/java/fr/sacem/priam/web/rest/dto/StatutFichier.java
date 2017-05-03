package fr.sacem.priam.web.rest.dto;

/**
 * Created by benmerzoukah on 28/04/2017.
 */
public class StatutFichier {
  
  private String code;
  private String libelle;
  
  public StatutFichier(String code, String libelle) {
    this.code = code;
    this.libelle = libelle;
  }
  
  public String getLibelle() {
    return libelle;
  }
  
  public String getCode() {
    return code;
  }
  
  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
}
