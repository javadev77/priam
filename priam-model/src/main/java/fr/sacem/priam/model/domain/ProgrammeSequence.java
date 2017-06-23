package fr.sacem.priam.model.domain;




import javax.persistence.*;

/**
 * Created by fandis on 22/06/2017.
 */
@Entity
@Table(name = "PRIAM_PROGRAMME_SEQUENCE")
public class ProgrammeSequence {
    @EmbeddedId
    private ProgrammeKey programmeKey;

    public ProgrammeKey getProgrammeKey() {
        return programmeKey;
    }

    public void setProgrammeKey(ProgrammeKey programmeKey) {
        this.programmeKey = programmeKey;
    }

    public ProgrammeSequence(ProgrammeKey programmeKey) {
        this.programmeKey = programmeKey;
    }

    public ProgrammeSequence() {
    }
}
