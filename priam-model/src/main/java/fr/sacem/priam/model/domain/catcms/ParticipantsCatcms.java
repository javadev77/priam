package fr.sacem.priam.model.domain.catcms;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by benmerzoukah on 20/06/2018.
 */
@Entity
@Table(name = "PRIAM_CATCMS_PARTICIPANTS")
@JsonSerialize(using = ParticipantsSerializer.class)
public class ParticipantsCatcms implements Serializable {

    @EmbeddedId
    ParticipantsPK id;

    @Column(name = "PARTICIPANT")
    private String nomParticpant;

    public ParticipantsPK getId() {
        return id;
    }

    public void setId(ParticipantsPK id) {
        this.id = id;
    }

    public String getNomParticpant() {
        return nomParticpant;
    }

    public void setNomParticpant(String nomParticpant) {
        this.nomParticpant = nomParticpant;
    }

    public ParticipantsCatcms() {

    }
}
