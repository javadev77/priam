package fr.sacem.priam.catcms.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.sacem.priam.model.util.DateTimeRechercheDeserializer;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by embouazzar on 31/08/2018.
 */
public class JournalCatcmsCritereRecherche {

    private String typeCMS;
    private String typeEvenement;
    private String ide12;

    /*@JsonDeserialize(using = DateTimeRechercheDeserializer.class)
    private Timestamp periodeDebutEvenement;

    @JsonDeserialize(using = DateTimeRechercheDeserializer.class)
    private Timestamp periodeFinEvenement;*/

    @JsonDeserialize(using = DateTimeRechercheDeserializer.class)
    private Date periodeDebutEvenement;

    @JsonDeserialize(using = DateTimeRechercheDeserializer.class)
    private Date periodeFinEvenement;

    public String getTypeCMS() {
        return typeCMS;
    }

    public void setTypeCMS(String typeCMS) {
        this.typeCMS = typeCMS;
    }

    public String getTypeEvenement() {
        return typeEvenement;
    }

    public void setTypeEvenement(String typeEvenement) {
        this.typeEvenement = typeEvenement;
    }

    public String getIde12() {
        return ide12;
    }

    public void setIde12(String ide12) {
        this.ide12 = ide12;
    }

    public Date getPeriodeDebutEvenement() {
        return periodeDebutEvenement;
    }

    public void setPeriodeDebutEvenement(Date periodeDebutEvenement) {
        this.periodeDebutEvenement = periodeDebutEvenement;
    }

    public Date getPeriodeFinEvenement() {
        return periodeFinEvenement;
    }

    public void setPeriodeFinEvenement(Date periodeFinEvenement) {
        this.periodeFinEvenement = periodeFinEvenement;
    }

    /*public Timestamp getPeriodeDebutEvenement() {
        return periodeDebutEvenement;
    }

    public void setPeriodeDebutEvenement(Timestamp periodeDebutEvenement) {
        this.periodeDebutEvenement = periodeDebutEvenement;
    }

    public Timestamp getPeriodeFinEvenement() {
        return periodeFinEvenement;
    }

    public void setPeriodeFinEvenement(Timestamp periodeFinEvenement) {
        this.periodeFinEvenement = periodeFinEvenement;
    }*/

    public JournalCatcmsCritereRecherche() {
    }
}
