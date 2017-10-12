package fr.sacem.domain;

import java.time.LocalDateTime;

/**
 * Created by fandis on 06/09/2017.
 */
public class Repartition {
    String numeroProgramme ;
    LocalDateTime datcalc;
    Long rion;
    String status;
    int lineNumber;
    private Exception exception;

    public String getNumeroProgramme() {
        return numeroProgramme;
    }

    public void setNumeroProgramme(String numeroProgramme) {
        this.numeroProgramme = numeroProgramme;
    }

    public LocalDateTime getDatcalc() {
        return datcalc;
    }

    public void setDatcalc(LocalDateTime datcalc) {
        this.datcalc = datcalc;
    }

    public Long getRion() {
        return rion;
    }

    public void setRion(Long rion) {
        this.rion = rion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Repartition() {

    }

    public Repartition(String numeroProgramme, LocalDateTime datcalc, Long rion, String status) {
        this.numeroProgramme = numeroProgramme;
        this.datcalc = datcalc;
        this.rion = rion;
        this.status = status;

    }

    public Repartition(Exception exception) {
        this.exception = exception;
    }
}
