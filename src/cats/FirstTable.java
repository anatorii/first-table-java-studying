package cats;

import cats.repository.Cats;
import cats.repository.Connection;
import cats.repository.Types;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstTable {
    static final Config config = new Config();
    Types types;
    Cats cats;

    public FirstTable() throws ClassNotFoundException, SQLException {
        Class.forName(config.get("DB_Driver"));
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

            app.cats.insert("котик", "Уличная кошка", 2, 4.0);
            app.cats.insert("мурзик", "Меконгский бобтейл", 1, 4.1);
            app.cats.insert("мурзик 2", "Дворовая", 1, 4.1);

            app.cats.addNumberCats(5000);

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
