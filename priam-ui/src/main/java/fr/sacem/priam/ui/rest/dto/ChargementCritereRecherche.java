package fr.sacem.priam.ui.rest.dto;

import fr.sacem.priam.model.domain.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benmerzoukah on 27/04/2017.
 */
public class ChargementCritereRecherche {
  private List<Famille> famille = new ArrayList<>(3);
  private List<TypeUtilisation> typeUtilisation = new ArrayList<>(3);
  private Status[] statuts;
  
  public ChargementCritereRecherche() {
  
  }
  
  
  public List<Famille> getFamille() {
    famille.add(new Famille("COPIPRIV", "Copie Privee"));
    famille.add(new Famille("FDSVAL", "Fonds de valorisation"));
    famille.add(new Famille("CRM", "CRM"));
    
    return famille;
  }
  
  public void setFamille(List<Famille> famille) {
    this.famille = famille;
  }
  
  public List<TypeUtilisation> getTypeUtilisation() {

    typeUtilisation.add(new TypeUtilisation("COPRIVSON", "Copie Privée Sonore"));
    typeUtilisation.add(new TypeUtilisation("CPRIVSONPH", "Copie privée sonore Phono"));
    typeUtilisation.add(new TypeUtilisation("CPRIVSONRD", "Copie Privée Sonore radio"));
    typeUtilisation.add(new TypeUtilisation("CPRIVAUDV", "Copie Privée Audiovisuelle"));
    typeUtilisation.add(new TypeUtilisation("CPRIVAUDPL", "Copie Privée Audiovisuel - Part Littéraire"));
    
    return typeUtilisation;
  }
  
  public Status[] getStatuts() {
    return Status.values();
  }
  
  public void setStatuts(Status[] statuts) {
    this.statuts = statuts;
  }
  
  public void setTypeUtilisation(List<TypeUtilisation> typeUtilisation) {
    this.typeUtilisation = typeUtilisation;
  }
}
