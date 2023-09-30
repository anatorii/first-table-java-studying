package cats;

import cats.repository.Cats;
import cats.repository.Types;
import cats.repository.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatsSelect {
    static final Config config = new Config();
    Types types;
    Cats cats;

    public CatsSelect() throws ClassNotFoundException, SQLException {
        Class.forName(config.get("DB_Driver"));
        types = new Types();
        cats = new Cats();
        types.createTable();
        cats.createTable();
        types.addAllTypes();
    }

    public static void main(String[] args) {
        try {
            CatsSelect app = new CatsSelect();

            int i;
            ResultSet rows;

            i = 0;
            rows = app.cats.getByWhere("name like '%Том%'");
            while (rows.next()) {
                i++;
                System.out.println(rows.getString("name"));
            }
            System.out.println("total - " + i);
            System.out.println();

            i = 0;
            rows = app.cats.getById(10300);
            while (rows.next()) {
                i++;
                System.out.println(rows.getString("name"));
            }
            System.out.println("total - " + i);
            System.out.println();

            i = 0;
            rows = app.cats.getAll();
            while (rows.next()) {
                i++;
                System.out.println(rows.getString("name"));
            }
            System.out.println("total - " + i);
            System.out.println();


            Connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC драйвер для СУБД не найден!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !");
        }
    }

}
