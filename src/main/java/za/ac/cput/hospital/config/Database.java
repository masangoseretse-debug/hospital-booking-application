package za.ac.cput.hospital.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Database {
    private static final Properties PROPS = new Properties();
    static {
        try (InputStream in = Database.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new IllegalStateException("Missing db.properties. Copy db.properties.example and set your MySQL details.");
            PROPS.load(in);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) { throw new ExceptionInInitializerError(e); }
    }
    private Database() {}
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(PROPS.getProperty("db.url"), PROPS.getProperty("db.username"), PROPS.getProperty("db.password"));
    }
}
