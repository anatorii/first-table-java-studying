package cats.repository;

import java.sql.SQLException;

public class Types extends BaseTable implements TableOperations {
    public Types() throws SQLException {
        super("types");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement(
                "create table if not exists types (" +
                "id integer auto_increment primary key," +
                "type varchar(100) not null)");
    }

    @Override
    public void createForeignKeys() throws SQLException {

    }

    @Override
    public void createExtraConstraints() throws SQLException {

    }
}
