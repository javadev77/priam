package fr.sacem.priam.ui.rest.dto;

/**
 * Created by benmerzoukah on 28/04/2017.
 */
public class Famille {
  private String code;
  private String libelle;
  
  public Famille(String code, String libelle) {
    this.code = code;
    this.libelle = libelle;
  }
  
  
  public String getCode() {
    return code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public String getLibelle() {
    return libelle;
  }
  
  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }
}
