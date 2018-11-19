import jsonparser.JsonArray;
import jsonparser.JsonObject;
import jsonparser.JsonWriter;

import org.junit.*;

import java.io.FileWriter;

/**
 * UnitTest class of JSON parser.
 */
public class UnitTest {

    @Test
    public void main() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add("String");
        jsonArray.add(1);
        jsonArray.add(1.1);
        jsonArray.add(true);
        jsonArray.add(null);

        Assert.assertTrue(jsonArray instanceof JsonArray);
        Assert.assertEquals("String", jsonArray.get(0));

        JsonObject jsonObject = new JsonObject();
        jsonObject.put("string", "name");
        jsonObject.put("int", 1);
        jsonObject.put("double", 1.5);
        jsonObject.put("boolean", true);
        jsonObject.put("null", null);
        jsonObject.put("list", jsonArray);

        Assert.assertTrue(jsonObject instanceof JsonObject);
        Assert.assertEquals("name", jsonObject.get("string"));

        try (JsonWriter writer = new JsonWriter(new FileWriter("values.txt"))) {
            writer.objectToJson(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
