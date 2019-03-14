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
@Table(name = "SAREFTR_GREOEUV")
public class SareftrGreOeuv implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDEGREOEUV")
    private String cdeGreOeuv;

    public SareftrGreOeuv() {

    }

    public String getCdeGreOeuv() {
        return cdeGreOeuv;
    }

    public void setCdeGreOeuv(final String cdeGreOeuv) {
        this.cdeGreOeuv = cdeGreOeuv;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cdeGreOeuv);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SareftrGreOeuv that = (SareftrGreOeuv) o;

        if (cdeGreOeuv != null ? !cdeGreOeuv.equals(that.cdeGreOeuv) : that.cdeGreOeuv != null) return false;

        return true;
    }
}
