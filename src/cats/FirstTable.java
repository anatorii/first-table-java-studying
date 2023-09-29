package cats;

import cats.repository.Cats;
import cats.repository.Types;
import cats.repository.TypesArray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstTable {
    public static final String DB_URL = "jdbc:sqlite:db/cats.db";
    public static final String DB_Driver = "org.sqlite.JDBC";
    Types types;
    Cats cats;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public FirstTable() throws ClassNotFoundException, SQLException {
        Class.forName(DB_Driver);
        types = new Types();
        cats = new Cats();
        types.createTable();
        cats.createTable();
        types.addAllTypes();
    }

    public static void main(String[] args) {
        try {
            FirstTable app = new FirstTable();

            int i;
            ResultSet rows;

            i = 0;
            rows = app.types.getByWhere("type like '%кошка%'");
            while (rows.next()) {
                i++;
                System.out.println(rows.getString("type"));
            }
            System.out.println("total - " + i);
            System.out.println();

            i = 0;
            rows = app.types.getById(230);
            while (rows.next()) {
                i++;
                System.out.println(rows.getString("type"));
            }
            System.out.println("total - " + i);
            System.out.println();

            i = 0;
            rows = app.types.getByName("Сноу-шу");
            while (rows.next()) {
                i++;
                System.out.println(rows.getString("type"));
            }
            System.out.println("total - " + i);
            System.out.println();

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
