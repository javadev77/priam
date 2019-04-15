package fr.sacem.priam.batch.common.domain;

/**
 * Created by fandis on 10/10/2017.
 */
public class Admap {
    String inputFile;
    String secondInputFile;
    String outputFile;
    String patternFileName;

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getSecondInputFile() {
        return secondInputFile;
    }

    public void setSecondInputFile(String secondInputFile) {
        this.secondInputFile = secondInputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public String getPatternFileName() {
        return patternFileName;
    }

    public void setPatternFileName(String patternFileName) {
        this.patternFileName = patternFileName;
    }

    public Admap() {
    }
}
