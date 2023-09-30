package cats.repository;

import cats.Config;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    static final Config config = new Config();
    public static java.sql.Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.get("DB_URL"));
    }
    public static void close() throws SQLException {
        getConnection().close();
    }
}
