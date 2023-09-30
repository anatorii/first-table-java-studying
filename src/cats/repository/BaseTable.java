package cats.repository;

import cats.FirstTable;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
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

    public Statement statementOfQuery(String sql) throws SQLException {
        reopenConnection();
        Statement statement = connection.createStatement();
        statement.executeQuery(sql);
        return statement;
    }

    public ResultSet query(String sql) throws SQLException {
        reopenConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    void reopenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = FirstTable.getConnection();
        }
    }

    public ResultSet getByWhere(String whereParams) throws SQLException {
        String where = whereParams.equals("") ? "" : " where " + whereParams;
        return query("select * from " + this.tableName + where);
    }

    public ResultSet getById(int id) throws SQLException {
        return getByWhere("id = " + id);
    }

    public ResultSet getAll() throws SQLException {
        return getByWhere("");
    }

    public void deleteById(int id) throws SQLException {
        reopenConnection();
        Statement statement = connection.createStatement();
        statement.execute("delete from " + this.tableName + " where id = " + id);
        statement.close();
    }

}
