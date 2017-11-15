package fr.sacem.priam.common.exception;

/**
 * Created by benmerzoukah on 26/04/2017.
 */
public class InputValidationException extends RuntimeException {

    public InputValidationException(String s) {
        super(s);
    }

    public InputValidationException(String s, Exception e) {
        super(s, e);
    }
}
