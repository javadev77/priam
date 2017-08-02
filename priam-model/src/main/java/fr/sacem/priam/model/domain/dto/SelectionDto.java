package fr.sacem.priam.model.domain.dto;

/**
 * Created by fandis on 19/07/2017.
 */
public class SelectionDto {
  private Long ide12;
  private String titreOeuvre;
  private String roleParticipant1;
  private String nomParticipant1;
  private Long durDif;
  private String ajout;
  private Long quantite;
  private String utilisateur;
  private boolean selection;
  private Long id;
  public String getUtilisateur() {
    return utilisateur;
  }

  public void setUtilisateur(String utilisateur) {
    this.utilisateur = utilisateur;
  }

  public Long getIde12() {
    return ide12;
  }

  public void setIde12(Long ide12) {
    this.ide12 = ide12;
  }

  public String getTitreOeuvre() {
    return titreOeuvre;
  }

  public void setTitreOeuvre(String titreOeuvre) {
    this.titreOeuvre = titreOeuvre;
  }

  public String getRoleParticipant1() {
    return roleParticipant1;
  }

  public void setRoleParticipant1(String roleParticipant1) {
    this.roleParticipant1 = roleParticipant1;
  }

  public String getNomParticipant1() {
    return nomParticipant1;
  }

  public void setNomParticipant1(String nomParticipant1) {
    this.nomParticipant1 = nomParticipant1;
  }

  public Long getDurDif() {
    return durDif;
  }

  public void setDurDif(Long durDif) {
    this.durDif = durDif;
  }

  public String getAjout() {
    return ajout;
  }

  public void setAjout(String ajout) {
    this.ajout = ajout;
  }

  public Long getQuantite() {
    return quantite;
  }

  public void setQuantite(Long quantite) {
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

  public SelectionDto(Long ide12, String titreOeuvre, String roleParticipant1, String nomParticipant1,String ajout,Long durDif, Long quantite, String utilisateur, boolean selection, Long id) {
    this.ide12 = ide12;
    this.titreOeuvre = titreOeuvre;
    this.roleParticipant1 = roleParticipant1;
    this.nomParticipant1 = nomParticipant1;
    this.durDif = durDif;
    this.ajout = ajout;
    this.quantite = quantite;
    this.utilisateur = utilisateur;
    this.selection = selection;
    this.id = id;
  }

}
