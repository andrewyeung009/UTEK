import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class LocationData {
    private String fullName;
    private double latitude;
    private double longitude;
    private String timestamp;

    public LocationData(String fullName, double latitude, double longitude, String timestamp) {
        this.fullName = fullName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "fullName='" + fullName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

public class UTEK {
    public static void main(String[] args) throws IOException, ParseException {

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("UTEK/src/data.json"));

        List<LocationData> locations = new ArrayList<>();

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String fullName = (String) jsonObject.get("full_name");
            double latitude = Double.parseDouble((String) jsonObject.get("latitude"));
            double longitude = Double.parseDouble((String) jsonObject.get("longitude"));
            String timestamp = (String) jsonObject.get("timestamp");

            locations.add(new LocationData(fullName, latitude, longitude, timestamp));
        }

        for (LocationData location : locations) {
            System.out.println(location);
        }
    }
}
