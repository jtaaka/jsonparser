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

    /*

    {
      "firstName": "John",
      "lastName": "Smith",
      "isAlive": true,
      "age": 27,
      "address": {
        "streetAddress": "21 2nd Street",
        "city": "New York",
        "state": "NY",
        "postalCode": "10021-3100"
      },
      "phoneNumbers": [
        {
          "type": "home",
          "number": "212 555-1234"
        },
        {
          "type": "office",
          "number": "646 555-4567"
        },
        {
          "type": "mobile",
          "number": "123 456-7890"
        }
      ],
      "children": [],
      "spouse": null
    }

    */

    }
}
