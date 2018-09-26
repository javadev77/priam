package fr.sacem.priam.model.domain.catcms;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by benmerzoukah on 20/06/2018.
 */
@Entity
@Table(name = "PRIAM_CATCMS_PARTICIPANTS")
public class ParticipantsCatcms implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE_CMS")
    private String typeCMS;

    @Column(name = "IDE12")
    private Long ide12;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "PARTICIPANT")
    private String nomParticpant;

    /*@ManyToOne
    @JoinColumn(name = "ID_CATALOGUE")
    private CatalogueCms catalogueCms;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomParticpant() {
        return nomParticpant;
    }

    public void setNomParticpant(String nomParticpant) {
        this.nomParticpant = nomParticpant;
    }

    public String getTypeCMS() {
        return typeCMS;
    }

    public String getRole() {
        return role;
    }

    public Long getIde12() {
        return ide12;
    }

    public void setTypeCMS(String typeCMS) {
        this.typeCMS = typeCMS;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }

    /*public CatalogueCms getCatalogueCms() {
        return catalogueCms;
    }

    public void setCatalogueCms(CatalogueCms catalogueCms) {
        this.catalogueCms = catalogueCms;
    }*/

    public ParticipantsCatcms() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Long)) return false;

        Long that = (Long) o;


        return Objects.equals(getId(), that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
