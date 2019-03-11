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
@Table(name = "SAREFTR_STE")
public class SareftrSte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSTE")
    private Long idSte;

    public SareftrSte() {

    }

    public Long getIdSte() {
        return idSte;
    }

    public void setIdSte(final Long idSte) {
        this.idSte = idSte;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idSte);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SareftrSte that = (SareftrSte) o;

        if (idSte != null ? !idSte.equals(that.idSte) : that.idSte != null) return false;

        return true;
    }
}
