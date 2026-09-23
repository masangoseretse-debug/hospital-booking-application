package za.ac.cput.hospital.config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseIntegrationTest {

    @Test
    void connectsToDatabaseAndReadsData() throws Exception {
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users")) {

            assertNotNull(connection);
            assertTrue(connection.isValid(2));
            assertTrue(resultSet.next());

            int userCount = resultSet.getInt(1);
            assertTrue(userCount >= 15);
        }
    }
}
