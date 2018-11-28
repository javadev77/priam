package fr.sacem.priam.batch.common.util;

/**
 * Created by benmerzoukah on 19/02/2018.
 */
public class DateTimeUtils {

    public java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
}
