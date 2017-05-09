package fr.sacem.priam.ui.rest.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benmerzoukah on 27/04/2017.
 */
public class ChargementCritereRecherche {
  private List<Famille> famille = new ArrayList<>(3);
  private List<TypeUtilisation> typeUtilisation = new ArrayList<>(3);
  private List<StatutFichier> statuts = new ArrayList<>();
  
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
  
  public List<StatutFichier> getStatuts() {
    statuts.add(new StatutFichier("CHG_EN_COURS", "En cours"));
    statuts.add(new StatutFichier("CHG_OK", "Chargement OK"));
    statuts.add(new StatutFichier("CHG_KO", "Chargement KO"));
    statuts.add(new StatutFichier("CHG_AFCT", "Affecte"));
    statuts.add(new StatutFichier("CHG_ABDN", "Abondonné"));
    
    return statuts;
  }
  
  public void setStatuts(List<StatutFichier> statuts) {
    this.statuts = statuts;
  }
  
  public void setTypeUtilisation(List<TypeUtilisation> typeUtilisation) {
    this.typeUtilisation = typeUtilisation;
  }
}
