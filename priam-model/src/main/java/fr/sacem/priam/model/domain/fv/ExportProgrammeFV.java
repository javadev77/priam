package fr.sacem.priam.model.domain.fv;

import com.google.common.base.Objects;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutExportProgramme;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PRIAM_EXPORT_PROGRAMME_FV")
public class ExportProgrammeFV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NUMPROG")
    private Programme programme;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "DATE_CREATION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUT", nullable = true)
    private StatutExportProgramme statutExportProgramme;

    public ExportProgrammeFV() {
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public StatutExportProgramme getStatutExportProgramme() {
        return statutExportProgramme;
    }

    public void setStatutExportProgramme(StatutExportProgramme statutExportProgramme) {
        this.statutExportProgramme = statutExportProgramme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportProgrammeFV that = (ExportProgrammeFV) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
