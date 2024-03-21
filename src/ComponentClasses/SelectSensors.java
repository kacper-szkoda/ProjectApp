package ComponentClasses;

import Control.SQLControl;
import Main.MyFrame;
import SupplementaryClasses.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SelectSensors extends JDialog implements ActionListener {

    private final JComboBox comboBoxL;
    private final JComboBox comboBoxM;
    private String sl, sm;
    private final JButton change;

    public SelectSensors(MyFrame owner, String title, boolean modal, ArrayList<Plant> curr) {
        super(owner, title, modal);
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel labelLight = new JLabel("Select plant for light sensor");
        labelLight.setBackground(new Color(156, 169, 143));
        labelLight.setFont(new Font("Helvetica", Font.BOLD, 25));
        labelLight.setForeground(Color.white);

        JLabel labelMoist = new JLabel("Select plant for moisture sensor");
        labelMoist.setBackground(new Color(156, 169, 143));
        labelMoist.setFont(new Font("Helvetica", Font.BOLD, 25));
        labelMoist.setForeground(Color.white);

        ArrayList<String> plant_names = new ArrayList<String>();
        for (Plant plant : curr) {
            plant_names.add(plant.getPlant_name());
        }

        comboBoxL = new JComboBox(plant_names.toArray());
        comboBoxL.setPreferredSize(new Dimension(200, 50));
        comboBoxL.setFont(new Font("Helvetica", Font.BOLD, 25));
        comboBoxL.setBackground(Color.decode("#829F77"));
        comboBoxL.setForeground(Color.white);
        comboBoxL.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80), new Color(18, 55, 42)));

        comboBoxM = new JComboBox(plant_names.toArray());
        comboBoxM.setPreferredSize(new Dimension(200, 50));
        comboBoxM.setFont(new Font("Helvetica", Font.BOLD, 25));
        comboBoxM.setBackground(Color.decode("#829F77"));
        comboBoxM.setForeground(Color.white);
        comboBoxM.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80), new Color(18, 55, 42)));

        change = new CustomButton("Change", 40);
        change.setPreferredSize(new Dimension(150, 75));
        change.addActionListener(this);

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(labelLight, gbc);

        gbc.gridx = 1;
        this.add(comboBoxL, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        this.add(labelMoist, gbc);

        gbc.gridx = 1;
        this.add(comboBoxM, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        this.add(change, gbc);

        this.getContentPane().setBackground(new Color(156, 169, 143));
        this.setPreferredSize(new Dimension(800, 800));
        this.pack();
        this.setVisible(true);
        this.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBoxL) {
            sl = (String) comboBoxL.getSelectedItem();
        }
        if (e.getSource() == comboBoxM) {
            sm = (String) comboBoxM.getSelectedItem();
        }
        if (e.getSource() == change) {
            sm = (String) comboBoxM.getSelectedItem();
            sl = (String) comboBoxL.getSelectedItem();
            SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/toggle/0/0/" + sm.replace(' ', '+') + "/" + 4);
            SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/toggle/0/0/" + sl.replace(' ', '+') + "/" + 5);
        }

    }
}
