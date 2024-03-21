import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SelectSensors extends JDialog implements ActionListener{

    private JButton add;
    JTextField addPlant;
    private MyFrame owner;
    public JButton getAdd() {
        return add;
    }

    public SelectSensors(MyFrame owner, String title, boolean modal, ArrayList<Plant> curr) {
        super(owner, title, modal);
        this.owner = owner;
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel labelLight = new JLabel("Select plant for light sensor");
        labelLight.setBackground(new Color(156, 169, 143));
        labelLight.setFont(new Font("Helvetica",Font.BOLD, 25));
        labelLight.setForeground(Color.white);

        JLabel labelMoist = new JLabel("Select plant for moisture sensor");
        labelMoist.setBackground(new Color(156, 169, 143));
        labelMoist.setFont(new Font("Helvetica",Font.BOLD, 25));
        labelMoist.setForeground(Color.white);

        ArrayList<String> plant_names = new ArrayList<String>();
        for (Plant plant : curr)
        {
            plant_names.add(plant.getPlant_name());
        }

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(labelLight, gbc);

        gbc.gridx = 1;
        JComboBox comboBoxl = new JComboBox(plant_names.toArray());
        comboBoxl.setPreferredSize(new Dimension(200, 50));
        comboBoxl.setFont(new Font("Helvetica",Font.BOLD, 25));
        comboBoxl.setBackground(Color.decode("#829F77"));
        comboBoxl.setForeground(Color.white);
        comboBoxl.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        this.add(comboBoxl, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        this.add(labelMoist, gbc);

        gbc.gridx = 1;
        JComboBox comboBoxm = new JComboBox(plant_names.toArray());
        comboBoxm.setPreferredSize(new Dimension(200, 50));
        comboBoxm.setFont(new Font("Helvetica",Font.BOLD, 25));
        comboBoxm.setBackground(Color.decode("#829F77"));
        comboBoxm.setForeground(Color.white);
        comboBoxm.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        this.add(comboBoxm, gbc);

        this.getContentPane().setBackground(new Color(156, 169, 143));
        this.setPreferredSize(new Dimension(800, 800));
        this.pack();
        this.setVisible(true);
        this.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
