package es.publicotech.database;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class DataBaseConnector {
    private static final String propertiesPath = "/config.properties";

    public Connection connectToDB() throws IOException, SQLException {
        Properties properties = new Properties();
        try (InputStream inputStream = DataBaseConnector.class.getResourceAsStream(propertiesPath)) {
            properties.load(inputStream);
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            return DriverManager.getConnection(url, user, password);
        }
    }
}
