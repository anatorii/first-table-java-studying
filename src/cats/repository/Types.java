package cats.repository;

import java.sql.SQLException;

public class Types extends BaseTable implements TableOperations {
    public Types() throws SQLException {
        super("types");
    }

    public boolean insert(String type) throws SQLException {
        return super.executeSqlStatement("insert into " + this.tableName + "(type) values ('" + type + "')");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement(
                "create table if not exists types (" +
                "id integer not null primary key autoincrement unique," +
                "type varchar(100) not null)");
    }

    @Override
    public void createForeignKeys() throws SQLException {

    }

    @Override
    public void createExtraConstraints() throws SQLException {

    }
}
