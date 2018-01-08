package fr.sacem.priam.batch.affectation.mapper;

import java.awt.*;

/**
 * Created by fandis on 15/12/2017.
 */
public class PointsResult {

    private Long id;
    private Double mt;
    private Long ide12;

    public PointsResult() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMt() {
        return mt;
    }

    public void setMt(Double mt) {
        this.mt = mt;
    }

    public Long getIde12() {
        return ide12;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }

    @Override
    public String toString() {
        return "pointsResult : " + "id=" + id + ", ide12="  + ide12 + ", mt = " + mt;
    }
}
