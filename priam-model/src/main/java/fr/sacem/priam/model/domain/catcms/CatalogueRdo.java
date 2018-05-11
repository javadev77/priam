package fr.sacem.priam.model.domain.catcms;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.sacem.priam.model.util.SimpleDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PRIAM_CATCMS_RDO")
public class CatalogueRdo implements Serializable {

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
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = SimpleDateSerializer.class)
    private Date dateEntree;

    @Column(name = "TYPE_INSCRIPTION")
    private String typeInscription;

    @Column(name = "DATE_RENOUVELLEMENT")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = SimpleDateSerializer.class)
    private Date dateRenouvellement;

    @Column(name = "DATE_SORTIE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = SimpleDateSerializer.class)
    private Date dateSortie;

    @Column(name = "TYPE_SORTIE")
    private String typeSortie;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "PARTICIPANT")
    private String participant;


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

    public CatalogueRdo() {
    }
}
