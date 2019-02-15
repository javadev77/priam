package fr.sacem.priam.model.domain.fv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutImportProgramme;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PRIAM_IMPORT_PROGRAMME_FV")
public class ImportProgrammeFV implements Serializable {

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
    private StatutImportProgramme statutImportProgramme;


    @Column(name = "CONTENT")
    @Lob
    @JsonIgnore
    private byte[] content;
    public ImportProgrammeFV() {
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

    public StatutImportProgramme getStatutImportProgramme() {
        return statutImportProgramme;
    }

    public void setStatutImportProgramme(final StatutImportProgramme statutImportProgramme) {
        this.statutImportProgramme = statutImportProgramme;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(final byte[] content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImportProgrammeFV that = (ImportProgrammeFV) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
