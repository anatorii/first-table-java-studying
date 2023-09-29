package cats.repository;

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
}
