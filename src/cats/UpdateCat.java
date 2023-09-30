package cats;

import cats.repository.Cats;
import cats.repository.Connection;
import cats.repository.Types;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateCat {
    static final Config config = new Config();
    Types types;
    Cats cats;

    public UpdateCat() throws ClassNotFoundException, SQLException {
        Class.forName(config.get("DB_Driver"));
        types = new Types();
        cats = new Cats();
        types.createTable();
        cats.createTable();
        types.addAllTypes();
    }

    public static void main(String[] args) {
        try {
            UpdateCat app = new UpdateCat();

            int i;
            ResultSet rows;

            i = 0;
            rows = app.cats.getByWhere("name like '%Том%'");
            while (rows.next()) {
                i++;
                System.out.println(rows.getInt("id") + " " + rows.getString("name"));
            }
            System.out.println("total - " + i);
            System.out.println();

            app.cats.deleteById(8505);

            i = 0;
            rows = app.cats.getByWhere("name like '%Том%'");
            while (rows.next()) {
                i++;
                System.out.println(rows.getInt("id") + " " + rows.getString("name"));
            }
            System.out.println("total - " + i);
            System.out.println();

            app.cats.deleteByWhere("name like '%Том%' and id > 10000");

            i = 0;
            rows = app.cats.getByWhere("name like '%Том%'");
            while (rows.next()) {
                i++;
                System.out.println(rows.getInt("id") + " " + rows.getString("name"));
            }
            System.out.println("total - " + i);
            System.out.println();

            app.cats.updateByWhere("name = 'Томас', age  = 2", "name like '%Том%'");

            i = 0;
            rows = app.cats.getByWhere("name like '%Том%'");
            while (rows.next()) {
                i++;
                System.out.println(rows.getInt("id") + " " + rows.getString("name"));
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

