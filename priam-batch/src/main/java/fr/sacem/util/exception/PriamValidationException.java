package fr.sacem.util.exception;

/**
 * Created by belwidanej on 30/08/2017.
 */
public class PriamValidationException extends Exception{

    private final int lineNumber;
    private final ErrorType errorType;

    public static enum ErrorType {
        FORMAT_FICHIER, FORMAT_ATTRIBUT
    }

    public PriamValidationException(int lineNumber, Exception ex, ErrorType errorType) {
        super(ex);
        this.lineNumber = lineNumber;
        this.errorType = errorType;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
