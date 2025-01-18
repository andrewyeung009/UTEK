import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        //read the json file
        JSONParser jsonparse = new JSONParser();

        JSONObject jsonobject = (JSONObject) jsonparse.parse(new FileReader("UTEK/src/data.json"));

        // read data
        String name = (String) jsonobject.get("name");
        System.out.println(name);

    }
}
