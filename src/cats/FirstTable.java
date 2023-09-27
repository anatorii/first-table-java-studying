package cats;

import cats.repository.Types;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FirstTable {
    public static final String DB_URL = "jdbc:sqlite:db/cats.db";
    public static final String DB_Driver = "org.sqlite.JDBC";
    Types types;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public FirstTable() throws ClassNotFoundException, SQLException {
        Class.forName(DB_Driver);
        types = new Types();
        types.createTable();
    }

    public static void main(String[] args) {
        try {
            FirstTable app = new FirstTable();
            FirstTable.getConnection().close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC драйвер для СУБД не найден!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !");
        }
    }
}
