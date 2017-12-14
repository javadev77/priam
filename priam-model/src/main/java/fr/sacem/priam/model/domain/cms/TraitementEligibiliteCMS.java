package fr.sacem.priam.model.domain.cms;

import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutEligibilite;
import fr.sacem.priam.model.domain.StatutFichierFelix;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by benmerzoukah on 14/12/2017.
 */
@Entity
@Table(name = "PRIAM_TRAITEMENT_ELIGIBILITE_CMS")
public class TraitementEligibiliteCMS implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "NUMPROG")
    private Programme programme;

    @Column(name = "DATE_DEBUT_TMT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebutTmt;

    @Column(name = "DATE_FIN_TMT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFinTmt;

    @Column(name = "STATUT_ELIGIBILITE")
    @Enumerated(EnumType.STRING)
    private StatutEligibilite statutEligibilite;

    @Column(name = "NB_OEUVRES_CATALOGUE")
    private Long nbOeuvresCatalogue;

    @Column(name = "NB_OEUVRES_RETENUES")
    private Long nbOeuvresRetenues;

    @Column(name = "NB_OEUVRES_EXTRACT")
    private Long nbOeuvresExtraction;


    @Column(name = "SOMME_POINTS")
    private Double sommePoints;

    public TraitementEligibiliteCMS() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public Date getDateDebutTmt() {
        return dateDebutTmt;
    }

    public void setDateDebutTmt(Date dateDebutTmt) {
        this.dateDebutTmt = dateDebutTmt;
    }

    public Date getDateFinTmt() {
        return dateFinTmt;
    }

    public void setDateFinTmt(Date dateFinTmt) {
        this.dateFinTmt = dateFinTmt;
    }

    public StatutEligibilite getStatutEligibilite() {
        return statutEligibilite;
    }

    public void setStatutEligibilite(StatutEligibilite statutEligibilite) {
        this.statutEligibilite = statutEligibilite;
    }

    public Long getNbOeuvresCatalogue() {
        return nbOeuvresCatalogue;
    }

    public void setNbOeuvresCatalogue(Long nbOeuvresCatalogue) {
        this.nbOeuvresCatalogue = nbOeuvresCatalogue;
    }


    public Long getNbOeuvresRetenues() {
        return nbOeuvresRetenues;
    }

    public void setNbOeuvresRetenues(Long nbOeuvresRetenues) {
        this.nbOeuvresRetenues = nbOeuvresRetenues;
    }

    public Long getNbOeuvresExtraction() {
        return nbOeuvresExtraction;
    }

    public void setNbOeuvresExtraction(Long nbOeuvresExtraction) {
        this.nbOeuvresExtraction = nbOeuvresExtraction;
    }

    public Double getSommePoints() {
        return sommePoints;
    }

    public void setSommePoints(Double sommePoints) {
        this.sommePoints = sommePoints;
    }

}
