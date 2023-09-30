package cats.repository;

import cats.model.Type;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
