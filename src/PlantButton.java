import javax.swing.*;
import java.awt.*;

public class PlantButton extends JButton{
    private Plant plant;

    public Plant getPlant() {
        return plant;
    }

    public PlantButton(Plant plant) {
        this.plant = plant;
        this.setText(plant.getPlant_name());
        this.setFont(new Font("Helvetica", Font.BOLD, 40));
        this.setForeground(new Color(255, 255, 255));
        this.setBackground(Color.decode("#829F77"));
        this.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80), new Color(18, 55, 42)));
    }
}
