import javax.swing.*;
import java.awt.*;

public class PumpButton extends JButton 
{

    public PumpButton(String text) {
        super(text);
        this.setFont(new Font("Helvetica",Font.PLAIN, 30));
        this.setForeground(new Color(18, 55, 42));
        this.setBackground(new Color(130, 147, 108));
        this.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        this.setPreferredSize(new Dimension(200, 75));
    }
}
