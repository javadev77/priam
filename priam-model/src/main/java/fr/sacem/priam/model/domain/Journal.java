package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @Column(name = "evenement")
    private String evenement;

    @Column(name = "ide12")
    private Long ide12;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "utilisateur")
    private String utilisateur;

    @Column(name = "situationAvant")
    private String situationAvant;

    @Column(name = "situationApres")
    private String situationApres;

    public Journal(String evenement, Long ide12, Date date, String utilisateur, String situationAvant, String situationApres) {
        this.evenement = evenement;
        this.ide12 = ide12;
        this.date = date;
        this.utilisateur = utilisateur;
        this.situationAvant = situationAvant;
        this.situationApres = situationApres;
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

    public String getSituationAvant() { return situationAvant; }

    public void setSituationAvant(String situationAvant) { this.situationAvant = situationAvant; }

    public void setSituationApres(String situationApres) { this.situationApres = situationApres; }

    public String getSituationApres() { return situationApres; }

}
