package ComponentClasses;

import SupplementaryClasses.TimerPanel;
import SupplementaryClasses.WrapLayout;

import java.awt.*;
import java.util.ArrayList;

public class OverviewPanel extends TimerPanel {

    public OverviewPanel(ArrayList<PlantButton> plants) {
        this.setLayout(new WrapLayout(FlowLayout.CENTER, 100, 40));
        this.setBackground(new Color(173, 188, 159));
        for (PlantButton button : plants) {
            this.add(button);
        }
        this.setSize(this.getPreferredSize());
        this.revalidate();
    }
}
