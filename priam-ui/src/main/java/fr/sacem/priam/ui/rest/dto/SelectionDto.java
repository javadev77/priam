package fr.sacem.priam.ui.rest.dto;

/**
 * Created by fandis on 19/07/2017.
 */
public class SelectionDto {
  private Long ide12;
  private String titre;
  private String role;
  private String participant;
  private Long duree;
  private String ajout;
  private String quantite;
  private boolean selection;
  private Long id;

  public Long getIde12() {
    return ide12;
  }

  public void setIde12(Long ide12) {
    this.ide12 = ide12;
  }

  public String getTitre() {
    return titre;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getParticipant() {
    return participant;
  }

  public void setParticipant(String participant) {
    this.participant = participant;
  }

  public Long getDuree() {
    return duree;
  }

  public void setDuree(Long duree) {
    this.duree = duree;
  }

  public String getAjout() {
    return ajout;
  }

  public void setAjout(String ajout) {
    this.ajout = ajout;
  }

  public String getQuantite() {
    return quantite;
  }

  public void setQuantite(String quantite) {
    this.quantite = quantite;
  }

  public boolean getSelection() {
    return selection;
  }

  public void setSelection(boolean selection) { this.selection = selection; }

  public Long getId() { return id; }

  public void setId(Long id) { this.id = id; }

  public SelectionDto() {
  }
}
