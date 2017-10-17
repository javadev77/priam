package fr.sacem.priam.common.exception;

/**
 * Created by benmerzoukah on 26/04/2017.
 */
public class TechnicalException extends RuntimeException {

    public TechnicalException(String s) {
        super(s);
    }
    
    public TechnicalException(String s, Exception e) {
        super(s, e);
    }
}
