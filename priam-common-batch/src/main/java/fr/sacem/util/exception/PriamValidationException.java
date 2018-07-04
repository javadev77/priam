package fr.sacem.util.exception;

/**
 * Created by belwidanej on 30/08/2017.
 */
public class PriamValidationException extends Exception{

    private final int lineNumber;
    private final ErrorType errorType;
    private final Long idFichier;

    public static enum ErrorType {
        FORMAT_FICHIER, FORMAT_ATTRIBUT
    }

    public PriamValidationException(int lineNumber, Exception ex, ErrorType errorType, Long idFichier) {
        super(ex);
        this.lineNumber = lineNumber;
        this.errorType = errorType;
        this.idFichier = idFichier;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public Long getIdFichier() { return idFichier; }
}
