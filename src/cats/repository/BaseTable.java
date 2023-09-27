package cats.repository;

import cats.FirstTable;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseTable implements Closeable {
    Connection connection;  // JDBC-соединение для работы с таблицей
    String tableName;

    public BaseTable(String tableName) throws SQLException {
        this.tableName = tableName;
        this.connection = FirstTable.getConnection();
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия SQL соединения!");
        }
    }

    boolean executeSqlStatement(String sql) throws SQLException {
        boolean result;
        reopenConnection();
        Statement statement = connection.createStatement();
        result = statement.execute(sql);
        statement.close();

        return result;
    };

    void reopenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = FirstTable.getConnection();
        }
    }
}
