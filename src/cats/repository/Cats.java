package cats.repository;

import cats.model.Type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cats extends BaseTable implements TableOperations {
    public Cats() throws SQLException {
        super("cats");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement(
                "create table if not exists cats (" +
                        "id integer not null primary key autoincrement unique," +
                        "name varchar(20) not null," +
                        "type_id integer not null," +
                        "age integer not null," +
                        "weight double," +
                        "foreign key (type_id) references types(id)" +
                        ")");
    }

    @Override
    public void createForeignKeys() throws SQLException {

    }

    @Override
    public void createExtraConstraints() throws SQLException {

    }

    public boolean insert(String name, String type, int age, Double weight) throws SQLException {
        Types typeTable = new Types();
        ResultSet result = typeTable.getByName(type);
        if (!result.next()) {
            result.getStatement().close();
            typeTable.insert(type);
            result = typeTable.getByName(type);
        }

        int typeId = result.getInt("id");
        result.getStatement().close();

        String sql = "insert into cats (name, type_id, age, weight)" +
                "values ('"+name+"',"+
                String.valueOf(typeId)+","+
                String.valueOf(age)+","+
                String.valueOf(weight)+")";
        return super.executeSqlStatement(sql);
    }

    public boolean insert(String name, int typeId, int age, Double weight) throws SQLException {
        String sql = "insert into cats (name, type_id, age, weight)" +
                "values ('"+name+"',"+
                String.valueOf(typeId)+","+
                String.valueOf(age)+","+
                String.valueOf(weight)+")";
        return super.executeSqlStatement(sql);
    }

    public void addNumberCats(int n) throws SQLException {
        // количество пород
        ResultSet resultSet = query("select count(*) from types");
        int count = resultSet.getInt(1);
        resultSet.getStatement().close();

        // массив ключей пород
        int[] typesIds = new int[count];
        Types types = new Types();
        ResultSet result = types.getAll();
        int i = 0;
        while (result.next()) {
            typesIds[i++] = result.getInt("id");
        }

        // формируем запрос добавление кошек
        int typeId;
        int catNameIndex;
        String sql = "";
        for (int j = 0; j < n; j++) {
            typeId = (int) (Math.random() * typesIds.length);
            catNameIndex = (int) (Math.random() * CatNamesArray.getNamesArray().length);

            if (!sql.equals("")) {
                sql += ",\n";
            }

            sql += "('"+CatNamesArray.getNamesArray()[catNameIndex]+"',"+
                    String.valueOf(typeId)+","+
                    String.valueOf(1 + (int) (Math.random() * 15))+","+
                    String.valueOf((10 + (int) (Math.random() * 31)) / 10.0)+")";
        }
        sql = "insert into cats (name, type_id, age, weight) values\n" + sql;

        // выполнение запроса
        super.executeSqlStatement(sql);

    }
}
