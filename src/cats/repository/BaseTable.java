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

    void executeSqlStatement(String sql, String description) throws SQLException {
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        Statement statement = connection.createStatement();  // Создаем statement для выполнения sql-команд
        statement.execute(sql); // Выполняем statement - sql команду
        statement.close();      // Закрываем statement для фиксации изменений в СУБД
        if (description != null)
            System.out.println(description);
    };

    void executeSqlStatement(String sql) throws SQLException {
        executeSqlStatement(sql, null);
    };

    void reopenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = FirstTable.getConnection();
        }
    }
}
