package cats.repository;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseTable implements Closeable {
    Connection connection;
    String tableName;

    public BaseTable(String tableName) throws SQLException {
        this.tableName = tableName;
        this.connection = cats.repository.Connection.getConnection();
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

    public ResultSet query(String sql) throws SQLException {
        reopenConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    void reopenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = cats.repository.Connection.getConnection();
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

    public boolean deleteById(int id) throws SQLException {
        return deleteByWhere("id = " + id);
    }

    public boolean deleteByWhere(String whereParams) throws SQLException {
        String where = whereParams.equals("") ? "" : " where " + whereParams;
        return executeSqlStatement("delete from " + this.tableName + where);
    }

    public boolean updateByWhere(String values, String whereParams) throws SQLException {
        String where = whereParams.equals("") ? "" : " where " + whereParams;
        return executeSqlStatement("update " + this.tableName + " set " + values + where);
    }

    public boolean updateById(String values, int id) throws SQLException {
        return updateByWhere(values, "id = " + id);
    }

}
