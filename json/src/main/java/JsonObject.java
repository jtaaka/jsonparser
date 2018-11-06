import java.util.HashMap;
import java.util.Map;

public class JsonObject {
    private JsonWriter writer;
    private Map<String, Object> map;

    public JsonObject() {
        writer = new JsonWriter();
        map = new HashMap<>();
    }

    public void put(String key, Object value) {
        map.put(key, value);
        writer.objectToJson(map);
    }
}
