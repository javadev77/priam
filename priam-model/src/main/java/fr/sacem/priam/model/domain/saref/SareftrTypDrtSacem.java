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
@Table(name = "SAREFTR_TYPDRTSACEM")
public class SareftrTypDrtSacem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDETYPDRTSACEM")
    private String cdeTypDrtSacem;

    public SareftrTypDrtSacem() {

    }

    public String getCdeTypDrtSacem() {
        return cdeTypDrtSacem;
    }

    public void setCdeTypDrtSacem(final String cdeTypDrtSacem) {
        this.cdeTypDrtSacem = cdeTypDrtSacem;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cdeTypDrtSacem);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SareftrTypDrtSacem that = (SareftrTypDrtSacem) o;

        if (cdeTypDrtSacem != null ? !cdeTypDrtSacem.equals(that.cdeTypDrtSacem) : that.cdeTypDrtSacem != null) return false;

        return true;
    }
}
