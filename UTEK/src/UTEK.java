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
    private String med;
    private String condition;
    private String canMove;
    private String reason;
    private double  age;
    private String sex;
    private double numpeople;
    private String additionalInfo;
    private String time;

    public LocationData(String fullName, double latitude, double longitude, String med, String condition, String canMove, String reason, double age, String sex, double numpeople, String additionalInfo, String time) {
        this.fullName = fullName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.med = med;
        this.condition = condition;
        this.canMove = canMove;
        this.reason = reason;
        this.age = age;
        this.sex = sex;
        this.numpeople = numpeople;
        this.additionalInfo = additionalInfo;
        this.time = time;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "\n  fullName='" + fullName + '\'' +
                "\n  latitude=" + latitude +
                "\n  longitude=" + longitude +
                "\n  med='" + med + '\'' +
                "\n  condition='" + condition + '\'' +
                "\n  canMove='" + canMove + '\'' +
                "\n  reason='" + reason + '\'' +
                "\n  age=" + age +
                "\n  sex='" + sex + '\'' +
                "\n  numpeople=" + numpeople +
                "\n  additionalInfo='" + additionalInfo + '\'' +
                "\n  time='" + time + '\'' +
                "\n}";
    }
}

public class UTEK {
    public static List<Double> LATS;
    public static List<Double> LONGS;

    public static void main(String[] args) throws IOException, ParseException {

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("UTEK/src/data.json"));

        List<LocationData> locations = new ArrayList<>();

        LATS = new ArrayList<>();
        LONGS = new ArrayList<>();

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String fullName = (String) jsonObject.get("full_name");
            double latitude = Double.parseDouble((String) jsonObject.get("latitude"));
            double longitude = Double.parseDouble((String) jsonObject.get("longitude"));
            String med = (String) jsonObject.get("requireMedicalHelp");
            String condition = (String) jsonObject.get("selectedConditions");
            String canMove = (String) jsonObject.get("canMove");
            String reason = (String) jsonObject.get("reason");
            double age = ((Number) jsonObject.get("age")).doubleValue();
            String sex = (String) jsonObject.get("sex");
            double numPeople = ((Number) jsonObject.get("numOfPeople")).doubleValue();
            String additionalInfo = (String) jsonObject.get("additionalInfo");
            String timestamp = (String) jsonObject.get("timestamp");

            LATS.add(latitude);
            LONGS.add(longitude);

            locations.add(new LocationData(fullName, latitude, longitude, med, condition, canMove, reason, age, sex, numPeople, additionalInfo, timestamp));
        }

        for (LocationData location : locations) {
            System.out.println(location);
        }


        System.out.println(LATS);
        System.out.println(LONGS);


        final int WIDTH = 1650;
        final int HEIGHT = 1080;

        JFrame mapWindow = new JFrame("Alert360 Map");
        mapWindow.setSize(WIDTH, HEIGHT);
        mapWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BackgroundPanel backgroundPanel = new BackgroundPanel(LATS, LONGS);

        mapWindow.add(backgroundPanel);
        mapWindow.setVisible(true);
    }
}
class BackgroundPanel extends JPanel {
    private Image backgroundImage;
    private Image title;
    private List<Double> lats;
    private List<Double> longs;
    public BackgroundPanel(List<Double> lats, List<Double> longs) {
        this.lats = lats;
        this.longs = longs;
        try {
            backgroundImage = ImageIO.read(new File("UTEK/japan_map.png"));
            title = ImageIO.read(new File("UTEK/Alert360.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(title, 500, -60,500 ,250, this);
            g.drawImage(backgroundImage, 125, 125, 1300,600, this);
        }
        g.setColor(Color.RED);

        for (int i = 0; i < lats.size(); i++) {
            double x = lats.get(i);
            double y = longs.get(i);
            g.fillOval((int)x, (int)y, 15, 15);
        }
    }
}