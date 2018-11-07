public class Main {

    public static void main(String[] args) {
        System.out.println("Author: Juho Taakala");

        /*JsonObject jsonObject = new JsonObject();
        jsonObject.put("name", "name");
        jsonObject.put("num", 1);
        jsonObject.put("true/false", true);*/

        JsonArray jsonArray = new JsonArray();
        jsonArray.add("testi");
        jsonArray.add(1);

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
