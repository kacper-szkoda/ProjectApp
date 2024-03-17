import javax.swing.*;
import java.awt.*;

public class PumpButton extends JButton 
{

    public PumpButton(String text) {
        super(text);
        this.setFont(new Font("Helvetica",Font.BOLD, 60));
        this.setForeground(new Color(255, 255, 255));
        this.setBackground(Color.decode("#829F77"));
        this.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        this.setPreferredSize(new Dimension(200, 75));
    }
}
