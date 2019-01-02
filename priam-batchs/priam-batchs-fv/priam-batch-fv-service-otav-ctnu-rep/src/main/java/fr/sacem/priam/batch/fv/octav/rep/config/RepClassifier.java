package fr.sacem.priam.batch.fv.octav.rep.config;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import org.springframework.batch.support.annotation.Classifier;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class RepClassifier {

    @Classifier
    public String classify(LigneProgrammeFV ligneProgrammeFV) {

        return "";
    }
}
