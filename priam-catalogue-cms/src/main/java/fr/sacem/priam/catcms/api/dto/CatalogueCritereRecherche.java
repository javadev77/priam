package fr.sacem.priam.catcms.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.sacem.priam.model.util.DateRechercheDeserializer;

import java.util.Date;

public class CatalogueCritereRecherche {
    private String typeCMS;
    /*private Long ide12;*/
    private String ide12;
    private String titre;
    private String participant;

    @JsonDeserialize(using = DateRechercheDeserializer.class)
    private Date periodeEntreeDateDebut;

    @JsonDeserialize(using = DateRechercheDeserializer.class)
    private Date periodeEntreeDateFin;

    @JsonDeserialize(using = DateRechercheDeserializer.class)
    private Date periodeRenouvellementDateDebut;

    @JsonDeserialize(using = DateRechercheDeserializer.class)
    private Date periodeRenouvellementDateFin;

    @JsonDeserialize(using = DateRechercheDeserializer.class)
    private Date periodeSortieDateDebut;

    @JsonDeserialize(using = DateRechercheDeserializer.class)
    private Date periodeSortieDateFin;

    private boolean displayOeuvreNonEligible;

    private String typeInscription;

    private String typeUtilisation;

    public String getTypeCMS() {
        return typeCMS;
    }

    public void setTypeCMS(String typeCMS) {
        this.typeCMS = typeCMS;
    }

    /*public Long getIde12() {
        return ide12;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }*/

    public String getIde12() {
        return ide12;
    }

    public void setIde12(String ide12) {
        this.ide12 = ide12;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public Date getPeriodeEntreeDateDebut() {
        return periodeEntreeDateDebut;
    }

    public void setPeriodeEntreeDateDebut(Date periodeEntreeDateDebut) {
        this.periodeEntreeDateDebut = periodeEntreeDateDebut;
    }

    public Date getPeriodeEntreeDateFin() {
        return periodeEntreeDateFin;
    }

    public void setPeriodeEntreeDateFin(Date periodeEntreeDateFin) {
        this.periodeEntreeDateFin = periodeEntreeDateFin;
    }

    public Date getPeriodeRenouvellementDateDebut() {
        return periodeRenouvellementDateDebut;
    }

    public void setPeriodeRenouvellementDateDebut(Date periodeRenouvellementDateDebut) {
        this.periodeRenouvellementDateDebut = periodeRenouvellementDateDebut;
    }

    public Date getPeriodeRenouvellementDateFin() {
        return periodeRenouvellementDateFin;
    }

    public void setPeriodeRenouvellementDateFin(Date periodeRenouvellementDateFin) {
        this.periodeRenouvellementDateFin = periodeRenouvellementDateFin;
    }

    public Date getPeriodeSortieDateDebut() {
        return periodeSortieDateDebut;
    }

    public void setPeriodeSortieDateDebut(Date periodeSortieDateDebut) {
        this.periodeSortieDateDebut = periodeSortieDateDebut;
    }

    public Date getPeriodeSortieDateFin() {
        return periodeSortieDateFin;
    }

    public void setPeriodeSortieDateFin(Date periodeSortieDateFin) {
        this.periodeSortieDateFin = periodeSortieDateFin;
    }

    public boolean isDisplayOeuvreNonEligible() {
        return displayOeuvreNonEligible;
    }

    public void setDisplayOeuvreNonEligible(boolean displayOeuvreNonEligible) {
        this.displayOeuvreNonEligible = displayOeuvreNonEligible;
    }

    public String getTypeInscription() {
        return typeInscription;
    }

    public void setTypeInscription(String typeInscription) {
        this.typeInscription = typeInscription;
    }

    public String getTypeUtilisation() {
        return typeUtilisation;
    }

    public void setTypeUtilisation(String typeUtilisation) {
        this.typeUtilisation = typeUtilisation;
    }

    public CatalogueCritereRecherche() {
    }

}
