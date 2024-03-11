import javax.swing.*;

public class PlantButton extends JButton{
    private Plant plant;

    public Plant getPlant() {
        return plant;
    }

    public PlantButton(Plant plant) {
        this.plant = plant;
        this.setText(plant.getPlant_name());
    }


}
