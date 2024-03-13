import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyFrame extends JFrame implements ActionListener
{
    private JPanel jPanel;
    private MyFrame.frame_state frame_state;
    private ArrayList<Plant> curr_plants;
    private JScrollPane scrollPane;
    private JPanel jPanel2;
    private JTextField to_seek;
    private JButton update_button, back;
    private ArrayList<PlantButton> buttons;
    private JLabel plant_name;

    enum frame_state {
        PLANT,
        OVERVIEW,
    }
    public MyFrame()
    {
        //this.frame_state = last;
        curr_plants = parseCurrentPlants();
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
        jPanel = new JPanel(new WrapLayout(FlowLayout.CENTER,400, 40));
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

        back = new JButton("back");
        back.addActionListener(this);
        jPanel.add(back);


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
        jPanel = new JPanel(new WrapLayout(FlowLayout.CENTER,400, 40));
        jPanel.setBackground(new Color(156, 169, 143));
        jPanel.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        this.add(jPanel, BorderLayout.NORTH);
        back = new JButton("back");
        back.addActionListener(this);
        jPanel.add(back);
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
        plant_name = new JLabel(temp.getPlant().getPlant_name());
        jPanel.add(plant_name);
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
