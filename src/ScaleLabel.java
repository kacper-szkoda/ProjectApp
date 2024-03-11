import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScaleLabel extends JLabel {
    public ScaleLabel(String text) {
        super(text);
        this.setPreferredSize(new Dimension(1000,165));
        try {
            File img = new File("C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\scale.png");
            BufferedImage image = ImageIO.read(img);
            Image scaled = image.getScaledInstance(1000, 165, Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(scaled));
        }   catch (IOException e)
        {
            e.printStackTrace();
        }
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
        this.setBackground(Color.DARK_GRAY);
    }


}
