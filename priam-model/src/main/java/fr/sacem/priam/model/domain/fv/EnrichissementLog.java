package fr.sacem.priam.model.domain.fv;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.util.CustomDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PRIAM_FICHIER_FV_ENRICHISSEMENT_LOG")
public class EnrichissementLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_FICHIER")
    private Fichier fichier;

    @Column(name = "STATUT")
    private String detail;

    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;

    public EnrichissementLog() {
    }

    public EnrichissementLog(Long id, Fichier fichier, String detail, Date date) {
        this.id = id;
        this.fichier = fichier;
        this.detail = detail;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier(Fichier fichier) {
        this.fichier = fichier;
    }

    public String getdetail() {
        return detail;
    }

    public void setdetail(String detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EnrichissementLog other = (EnrichissementLog) obj;
        return (this.id == null && other.id == null)
                || (this.id != null && this.id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
