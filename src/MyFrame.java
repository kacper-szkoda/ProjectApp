import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

public class MyFrame extends JFrame implements ActionListener
{
    private GridBagLayout gbl;
    private GridBagConstraints c;
    private JPanel jPanel;
    private MyFrame.frame_state frame_state;
    private ArrayList<Plant> curr_plants;
    private JScrollPane scrollPane;
    private JPanel jPanel2;
    private JTextField to_seek;
    private JButton update_button, back;
    private ArrayList<PlantButton> buttons;
    private OutlinePanel plant_name;
    Hashtable<String, Color> stringToCol;
    private OutlinePanel pref_moist;
    private OutlinePanel pref_light;

    enum frame_state {
        PLANT,
        OVERVIEW,
    }
    public MyFrame()
    {
        //this.frame_state = last;
        curr_plants = parseCurrentPlants();
        StateStorage ss = new StateStorage(curr_plants);
        //Set up frame with 2 panels, one for control, one for display
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080, 1080);
        this.setTitle("Sensor Overview");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        //this.setResizable(true);
        this.getContentPane().setBackground(new Color(173, 188, 159));
        //Note, WrapLayout is a piece of open-source code. FlowLayout would try to put all components
        //in one row, which is not suited for a scrollable panel, WrapLayout specifically addresses that issue
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.gridx = 0;
        c.weighty = 1;
        c.weightx = 1;
        gbl = new GridBagLayout();
        jPanel = new JPanel(gbl);
        jPanel.setBackground(new Color(156, 169, 143));
        jPanel.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        this.add(jPanel, BorderLayout.NORTH);
        jPanel2 = new OverviewPanel(createButtons());
        this.add (jPanel2, BorderLayout.CENTER);

        //Set up button for updating
//        update_button = new JButton();
//        update_button.addActionListener(this);
//        update_button.setFocusable(false);
//        update_button.setText("Update");
//        update_button.setFont(new Font("Helvetica", Font.PLAIN, 30));
//        update_button.setForeground(new Color(18, 55, 42));
//        update_button.setBackground(new Color(140, 152, 129));
//        update_button.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
//        update_button.setPreferredSize(new Dimension(200, 75));

        //Set up a text field for user input of the sought after measurement unit
//        to_seek = new JTextField();
//        to_seek.setPreferredSize(new Dimension(200,80));
//        to_seek.setFont(new Font("Helvetica", Font.PLAIN, 30));
//        to_seek.setHorizontalAlignment(JTextField.CENTER);

        back = new PumpButton("Back");
        back.addActionListener(this);
        jPanel.add(back,c);


        //Make the panel scrollable, so that the app does not break with
        //larger number of sensors stored in the database
        // (can be tested with hPa or by adding more to the db)
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
        stringToCol.put("Slightly Dry", Color.decode("#68B058"));
        stringToCol.put("High", Color.decode("#68B058"));
        stringToCol.put("Medium", Color.decode("#32B197"));
        stringToCol.put("Slightly Moist", Color.decode("#32B197"));
        stringToCol.put("Moist", Color.decode("#01B2D1"));
        stringToCol.put("Low", Color.decode("#01B2D1"));
    }

    public ArrayList<Plant> parseCurrentPlants()
    {
        return SQLControl.pJSONForCurrent(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/getCurrent"));
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
        jPanel = new JPanel(gbl);
        c.gridy = 0;
        c.gridx = 0;
        jPanel.setBackground(new Color(156, 169, 143));
        jPanel.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        this.add(jPanel, BorderLayout.NORTH);
        back = new PumpButton("Back");
        back.addActionListener(this);
        jPanel.add(back, c);
    }

    public void makeOverviewBottom()
    {
        jPanel2 = new OverviewPanel(createButtons());
        this.add(jPanel2, BorderLayout.CENTER);
    }

    public void makePlantPanel(PlantButton temp)
    {
        jPanel2 = new PlantPanel(temp.getPlant());
        this.add(jPanel2, BorderLayout.CENTER);
        plant_name = new OutlinePanel(temp.getPlant().getPlant_name(), Color.black, Color.white);
        plant_name.setFont(new Font("Helvetica",Font.BOLD, 30));
        plant_name.setBackground(new Color(156, 169, 143));
        pref_moist = new OutlinePanel(temp.getPlant().getPref_moist_plant(), stringToCol.get(temp.getPlant().getPref_light_plant()), Color.white );
        pref_moist.setBackground(new Color(156, 169, 143));
        //pref_moist.setFont(new Font("Helvetica",Font.BOLD, 30));
        //.setForeground(stringToCol.get(temp.getPlant().getPref_moist_plant()));
        pref_light = new OutlinePanel(temp.getPlant().getPref_light_plant(), stringToCol.get(temp.getPlant().getPref_light_plant()), Color.white );
        pref_light.setBackground(new Color(156, 169, 143));
        //pref_light.setFont(new Font("Helvetica",Font.BOLD, 30));
        //pref_light.setForeground(stringToCol.get(temp.getPlant().getPref_light_plant()));
        c.gridy = 0;
        c.gridx = 1;
        c.gridwidth = 2;
        jPanel.add(plant_name, c);
        c.gridwidth = 1;
        c.gridx = 3;
        jPanel.add(pref_light, c);
        c.gridx = 4;
        jPanel.add(pref_moist, c);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Call update and refresh frame
        if (buttons.contains(e.getSource()))
        {
            this.remove(jPanel2);
            this.remove(scrollPane);
            makePlantPanel((PlantButton) e.getSource());
            this.getContentPane().revalidate();
        }
        if (e.getSource() == back)
        {
            this.remove(jPanel2);
            this.remove(scrollPane);
            this.remove(jPanel);
            makeOverviewTop();
            makeOverviewBottom();
            this.getContentPane().revalidate();
        }
    }
}
