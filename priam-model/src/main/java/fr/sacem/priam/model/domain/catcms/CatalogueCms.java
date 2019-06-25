package fr.sacem.priam.model.domain.catcms;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.sacem.priam.model.util.SimpleDateSerializer;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PRIAM_CATCMS_CATALOGUE")
public class CatalogueCms implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE_CMS")
    private String typeCMS;

    @Column(name = "IDE12")
    private Long ide12;

    @Column(name = "TITRE")
    private String titre;

    @Column(name = "TYP_UTIL_GEN")
    private String typUtilGen;

    @Column(name = "DATE_ENTREE")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = SimpleDateSerializer.class)
    private Date dateEntree;

    @Column(name = "TYPE_INSCRIPTION")
    private String typeInscription;

    @Column(name = "DATE_RENOUVELLEMENT")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = SimpleDateSerializer.class)
    private Date dateRenouvellement;

    @Column(name = "DATE_SORTIE")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = SimpleDateSerializer.class)
    private Date dateSortie;

    @Column(name = "TYPE_SORTIE")
    private String typeSortie;

    @Column(name = "RAISON_SORTIE")
    private String raisonSortie;

    @Column(name = "ROLES")
    private String roles;

    @Column(name = "PARTICIPANTS")
    private String participants;

    @Column(name = "ELIGIBLE_CREATION")
    private Boolean eligibleCreation;

    public String getRoles() {
        return this.roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCMS() {
        return typeCMS;
    }

    public void setTypeCMS(String typeCMS) {
        this.typeCMS = typeCMS;
    }

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

    public String getTypUtilGen() {
        return typUtilGen;
    }

    public void setTypUtilGen(String typUtilGen) {
        this.typUtilGen = typUtilGen;
    }

    public Date getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(Date dateEntree) {
        this.dateEntree = dateEntree;
    }

    public String getTypeInscription() {
        return typeInscription;
    }

    public void setTypeInscription(String typeInscription) {
        this.typeInscription = typeInscription;
    }

    public Date getDateRenouvellement() {
        return dateRenouvellement;
    }

    public void setDateRenouvellement(Date dateRenouvellement) {
        this.dateRenouvellement = dateRenouvellement;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getTypeSortie() {
        return typeSortie;
    }

    public void setTypeSortie(String typeSortie) {
        this.typeSortie = typeSortie;
    }

    public String getRaisonSortie() {
        return raisonSortie;
    }

    public void setRaisonSortie(String raisonSortie) {
        this.raisonSortie = raisonSortie;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public Boolean getEligibleCreation() {
        return eligibleCreation;
    }

    public void setEligibleCreation(Boolean eligibleCreation) {
        this.eligibleCreation = eligibleCreation;
    }


    public CatalogueCms() {}
}
