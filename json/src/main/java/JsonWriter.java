import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class JsonWriter {
    private FileWriter fileWriter;

    public JsonWriter() {

    }

    public void objectToJson(Map<String, Object> map) {
        //Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();

        int index = 1;

        fileWriter = null;

        try {
            fileWriter = new FileWriter("values.txt");
            fileWriter.write("{");

            for (Map.Entry<String, Object> entry : map.entrySet()) {

                if (map.size() == index) {
                    fileWriter.write(lastObjectEntry(entry.getKey(), entry.getValue()));
                } else if (entry.getValue() instanceof String) {
                    fileWriter.write(toJsonString(entry.getKey(), entry.getValue()));
                } else {
                    fileWriter.write(toJsonObject(entry.getKey(), entry.getValue()));
                }

                index++;
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

    public void arrayToJson(ArrayList<Object> list) {

        fileWriter = null;

        try {
            fileWriter = new FileWriter("values.txt");
            fileWriter.write("[");

            for (Object value : list) {

                if (value == list.get(list.size() - 1)) {
                    fileWriter.write(lastArrayEntry(value));
                } else if (value instanceof String) {
                    fileWriter.write(toJsonArrayString(value));
                } else {
                    fileWriter.write(toJsonArray(value));
                }
            }

            fileWriter.write("]");

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

    private String lastObjectEntry(String key, Object value) {
        if (value instanceof String) {
            return String.format("\"%s\":\"%s\"", key, value);
        }

        return String.format("\"%s\":%s", key, value);
    }

    private String toJsonString(String key, Object value) {
        return String.format("\"%s\":\"%s\", ", key, value);
    }

    private String toJsonObject(String key, Object value) {
        return String.format("\"%s\":%s, ", key, value);
    }

    private String lastArrayEntry(Object value) {
        if (value instanceof String) {
            return String.format("\"%s\"", value);
        }

        return String.format("%s", value);
    }

    private String toJsonArray(Object value) {
        return String.format("%s, ", value);
    }

    private String toJsonArrayString(Object value) {
        return String.format("\"%s\", ", value);
    }
}
