import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OverviewPanel extends JPanel{
    private ArrayList<PlantButton> plants;

    public OverviewPanel(ArrayList<PlantButton> plants) {
        this.plants = plants;
        this.setLayout(new WrapLayout(FlowLayout.CENTER, 100, 40));
        this.setBackground(new Color(173, 188, 159));
        for (PlantButton button : plants)
        {
            this.add(button);
        }
    }
}
