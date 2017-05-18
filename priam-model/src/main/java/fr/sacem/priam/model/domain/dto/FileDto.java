package fr.sacem.priam.model.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.util.CustomDateSerializer;

import java.util.Date;

/**
 * Created by benmerzoukah on 28/04/2017.
 */
public class FileDto {
    private Long id;
    private String nomFichier;
    private String famille;
    private String typeUtilisation;
    
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date dateDebutChgt;
  
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date dateFinChgt;
    
    private Long nbLignes;
    private Status statut;
    
   
  
  public FileDto(Long id, String nomFichier, String famille, String typeUtilisation, Date dateDebutChgt, Date dateFinChgt, Long nbLignes, Status statut) {
    this.id = id;
    this.nomFichier = nomFichier;
    this.famille = famille;
    this.typeUtilisation = typeUtilisation;
    this.dateDebutChgt = dateDebutChgt;
    this.dateFinChgt = dateFinChgt;
    this.nbLignes = nbLignes;
    this.statut = statut;
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
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
    
    public void setNbLignes(Long nbLignes) {
      this.nbLignes = nbLignes;
    }
    
    public void setStatut(Status statut) {
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
    
    public Long getNbLignes() {
      return nbLignes;
    }
    
    public Status getStatut() {
      return statut;
    }
}
