import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JsonWriter {
    private FileWriter fileWriter;
    private char q ='"';

    public JsonWriter() {

    }

    public void objectToJson(Map<String, Object> map) {
        //Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();

        fileWriter = null;

        try {
            fileWriter = new FileWriter("values.txt");
            fileWriter.write("{");

            for (Map.Entry<String, Object> entry : map.entrySet()) {

                if (entry.getValue() instanceof String) {
                        fileWriter.write(q + entry.getKey() + q + ":" + q + entry.getValue() + q + ", ");
                } else {
                        fileWriter.write(q + entry.getKey() + q + ":" + entry.getValue() + ", ");
                }
            }

            fileWriter.write("}");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
