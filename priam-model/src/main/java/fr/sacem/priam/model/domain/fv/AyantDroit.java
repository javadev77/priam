package fr.sacem.priam.model.domain.fv;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
@Entity
@Table(name = "PRIAM_AYANT_DROIT")
public class AyantDroit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NUMPERS")
    private AyantDroitPers ayantDroitPers;

//    @ManyToOne
//    @Column(name = "ID_FV")
//    private LigneProgrammeFV ligneProgrammeFV;


    public AyantDroit() {

    }

//    public LigneProgrammeFV getLigneProgrammeFV() {
//        return ligneProgrammeFV;
//    }
//
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

//    public void setLigneProgrammeFV(final LigneProgrammeFV ligneProgrammeFV) {
//        this.ligneProgrammeFV = ligneProgrammeFV;
//    }

    public AyantDroitPers getAyantDroitPers() {
        return ayantDroitPers;
    }

    public void setAyantDroitPers(final AyantDroitPers ayantDroitPers) {
        this.ayantDroitPers = ayantDroitPers;
    }
}
