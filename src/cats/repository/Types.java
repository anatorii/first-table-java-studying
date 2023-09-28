package cats.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public ResultSet getTypeByName(String type) throws SQLException {
        reopenConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from " + this.tableName + " where type = '" + type + "'");
        return result;
    }

    public void addAllTypes() throws SQLException {
        reopenConnection();
        Statement statement = connection.createStatement();
        for (String type : TypesArray.getTypesArray()) {
            ResultSet resultSet = statement.executeQuery("select * from " + this.tableName + " where type = '" + type + "'");
            if (!resultSet.next()) {
                insert(type);
            }
        }
        statement.close();
    }

    public void delete(int id) throws SQLException {
        reopenConnection();
        Statement statement = connection.createStatement();
        statement.execute("delete from " + this.tableName + " where id = " + id);
        statement.close();
    }

    public void update(int id, String type) throws SQLException {
        reopenConnection();
        Statement statement = connection.createStatement();
        statement.execute("update " + this.tableName + " set " +
                " type = '" + type + "'" +
                " where id = " + id);
        statement.close();
    }
}
