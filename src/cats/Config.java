package cats;

import java.util.HashMap;
import java.util.Map;

public class Config implements ConfigOperations {
    HashMap<String, String> config;

    public Config() {
        config = new HashMap<>();
        config.put("DB_URL", "jdbc:sqlite:db/cats.db");
        config.put("DB_Driver", "org.sqlite.JDBC");
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public String get(String param) {
        return config.get(param);
    }
}
