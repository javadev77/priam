package fr.sacem.priam.model.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.util.SimpleDateSerializer;

import java.util.Date;

/**
 * Created by benmerzoukah on 28/04/2017.
 */
public class ProgrammeDto {
    private String numProg;
    private String nom;
    private String famille;
    private String typeUtilisation;
    private Integer rionTheorique;
    
    @JsonSerialize(using = SimpleDateSerializer.class)
    private Date dateCreation;
    private TypeRepart typeRepart;
    private StatutProgramme statut;
    private Integer rionPaiement;
    private Long fichiers;
    private String usercre;
    private Date datmaj;
    private String usermaj;
    
  
    public ProgrammeDto(String numProg, String nom, Famille famille, TypeUtilisation typeUtilisation, Rion rionTheorique,
                      Date dateCreation, TypeRepart typeRepart, StatutProgramme statut, Rion rionPaiement, Long nbFichiers,
                      String usercre, Date datmaj, String usermaj) {
        this.numProg = numProg;
        this.nom = nom;
        this.famille = famille != null ? famille.getCode() : "";
        this.typeUtilisation = typeUtilisation!=null ? typeUtilisation.getCode() : "";
        this.rionTheorique = rionTheorique!=null ? rionTheorique.getRion() : null;
        this.dateCreation = dateCreation;
        this.typeRepart = typeRepart;
        this.statut = statut;
        this.rionPaiement = rionPaiement != null ? rionPaiement.getRion(): null;
        this.fichiers = nbFichiers;
        this.usercre = usercre;
        this.datmaj = datmaj;
        this.usermaj = usermaj;
    }
    
    public ProgrammeDto(String numProg, String nom, String famille, String typeUtilisation, Integer rionTheorique, Date dateCreation, TypeRepart typeRepart,
                        StatutProgramme statut, Integer rionPaiement, Long fichiers, String usercre, Date datmaj, String usermaj) {
        this.numProg = numProg;
        this.nom = nom;
        this.famille = famille;
        this.typeUtilisation = typeUtilisation;
        this.rionTheorique = rionTheorique;
        this.dateCreation = dateCreation;
        this.typeRepart = typeRepart;
        this.statut = statut;
        this.rionPaiement = rionPaiement;
        this.fichiers = fichiers;
        this.usercre = usercre;
        this.datmaj = datmaj;
        this.usermaj = usermaj;
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
  
  public Date getDateCreation() {
    return dateCreation;
  }
  
  public void setDateCreation(Date dateCreation) {
    this.dateCreation = dateCreation;
  }
  
  public TypeRepart getTypeRepart() {
    return typeRepart;
  }
  
  public void setTypeRepart(TypeRepart typeRepart) {
    this.typeRepart = typeRepart;
  }
  
  public StatutProgramme getStatut() {
    return statut;
  }
  
  public void setStatut(StatutProgramme statut) {
    this.statut = statut;
  }
  
  public Integer getRionPaiement() {
    return rionPaiement;
  }
  
  public void setRionPaiement(Integer rionPaiement) {
    this.rionPaiement = rionPaiement;
  }
    
    public Long getFichiers() {
        return fichiers;
    }
    
    public void setFichiers(Long fichiers) {
        this.fichiers = fichiers;
    }
    
    public String getUsercre() {
        return usercre;
    }
    
    public void setUsercre(String usercre) {
        this.usercre = usercre;
    }
    
    public Date getDatmaj() {
        return datmaj;
    }
    
    public void setDatmaj(Date datmaj) {
        this.datmaj = datmaj;
    }
    
    public String getUsermaj() {
        return usermaj;
    }
    
    public void setUsermaj(String usermaj) {
        this.usermaj = usermaj;
    }
    
    public ProgrammeDto() {
    }
}
