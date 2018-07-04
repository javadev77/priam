package fr.sacem.priam.batch.filiation.npu.domain;

public class OeuvreFiliationNPU {

    private Integer id;
    private Long ide12;
    private Long ide12Rep;
    private String titreOriginal;
    private Integer lineNumber;
    private Exception exception;

    public OeuvreFiliationNPU() {
    }

    public OeuvreFiliationNPU(Exception exception) {
        this.exception = exception;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIde12() {
        return ide12;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }

    public Long getIde12Rep() {
        return ide12Rep;
    }

    public void setIde12Rep(Long ide12Rep) {
        this.ide12Rep = ide12Rep;
    }

    public String getTitreOriginal() {
        return titreOriginal;
    }

    public void setTitreOriginal(String titreOriginal) {
        this.titreOriginal = titreOriginal;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
