package fr.sacem.priam.model.domain.criteria;

/**
 * Created by jbelwidane on 25/07/2017.
 */
public class LigneProgrammeCriteria {


    private String numProg;
    private String utilisateur;
    private Long ide12;
    private String titre;
    private String ajout;
    private Boolean selection;


    public LigneProgrammeCriteria() {

    }

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }

    public String getUtilisateur() { return utilisateur; }

    public void setUtilisateur(String utilisateur) { this.utilisateur = utilisateur; }

    public Long getIde12() { return ide12; }

    public void setIde12(Long ide12) { this.ide12 = ide12; }

    public String getTitre() { return titre; }

    public void setTitre(String titre) { this.titre = titre; }

    public String getAjout() { return ajout; }

    public void setAjout(String ajout) { this.ajout = ajout; }

    public Boolean getSelection() { return selection; }

    public void setSelection(Boolean selection) { this.selection = selection; }

}
