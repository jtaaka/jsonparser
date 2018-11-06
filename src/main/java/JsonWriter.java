import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JsonWriter {
    private String str;
    private FileWriter fileWriter;
    private char q ='"';

    public JsonWriter() {

    }

    public void writeToJson(Map<String, Object> map) {
        //Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();

        fileWriter = null;
            try {
                fileWriter = new FileWriter("values.txt");
                fileWriter.write("{");

                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    //System.out.println(entry.getKey() + entry.getValue());

                    if (entry.getValue() instanceof String) {
                        fileWriter.write(q + entry.getKey() + q + ":" + q + entry.getValue() + q + ", ");
                    } else {
                        fileWriter.write(q + entry.getKey() + q + ":" + entry.getValue() + ", ");
                    }

                    //return "{" + q + entry.getKey() + q + ":" + q + entry.getValue() + q + ",";
                }

                fileWriter.write("}");

            } catch(IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(fileWriter != null) {
                        fileWriter.close();
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

        /*if (entries.get) {
            try {
                fileWriter.write("}");
                //return "}";
            } catch (Exception e) {

            }
        }*/

        //return "";
    }

    public void setJsonString(String str) {
        this.str = str;
    }

    public String getJsonString() {
        return this.str;
    }
}
