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
@Table(name = "SAREFTR_CATEGADSACEM")
public class SareftrCategAdSacem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDECATEGADSACEM")
    private String cdeCategAdSacem;

    public SareftrCategAdSacem() {

    }

    public String getCdeCategAdSacem() {
        return cdeCategAdSacem;
    }

    public void setCdeCategAdSacem(final String cdeCategAdSacem) {
        this.cdeCategAdSacem = cdeCategAdSacem;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cdeCategAdSacem);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SareftrCategAdSacem that = (SareftrCategAdSacem) o;

        if (cdeCategAdSacem != null ? !cdeCategAdSacem.equals(that.cdeCategAdSacem) : that.cdeCategAdSacem != null) return false;

        return true;
    }
}
