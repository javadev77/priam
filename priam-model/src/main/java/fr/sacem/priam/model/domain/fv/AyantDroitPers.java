package fr.sacem.priam.model.domain.fv;

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
@Table(name = "PRIAM_AYANT_DROIT_PERS")
public class AyantDroitPers implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUMPERS")
    private Long numPers;

    public AyantDroitPers() {
    }

    public Long getNumPers() {
        return numPers;
    }

    public void setNumPers(final Long numPers) {
        this.numPers = numPers;
    }
}
