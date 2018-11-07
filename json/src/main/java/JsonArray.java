import java.util.ArrayList;

public class JsonArray {
    private JsonWriter writer;
    private ArrayList<Object> arrayList;

    public JsonArray() {
        writer = new JsonWriter();
        arrayList = new ArrayList<>();
    }

    public void add(Object value) {
        arrayList.add(value);
        writer.arrayToJson(arrayList);
    }
}