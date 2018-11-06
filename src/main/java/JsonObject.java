import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonObject {
    JsonWriter writer;
    Map<String, Object> map;

    public JsonObject() {
        writer = new JsonWriter();
        map = new HashMap<>();
    }

    public void put(String key, Object value) {
        map.put(key, value);
        writer.writeToJson(map);

        //Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();

        /*if (entries.hasNext()) {
            writer.writeToJson(map);
        }*/
    }

    public String getKey(String key) {
        return "";
    }

    public Object getObject(String key) {
        return map.get(key);
    }

    public void setJsonString(Map map) {

    }


}
