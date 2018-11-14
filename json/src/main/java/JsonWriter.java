import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class JsonWriter implements AutoCloseable {
    private Writer writer;

    public JsonWriter(Writer writer) {
        this.writer = writer;
    }

    public void objectToJson(Map<String, Object> map) throws IOException {
        int index = 1;

        writer.write("{");

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            if (map.size() == index) {
                writer.write(lastObjectEntry(entry.getKey(), entry.getValue()));
            } else {
                writer.write(toJsonObject(entry.getKey(), entry.getValue()));
            }

            index++;
        }

        writer.write("}");
    }

    public void arrayToJson(ArrayList<Object> list) throws IOException {
        writer.write("[");

        for (Object value : list) {
            if (value == list.get(list.size() - 1)) {
                writer.write(lastArrayEntry(value));
            } else {
                writer.write(toJsonArray(value));
            }
        }

        writer.write("]");
    }

    private String lastObjectEntry(String key, Object value) {
        if (value instanceof String) {
            return String.format("\"%s\":\"%s\", ", key, value);

        } else if (value instanceof ArrayList) {
            return arrayObjectToString(key, value);
        }

        return String.format("\"%s\":%s", key, value);
    }

    private String toJsonObject(String key, Object value) {
        if (value instanceof String) {
            return String.format("\"%s\":\"%s\", ", key, value);

        } else if (value instanceof ArrayList) {
            return arrayObjectToString(key, value) + ", ";
        }

        return String.format("\"%s\":%s, ", key, value);
    }

    private String arrayObjectToString(String key, Object value) {
        StringBuilder str = new StringBuilder();

        str.append(String.format("\"%s\":[", key));

        for (int i = 0; i < ((ArrayList) value).size(); i++) {

            if (i == ((ArrayList) value).size() -1 && (((ArrayList) value).get(i)) instanceof String) {
                str.append(String.format("\"%s\"", (((ArrayList) value).get(i))));
            } else if (i == ((ArrayList) value).size() -1) {
                str.append(String.format("%s", (((ArrayList) value).get(i))));
            } else if ((((ArrayList) value).get(i)) instanceof String) {
                str.append(String.format("\"%s\", ", ((ArrayList) value).get(i)));

            } else {
                str.append(String.format("%s, ", (((ArrayList) value).get(i))));
            }
        }

        return str + "]";
    }

    private String lastArrayEntry(Object value) {
        if (value instanceof String) {
            return String.format("\"%s\"", value);
        }

        return String.format("%s", value);
    }

    private String toJsonArray(Object value) {
        if (value instanceof String) {
            return String.format("\"%s\", ", value);
        }

        return String.format("%s, ", value);
    }

    @Override
    public void close() throws Exception {
        if (writer != null) {
            writer.close();
        }
    }
}
