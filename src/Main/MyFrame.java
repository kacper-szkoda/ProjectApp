package Main;

import ComponentClasses.*;
import Control.SQLControl;
import SupplementaryClasses.Plant;
import SupplementaryClasses.TimerPanel;
import WaterLevelSprites.WateringCanSprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Hashtable;

public class MyFrame extends JFrame implements ActionListener, ComponentListener {
    Hashtable<String, Color> stringToCol;
    private JPanel jPanel, overPanel, jPanelAdd;
    private ArrayList<Plant> curr_plants;
    private JScrollPane scrollPane;
    private TimerPanel jPanel2;
    private ArrayList<PlantButton> buttons;
    private JLabel plant_name;
    private OutlinePanel pref_moist, pref_light;
    private CustomButton weigh, tare, config, back, add;
    private GridBagLayout gbltop;
    private GridBagConstraints ctop;
    private SelectSensors ss;
    private JTextField addPlant;

    public MyFrame() {
        this.setIconImage(new ImageIcon("src/generic_plant.png").getImage());
        curr_plants = parseCurrentPlants();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080, 1080);
        this.setTitle("Sensor Overview");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(new Color(173, 188, 159));
        jPanel2 = new OverviewPanel(createButtons());
        this.add(jPanel2, BorderLayout.CENTER);

//        scrollPane = new JScrollPane(jPanel2);
//        scrollPane.setPreferredSize(new Dimension(jPanel2.getSize()));
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
//        this.add(scrollPane);

        makeOverviewTop();
        makeOverviewBottom();
        makeOverviewPanelAdd();

        this.pack();
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        stringToCol = new Hashtable<String, Color>();
        stringToCol.put("Direct Sun", Color.decode("#A0AF17"));
        stringToCol.put("Dry", Color.decode("#A0AF17"));
        stringToCol.put("Slightly Dry", Color.decode("#66B05B"));
        stringToCol.put("High", Color.decode("#66B05B"));
        stringToCol.put("Medium", Color.decode("#2DB19D"));
        stringToCol.put("Slightly Moist", Color.decode("#2DB19D"));
        stringToCol.put("Moist", Color.decode("#01B2D1"));
        stringToCol.put("Low", Color.decode("#01B2D1"));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.revalidate();
    }

    public ArrayList<Plant> parseCurrentPlants()
    {
        return SQLControl.pJSONForCurrent(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/getCurrent"));
    }

    public void setCurr_plants(ArrayList<Plant> curr_plants) {
        this.curr_plants = curr_plants;
    }

    public ArrayList<PlantButton> createButtons() {
        buttons = new ArrayList<PlantButton>();
        for (Plant plant : curr_plants) {
            buttons.add(new PlantButton(plant));
        }
        for (PlantButton button : buttons) {
            button.addActionListener(this);
        }
        return buttons;
    }

    public void makeOverviewTop() {
        jPanel = new JPanel(new BorderLayout());
        jPanel.setBackground(new Color(156, 169, 143));
        jPanel.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80), new Color(18, 55, 42)));
        this.add(jPanel, BorderLayout.NORTH);
        if (back == null) {
            back = new CustomButton("Back", 40);
            back.setPreferredSize(new Dimension(150, 75));
            back.addActionListener(this);
            jPanel.add(back, BorderLayout.WEST);
        }

        overPanel = new JPanel();
        jPanel.add(overPanel, BorderLayout.CENTER);
        overPanel.setBackground(new Color(156, 169, 143));

        gbltop = new GridBagLayout();
        ctop = new GridBagConstraints();
        overPanel.setLayout(gbltop);

        ctop.gridy = 0;
        ctop.gridx = 1;
        ctop.weightx = 1;
        ctop.weighty = 1;
        ctop.fill = GridBagConstraints.NONE;
        ctop.insets = new Insets(0, 10, 0, 10);
        config = new CustomButton("Config", 40);
        config.setPreferredSize(new Dimension(150, 75));
        config.addActionListener(this);
        overPanel.add(config, ctop);

        ctop.gridx = 2;
        tare = new CustomButton("Tare", 40);
        tare.setPreferredSize(new Dimension(150, 75));
        tare.addActionListener(this);
        overPanel.add(tare, ctop);

        ctop.gridx = 3;
        weigh = new CustomButton("Weigh", 40);
        weigh.setPreferredSize(new Dimension(150, 75));
        weigh.addActionListener(this);
        overPanel.add(weigh, ctop);

        ctop.gridx = 4;
        overPanel.add(new WateringCanSprite(), ctop);
    }

    public void makeOverviewBottom() {
        this.remove(jPanel2);
        jPanel2 = new OverviewPanel(createButtons());
        this.add(jPanel2, BorderLayout.CENTER);
        jPanel2.revalidate();
        this.getContentPane().revalidate();
    }

    public void makeOverviewPanelAdd() {

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout gbl = new GridBagLayout();

        jPanelAdd = new JPanel();
        jPanelAdd.setLayout(gbl);
        jPanelAdd.setBackground(new Color(156, 169, 143));
        jPanelAdd.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80), new Color(18, 55, 42)));

        addPlant = new JTextField();
        addPlant.setPreferredSize(new Dimension(350, 40));
        addPlant.setFont(new Font("Helvetica", Font.BOLD, 25));
        addPlant.setHorizontalAlignment(JTextField.CENTER);
        addPlant.setBackground(Color.decode("#829F77"));
        addPlant.setForeground(Color.white);
        addPlant.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80), new Color(18, 55, 42)));

        add = new CustomButton("Add",40);
        add.setPreferredSize(new Dimension(150, 75));
        add.addActionListener(this);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 1;
        gbc.weightx = 1;
        jPanelAdd.add(addPlant, gbc);

        gbc.gridx = 1;
        jPanelAdd.add(add, gbc);

        this.add(jPanelAdd, BorderLayout.SOUTH);
        this.revalidate();
    }

    public void makePlantPanel(PlantButton temp) {
        jPanel2 = new PlantPanel(temp.getPlant());
        this.add(jPanel2, BorderLayout.CENTER);

        plant_name = new JLabel(temp.getPlant().getPlant_name());
        plant_name.setForeground(Color.white);
        plant_name.setHorizontalAlignment(JLabel.CENTER);
        plant_name.setFont(new Font("Helvetica", Font.BOLD, 50));
        plant_name.setBackground(Color.decode("#829F77"));

        pref_moist = new OutlinePanel(temp.getPlant().getPref_moist_plant(), stringToCol.get(temp.getPlant().getPref_moist_plant()), Color.white);
        pref_moist.setBackground(Color.decode("#829F77"));

        pref_light = new OutlinePanel(temp.getPlant().getPref_light_plant(), stringToCol.get(temp.getPlant().getPref_light_plant()), Color.white);
        pref_light.setBackground(Color.decode("#829F77"));


        overPanel = new JPanel();
        jPanel.add(overPanel, BorderLayout.CENTER);
        overPanel.setBackground(new Color(156, 169, 143));

        gbltop = new GridBagLayout();
        ctop = new GridBagConstraints();
        overPanel.setLayout(gbltop);

        ctop.weighty = 1;
        ctop.fill = GridBagConstraints.VERTICAL;
        ctop.weightx = 1;

        ctop.gridy = 0;
        ctop.gridx = 1;
        ctop.gridwidth = 2;
        overPanel.add(plant_name, ctop);

        ctop.gridwidth = 1;
        ctop.gridx = 3;
        overPanel.add(pref_light, ctop);

        ctop.gridx = 4;
        overPanel.add(pref_moist, ctop);
        overPanel.revalidate();
    }

    public TimerPanel getjPanel2() {
        return jPanel2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Call update and refresh frame
        if (buttons.contains(e.getSource())) {
            overPanel.remove(config);
            overPanel.remove(tare);
            overPanel.remove(weigh);
            jPanel.remove(overPanel);
            this.remove(jPanel2);
            //this.remove(scrollPane);
            makePlantPanel((PlantButton) e.getSource());
            this.remove(jPanelAdd);
            this.getContentPane().revalidate();
            overPanel.revalidate();
            jPanel.revalidate();
        }
        if (e.getSource() == back) {
            try {
                jPanel2.getTimer().cancel();
            } catch (NullPointerException ex) {

            }
            this.remove(jPanel2);
            //this.remove(scrollPane);
            back = null;
            this.remove(jPanel);
            makeOverviewTop();
            makeOverviewBottom();
            makeOverviewPanelAdd();
            this.getContentPane().revalidate();
        }
        if (e.getSource() == tare) {
             SQLControl.update("https://studev.groept.be/api/a23ib2a01/toggle/" + 1 + "/" + 0 + "/" + "tare/" + 3);
        }
        if (e.getSource() == weigh) {
            SQLControl.update("https://studev.groept.be/api/a23ib2a01/toggle/" + 1 + "/" + 0 + "/" + "weigh/" + 2);
        }
        if (e.getSource() == config) {
            ss = new SelectSensors(this, "Config", true, curr_plants);
        }
        if (e.getSource() == add) {
            int i = curr_plants.size();
            String txt = addPlant.getText();
            SQLControl.addNewCurrPlant(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/parseForPlant/" + txt.replace(" ", "+")));
            this.setCurr_plants(this.parseCurrentPlants());
            this.makeOverviewBottom();
            this.remove(jPanel2);
            back = null;
            this.remove(jPanel);
            makeOverviewTop();
            makeOverviewBottom();
            this.getContentPane().revalidate();
            addPlant.setText("");
            if (curr_plants.size() > i) {
                curr_plants.get(curr_plants.size() - 1).addNewPlantDefaultImg();
            }
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {
        if (e.getSource() == ss) {

        }
    }
}
