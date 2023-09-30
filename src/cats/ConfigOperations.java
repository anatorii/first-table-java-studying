package cats;

import java.util.Map;

public interface ConfigOperations {
    public Map<String, String> getConfig();

    public String get(String param);

}
