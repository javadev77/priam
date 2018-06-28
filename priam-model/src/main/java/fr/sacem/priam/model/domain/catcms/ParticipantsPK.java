package fr.sacem.priam.model.domain.catcms;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by benmerzoukah on 20/06/2018.
 */
@Embeddable
public class ParticipantsPK implements Serializable {

    @Column(name = "TYPE_CMS")
    private String typeCMS;

    @Column(name = "IDE12")
    private Long ide12;

    @Column(name = "ROLE")
    private String role;


    public Long getIde12() {
        return ide12;
    }

    public String getRole() {
        return role;
    }

    public String getTypeCMS() {
        return typeCMS;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setTypeCMS(String typeCMS) {
        this.typeCMS = typeCMS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipantsPK)) return false;

        ParticipantsPK that = (ParticipantsPK) o;


        return Objects.equals(getTypeCMS(), that.getTypeCMS()) &&
                Objects.equals(getIde12(), that.getIde12()) &&
                Objects.equals(getRole(), that.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeCMS(), getIde12(), getRole());
    }
}
