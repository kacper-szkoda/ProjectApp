import water_level_sprites.WateringCanSprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Hashtable;

public class MyFrame extends JFrame implements ActionListener, ComponentListener
{
    private GridBagLayout gbl;
    private GridBagConstraints c;
    private JPanel jPanel;
    //private MyFrame.frame_state frame_state;
    private ArrayList<Plant> curr_plants;
    private JScrollPane scrollPane;
    private TimerPanel jPanel2;
    private JButton  back;
    private ArrayList<PlantButton> buttons;
    private JLabel plant_name;
    Hashtable<String, Color> stringToCol;
    private OutlinePanel pref_moist;
    private OutlinePanel pref_light;
    private JButton weigh, tare;
    private JButton config;
    private JPanel overPanel;
    private GridBagLayout gbltop;
    private GridBagConstraints ctop;
    private SelectSensors ss;
    private JPanel jPanelAdd;
    private JButton add;
    private JTextField addPlant;

    //enum frame_state {
        //PLANT,
        //OVERVIEW,
    //}
    public MyFrame()
    {
        this.setIconImage(new ImageIcon("C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\can_icon.png").getImage());
        curr_plants = parseCurrentPlants();
        //StateStorage ss = new StateStorage(curr_plants);
        //Set up frame with 2 panels, one for control, one for display
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080, 1080);
        this.setTitle("Sensor Overview");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(new Color(173, 188, 159));
        jPanel2 = new OverviewPanel(createButtons());
        this.add (jPanel2, BorderLayout.CENTER);

        makeOverviewTop();
        makeOverviewPanelAdd();

        //Set up a text field for user input of the sought after measurement unit
//        to_seek = new JTextField();
//        to_seek.setPreferredSize(new Dimension(200,80));
//        to_seek.setFont(new Font("Helvetica", Font.PLAIN, 30));
//        to_seek.setHorizontalAlignment(JTextField.CENTER);


        //Make the panel scrollable, so that the app does not break with
        //larger number of sensors stored in the database
        //(can be tested with hPa or by adding more to the db)
        scrollPane = new JScrollPane(jPanel2);
        scrollPane.setPreferredSize(new Dimension(jPanel2.getSize()));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        this.add(scrollPane);

        //Add all components and prepare frame for being shown
        //jPanel.add(to_seek);
        //jPanel.add(update_button);
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
    }

    public ArrayList<Plant> parseCurrentPlants()
    {
        return SQLControl.pJSONForCurrent(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/getCurrent"));
    }

    public void setCurr_plants(ArrayList<Plant> curr_plants) {
        this.curr_plants = curr_plants;
    }

    public ArrayList<PlantButton> createButtons()
    {
        buttons = new ArrayList<PlantButton>();
        for (Plant plant : curr_plants)
        {
            buttons.add(new PlantButton(plant));
        }
        for (PlantButton button : buttons)
        {
            button.addActionListener(this);
        }
        return buttons;
    }

    public void makeOverviewTop ()
    {
        jPanel = new JPanel(new BorderLayout());
        jPanel.setBackground(new Color(156, 169, 143));
        jPanel.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        this.add(jPanel, BorderLayout.NORTH);
        if (back == null) {
            back = new JButton("Back");
            back.setFont(new Font("Helvetica", Font.BOLD, 40));
            back.setForeground(new Color(255, 255, 255));
            back.setBackground(Color.decode("#829F77"));
            back.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80), new Color(18, 55, 42)));
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
        ctop.gridx =1;
        ctop.weightx = 1;
        ctop.weighty =1;
        ctop.fill = GridBagConstraints.NONE;
        ctop.insets = new Insets(0, 10, 0, 10);
        config = new JButton("Config");
        config.setFont(new Font("Helvetica",Font.BOLD, 40));
        config.setForeground(new Color(255, 255, 255));
        config.setBackground(Color.decode("#829F77"));
        config.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        config.setPreferredSize(new Dimension(150, 75));
        config.addActionListener(this);
        overPanel.add(config, ctop);
        
        ctop.gridx = 2;
        tare = new JButton("Tare");
        tare.setFont(new Font("Helvetica",Font.BOLD, 40));
        tare.setForeground(new Color(255, 255, 255));
        tare.setBackground(Color.decode("#829F77"));
        tare.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        tare.setPreferredSize(new Dimension(150, 75));
        tare.addActionListener(this);
        overPanel.add(tare, ctop);

        ctop.gridx = 3;
        weigh = new JButton("Weigh");
        weigh.setFont(new Font("Helvetica",Font.BOLD, 40));
        weigh.setForeground(new Color(255, 255, 255));
        weigh.setBackground(Color.decode("#829F77"));
        weigh.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        weigh.setPreferredSize(new Dimension(150, 75));
        weigh.addActionListener(this);
        overPanel.add(weigh, ctop);

        ctop.gridx = 4;
        overPanel.add(new WateringCanSprite(), ctop);
    }

    public void makeOverviewBottom()
    {
        this.remove(jPanel2);
        jPanel2 = new OverviewPanel(createButtons());
        this.add(jPanel2, BorderLayout.CENTER);
        jPanel2.revalidate();
        this.getContentPane().revalidate();
    }

    public void makeOverviewPanelAdd()
    {

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout gbl = new GridBagLayout();

        jPanelAdd = new JPanel();
        jPanelAdd.setLayout(gbl);
        jPanelAdd.setBackground(new Color(156, 169, 143));
        jPanelAdd.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));


        addPlant = new JTextField();
        addPlant.setPreferredSize(new Dimension(350, 40));
        addPlant.setFont(new Font("Helvetica",Font.BOLD, 25));
        addPlant.setHorizontalAlignment(JTextField.CENTER);
        addPlant.setBackground(Color.decode("#829F77"));
        addPlant.setForeground(Color.white);
        addPlant.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));

        add = new JButton("Add");
        add.addActionListener(this);
        add.setFont(new Font("Helvetica",Font.BOLD, 40));
        add.setForeground(new Color(255, 255, 255));
        add.setBackground(Color.decode("#829F77"));
        add.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        add.setPreferredSize(new Dimension(150, 75));

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

    public void makePlantPanel(PlantButton temp)
    {
        jPanel2 = new PlantPanel(temp.getPlant());
        this.add(jPanel2, BorderLayout.CENTER);

//        jPanel = new JPanel(new BorderLayout());
//        jPanel.setBackground(new Color(156, 169, 143));
//        jPanel.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
//        this.add(jPanel, BorderLayout.NORTH);
//        jPanel.setLayout(new GridBagLayout());

        plant_name = new JLabel(temp.getPlant().getPlant_name());
        plant_name.setForeground(Color.white);
        plant_name.setHorizontalAlignment(JLabel.CENTER);
        plant_name.setFont(new Font("Helvetica",Font.BOLD, 50));
        plant_name.setBackground(Color.decode("#829F77"));

        pref_moist = new OutlinePanel(temp.getPlant().getPref_moist_plant(), stringToCol.get(temp.getPlant().getPref_moist_plant()), Color.white );
        pref_moist.setBackground(Color.decode("#829F77"));

        pref_light = new OutlinePanel(temp.getPlant().getPref_light_plant(), stringToCol.get(temp.getPlant().getPref_light_plant()), Color.white );
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
    public void actionPerformed(ActionEvent e)
    {
        //Call update and refresh frame
        if (buttons.contains(e.getSource()))
        {
            overPanel.remove(config);
            overPanel.remove(tare);
            overPanel.remove(weigh);
            jPanel.remove(overPanel);
            this.remove(jPanel2);
            this.remove(scrollPane);
            makePlantPanel((PlantButton) e.getSource());
            this.remove(jPanelAdd);
            this.getContentPane().revalidate();
            overPanel.revalidate();
            jPanel.revalidate();
        }
        if (e.getSource() == back)
        {
            try {
                jPanel2.getTimer().cancel();
            }
            catch (NullPointerException ex)
            {

            }
            this.remove(jPanel2);
            this.remove(scrollPane);
            back = null;
            this.remove(jPanel);
            makeOverviewTop();
            makeOverviewBottom();
            makeOverviewPanelAdd();
            this.getContentPane().revalidate();
        }
        if (e.getSource() == tare)
        {
            SQLControl.update("https://studev.groept.be/api/a23ib2a01/toggle/" + 1 + "/" + 0 + "/" + "tare/" +3);
        }
        if (e.getSource() == weigh)
        {
            SQLControl.update("https://studev.groept.be/api/a23ib2a01/toggle/" + 1 + "/" + 0 + "/"+ "weigh/" + 2);
        }
        if (e.getSource() == config)
        {
            ss = new  SelectSensors(this,  "Config", true, curr_plants);
        }
        if (e.getSource() == add)
        {
            int i = curr_plants.size();
            String txt = addPlant.getText();
            SQLControl.addNewCurrPlant(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/parseForPlant/" + txt.replace(" ", "+")));
            this.setCurr_plants(this.parseCurrentPlants());
            this.makeOverviewBottom();
            this.remove(jPanel2);
            this.remove(scrollPane);
            back = null;
            this.remove(jPanel);
            makeOverviewTop();
            makeOverviewBottom();
            this.getContentPane().revalidate();
            addPlant.setText("");
            if (curr_plants.size() > i)
            {
                curr_plants.get(curr_plants.size() -1).addNewPlantDefaultImg();
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
        if (e.getSource() == ss)
        {

        }
    }
}
