package fr.sacem.priam.batch.participants.req.domain;

/**
 * Created by benmerzoukah on 13/06/2018.
 */
public class CatalogueCms {

    private Long id;
    private Long ide12;
    private String cdeTypIde12;
    private String datConslt;

    public CatalogueCms() {

    }

    public Long getIde12() {
        return ide12;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCdeTypIde12() {
        return cdeTypIde12;
    }

    public String getDatConslt() {
        return datConslt;
    }

    public void setCdeTypIde12(String cdeTypIde12) {
        this.cdeTypIde12 = cdeTypIde12;
    }

    public void setDatConslt(String datConslt) {
        this.datConslt = datConslt;
    }
}
