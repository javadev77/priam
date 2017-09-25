package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by belwidanej on 21/08/2017.
 */
@Entity
@Table(name="PRIAM_PARAMETRAGE")
@IdClass(ParametragePK.class)
public class Parametrage implements Serializable {

    @Id
    @Column(name = "PRIAM_KEY")
    private String key;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PRIAM_VALUE")
    private String value;

    public Parametrage(String value, String key, String userId) {
        this.key = key;
        this.userId = userId;
        this.value = value;
    }

    public Parametrage() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

