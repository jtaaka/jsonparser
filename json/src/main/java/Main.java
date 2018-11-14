import java.io.FileWriter;

public class Main {

    public static void main(String[] args) {
        System.out.println("Author: Juho Taakala");

        JsonArray jsonArray = new JsonArray();
        jsonArray.add("String");
        jsonArray.add(1);
        jsonArray.add(1.1);
        jsonArray.add(true);
        jsonArray.add(null);

        JsonObject jsonObject = new JsonObject();
        jsonObject.put("string", "name");
        jsonObject.put("int", 1);
        jsonObject.put("double", 1.5);
        jsonObject.put("boolean", true);
        jsonObject.put("null", null);
        jsonObject.put("list", jsonArray);

        try (JsonWriter writer = new JsonWriter(new FileWriter("values.txt"))) {
            writer.objectToJson(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
