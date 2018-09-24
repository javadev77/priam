package fr.sacem.priam.model.util;

import org.hibernate.jdbc.Work;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Created by LM on 28/05/2015.
 */
public class ConnectionInfo implements Work {

    private String dataBaseUrl;
    private String dataBaseProductName;
    private String databaseProductVersion;
    private String driverName;
    private String driverVersion;

    @Override
    public void execute(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        dataBaseUrl = metaData.getURL();
        dataBaseProductName = metaData.getDatabaseProductName();
        databaseProductVersion = metaData.getDatabaseProductVersion();
        driverName = metaData.getDriverName();
        driverVersion = metaData.getDriverVersion();
    }

    public String getDataBaseUrl() {
        return dataBaseUrl;
    }

    public void setDataBaseUrl(String dataBaseUrl) {
        this.dataBaseUrl = dataBaseUrl;
    }

    public String getDataBaseProductName() {
        return dataBaseProductName;
    }

    public void setDataBaseProductName(String dataBaseProductName) {
        this.dataBaseProductName = dataBaseProductName;
    }

    public String getDatabaseProductVersion() {
        return databaseProductVersion;
    }

    public void setDatabaseProductVersion(String databaseProductVersion) {
        this.databaseProductVersion = databaseProductVersion;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverVersion() {
        return driverVersion;
    }

    public void setDriverVersion(String driverVersion) {
        this.driverVersion = driverVersion;
    }
}
