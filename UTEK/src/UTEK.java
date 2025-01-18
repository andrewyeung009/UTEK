import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

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

        final int WIDTH = 1650;
        final int HEIGHT = 1080;

        JFrame mapWindow = new JFrame("Alert360 Map");
        mapWindow.setSize(WIDTH, HEIGHT);
        mapWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BackgroundPanel backgroundPanel = new BackgroundPanel();

        mapWindow.add(backgroundPanel);



        mapWindow.setVisible(true);
    }
}
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel() {
        try {
            // Load the image (replace the path with your image file's path)
            backgroundImage = ImageIO.read(new File("UTEK/japan_map.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            // Draw the image to fill the entire panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}