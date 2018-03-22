package fr.sacem.priam.model.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;
import fr.sacem.priam.model.util.SimpleDateSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JournalDto {

    private String numProg;
    private String evenement;
    private Long ide12;

    @JsonSerialize(using = SimpleDateSerializer.class)
    private Date date;

    private String utilisateur;

    private List<SituationAvant> listSituationAvant = new ArrayList<>();
    private List<SituationApres> listSituationApres = new ArrayList<>();

    public JournalDto(String numProg, String evenement, Long ide12, Date date, String utilisateur) {
        this(numProg, evenement, ide12, date, utilisateur, null, null);

    }

    public JournalDto(String numProg, String evenement, Long ide12, Date date, String utilisateur, List<SituationAvant> listSituationAvant, List<SituationApres> listSituationApres) {
        this.numProg = numProg;
        this.evenement = evenement;
        this.ide12 = ide12;
        this.date = date;
        this.utilisateur = utilisateur;
        this.listSituationAvant = listSituationAvant;
        this.listSituationApres = listSituationApres;
    }

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }

    public String getEvenement() {
        return evenement;
    }

    public void setEvenement(String evenement) {
        this.evenement = evenement;
    }

    public Long getIde12() {
        return ide12;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<SituationAvant> getListSituationAvant() {
        return listSituationAvant;
    }

    public void setListSituationAvant(List<SituationAvant> listSituationAvant) {
        this.listSituationAvant = listSituationAvant;
    }

    public List<SituationApres> getListSituationApres() {
        return listSituationApres;
    }

    public void setListSituationApres(List<SituationApres> listSituationApres) {
        this.listSituationApres = listSituationApres;
    }
}
