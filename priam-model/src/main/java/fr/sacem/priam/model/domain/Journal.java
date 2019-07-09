package fr.sacem.priam.model.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.sacem.priam.model.util.CustomDateSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by monfleurm on 22/02/2018.
 */
@Entity
@Table(name = "PRIAM_JOURNAL_EVENEMENT")
public class Journal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numprog")
    private String numProg;

    @Column(name = "evenement")
    private String evenement;

    @Column(name = "ide12")
    private Long ide12;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;

    @Column(name = "utilisateur")
    private String utilisateur;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_EVENEMENT")
    List<SituationAvant> listSituationAvant = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_EVENEMENT")
    List<SituationApres> listSituationApres = new ArrayList<>();

    public Journal() {
    }

    public Journal(String numProg, String evenement, Long ide12, Date date, String utilisateur, List<SituationAvant> listSituationAvant, List<SituationApres> listSituationApres) {
        this.numProg = numProg;
        this.evenement = evenement;
        this.ide12 = ide12;
        this.date = date;
        this.utilisateur = utilisateur;
        this.listSituationAvant = listSituationAvant;
        this.listSituationApres = listSituationApres;
    }

    public Journal(String numProg, String evenement, Date date, String utilisateurMaj) {
        this.numProg = numProg;
        this.evenement = evenement;
        this.date = date;
        this.utilisateur = utilisateurMaj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getEvenement() { return evenement; }

    public void setEvenement(String evenement) { this.evenement = evenement; }

    public Long getIde12() { return ide12; }

    public void setIde12(Long ide12) { this.ide12 = ide12; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String getUtilisateur() { return utilisateur; }

    public void setUtilisateur(String utilisateur) { this.utilisateur = utilisateur; }

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Num Programme", numProg)
                .append("evenement", evenement)
                .append("utilisateur", utilisateur)
                .append("date", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date))
                .toString();
    }
}
