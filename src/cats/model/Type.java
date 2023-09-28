package cats.model;

import cats.repository.Types;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Type extends BaseModel {
    int id;
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type() {
        super();
        tableName = "types";
    }
}
