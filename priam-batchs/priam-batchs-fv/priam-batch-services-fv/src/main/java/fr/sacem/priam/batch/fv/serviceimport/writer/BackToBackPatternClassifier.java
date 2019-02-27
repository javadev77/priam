package fr.sacem.priam.batch.fv.serviceimport.writer;

import java.util.Map;
import org.springframework.classify.Classifier;
import org.springframework.classify.ClassifierAdapter;
import org.springframework.classify.PatternMatchingClassifier;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class BackToBackPatternClassifier<C, T> implements Classifier<C, T> {
    private Classifier<C, String> router;
    private Classifier<String, T> matcher;

    public BackToBackPatternClassifier() {
    }

    public BackToBackPatternClassifier(Classifier<C, String> router, Classifier<String, T> matcher) {
        this.router = router;
        this.matcher = matcher;
    }

    public void setMatcherMap(Map<String, T> map) {
        this.matcher = new PatternMatchingClassifier(map);
    }

    public void setRouterDelegate(Object delegate) {
        this.router = new ClassifierAdapter(delegate);
    }

    public T classify(C classifiable) {
        return this.matcher.classify(this.router.classify(classifiable));
    }
}