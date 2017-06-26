package fr.sacem.priam.model.domain;


import javax.persistence.*;

/**
 * Created by fandis on 22/06/2017.
 */
@Entity
@Table(name = "PRIAM_PARAMAPPLI")
public class ParamAppli {

    @Id
    @Column(name = "CDEPARAM")
    private String cdeParam;
    @Column(name = "LIBSTAT")
    private String libStat;
    @Column(name = "VAL")
    private String val;



    public String getCdeParam() {
        return cdeParam;
    }

    public void setCdeParam(String cdeParam) {
        this.cdeParam = cdeParam;
    }

    public String getLibStat() {
        return libStat;
    }

    public void setLibStat(String libStat) {
        this.libStat = libStat;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public ParamAppli(String cdeParam, String libStat, String val) {
        this.cdeParam = cdeParam;
        this.libStat = libStat;
        this.val = val;
    }

    public ParamAppli() {
    }
}
