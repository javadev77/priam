package fr.sacem.priam.model.domain;


import java.io.Serializable;

/**
 * Created by belwidanej on 22/09/2017.
 */
public class ParametragePK implements Serializable {

    private String key;
    private String userId;

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
}
