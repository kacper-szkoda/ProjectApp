package WaterLevelSprites;

import Control.SQLControl;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WateringCanSprite extends JLabel {

    public WateringCanSprite()
    {
        setSprite(findPath());
        this.setBackground(new Color(173, 188, 159));
    }

    public void setSprite(String path)
    {
        try
        {
            System.out.println(path);
            BufferedImage image = ImageIO.read(new File(path));
            Image scaled = image.getScaledInstance(134, 134, Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(scaled));
        }   catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String findPath() {
        JSONArray array = new JSONArray(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/getFrac"));
        JSONObject cur = array.getJSONObject(0);
        double frac = cur.getDouble("fraction");

        if (frac > 0.875)
            return "C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\WaterLevelSprites\\1.000.png";
        if (frac > 0.750)
            return "C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\WaterLevelSprites\\0.750.png";
        if (frac > 0.625)
            return "C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\WaterLevelSprites\\0.750.png";
        if (frac > 0.500)
            return "C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\WaterLevelSprites\\0.625.png";
        if (frac > 0.375)
            return "C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\WaterLevelSprites\\0.500.png";
        if (frac > 0.250)
            return "C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\WaterLevelSprites\\0.375.png";
        if (frac > 0.125)
            return "C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\WaterLevelSprites\\0.250.png";

        return "C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\WaterLevelSprites\\0.125.png";

    }
}
