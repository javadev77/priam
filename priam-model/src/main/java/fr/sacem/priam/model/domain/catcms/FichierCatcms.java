package fr.sacem.priam.model.domain.catcms;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PRIAM_CATCMS_FICHIER")
public class FichierCatcms implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOM")
    private String nomFichier;

    @Column(name = "DATE_DEBUT_CHGT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebutChargt;

    @Column(name = "DATE_FIN_CHGT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFinChargt;

    @Column(name = "NB_LIGNES")
    private Long nbLignes;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUT_CODE", nullable = true)
    private StatusCatcms statutCatcms;

    @Column(name = "TYPE_FICHIER")
    private String typeFichier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public Date getDateDebutChargt() {
        return dateDebutChargt;
    }

    public void setDateDebutChargt(Date dateDebutChargt) {
        this.dateDebutChargt = dateDebutChargt;
    }

    public Date getDateFinChargt() {
        return dateFinChargt;
    }

    public void setDateFinChargt(Date dateFinChargt) {
        this.dateFinChargt = dateFinChargt;
    }

    public Long getNbLignes() {
        return nbLignes;
    }

    public void setNbLignes(Long nbLignes) {
        this.nbLignes = nbLignes;
    }

    public StatusCatcms getStatutCatcms() {
        return statutCatcms;
    }

    public void setStatutCatcms(StatusCatcms statutCatcms) {
        this.statutCatcms = statutCatcms;
    }

    public String getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(String typeFichier) {
        this.typeFichier = typeFichier;
    }

    public FichierCatcms() {
    }

    public FichierCatcms(String nomFichier, Date dateDebutChargt, Date dateFinChargt, Long nbLignes, StatusCatcms statutCatcms, String typeFichier) {
        this.nomFichier = nomFichier;
        this.dateDebutChargt = dateDebutChargt;
        this.dateFinChargt = dateFinChargt;
        this.nbLignes = nbLignes;
        this.statutCatcms = statutCatcms;
        this.typeFichier = typeFichier;
    }
}
