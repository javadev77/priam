package fr.sacem.priam.model.domain.saref;

import com.google.common.base.Objects;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
@Entity
@Table(name = "SAREFTR_TYPPROTEC")
public class SareftrTypProtec implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDETYPPROTEC")
    private String cdeTypProtec;

    public SareftrTypProtec() {

    }

    public String getCdeTypProtec() {
        return cdeTypProtec;
    }

    public void setCdeTypProtec(final String cdeTypProtec) {
        this.cdeTypProtec = cdeTypProtec;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cdeTypProtec);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SareftrTypProtec that = (SareftrTypProtec) o;

        if (cdeTypProtec != null ? !cdeTypProtec.equals(that.cdeTypProtec) : that.cdeTypProtec != null) return false;

        return true;
    }
}
