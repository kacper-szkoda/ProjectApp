package ComponentClasses;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {

    public CustomButton(String text, int size) {
        super(text);
        this.setFont(new Font("Helvetica", Font.BOLD, size));
        this.setForeground(new Color(255, 255, 255));
        this.setBackground(Color.decode("#829F77"));
        this.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80), new Color(18, 55, 42)));
    }
}
