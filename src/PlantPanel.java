import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlantPanel extends JPanel implements ActionListener {
    private JButton interrupt, pump_button;
    private JLabel pictureLabel;
    private ScaleLabel scale_moist, scale_light;
    private float needed_moist;

    public PlantPanel(Plant plant) {
        this.setBackground(new Color(173, 188, 159));
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints c = new GridBagConstraints();
        pictureLabel = new JLabel();
        this.setPreferredSize(new Dimension(377,550));
        try {
            File img = new File("C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\71NAGPZug1L.jpg");
            BufferedImage image = ImageIO.read(img);
            Image scaled = image.getScaledInstance(377, 550, Image.SCALE_SMOOTH);
            pictureLabel.setIcon(new ImageIcon(scaled));
        }   catch (IOException e)
        {
            e.printStackTrace();
        }
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 3;
        c.insets = new Insets(20, 30, 20, 30);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        this.add(pictureLabel, c);
        c.gridy = 0;
        c.gridx = 2;
        c.gridwidth = 4;
        c.gridheight = 1;
        scale_light = new ScaleLabel("Light: " + SQLControl.parseJSONForLight(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/get_ldr_curr")));
        //JPanel light = new JPanel();
        //light.add(scale_light);
        //light.setPreferredSize(new Dimension(800, 165));
        //this.add(light, c);
        this.add(scale_light, c);
        c.gridx = 2;
        c.gridy = 1;
        scale_moist = new ScaleLabel("Moisture: " + SQLControl.parseJSONForMoist(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/get_moist_curr")));
        this.add(scale_moist, c);
        //c.gridx = 6;
        //c.gridy = 1;
        //c.gridwidth = 2;
        //c.gridheight = 1;
        pump_button = new PumpButton("Pump");
        pump_button.addActionListener(this);
        interrupt = new PumpButton("Interrupt");
        interrupt.addActionListener(this);
        Indicator m = new Indicator();
        Indicator m2 = new Indicator();
        setComponentZOrder(scale_moist, 1);
        setComponentZOrder(scale_light, 1);
        setComponentZOrder(m, 0);
        setComponentZOrder(m2, 0);
        c.gridx = 2;
        c.gridy = 0;
        this.add(m, c);
        c.gridx = 2;
        c.gridy = 1;
        this.add(m2, c);
        c.gridy = 2;
        c.gridx = 2;
        c.gridwidth = 1;
        this.add(pump_button, c);
        c.gridx = 4;
        this.add(interrupt, c);
    }


        @Override
        public void actionPerformed (ActionEvent e){
            if (e.getSource() == pump_button) {
                System.out.println("started");
                SQLControl.update("https://studev.groept.be/api/a23ib2a01/toggle/" + 1 + "/" + 1);
                SQLControl.update("https://studev.groept.be/api/a23ib2a01/toggle/" + 1 + "/" + 2);
                while (SQLControl.parseJSONForWeight(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/get_weight")) >= needed_moist) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex)
                    {
                        throw new RuntimeException(ex);
                    }
                }
                System.out.println("done");
                SQLControl.update("https://studev.groept.be/api/a23ib2a01/toggle/" + 0 + "/" +1);
            }
            if (e.getSource() == interrupt) {
                SQLControl.update("https://studev.groept.be/api/a23ib2a01/pump_interrupt");
            }
        }

//    public void addScale(ScaleLabel label){
//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(new File("/src/scale.png"));
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        Image dimg = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
//        ImageIcon image = new ImageIcon(dimg);
//        label.setIcon(image);
//    }
    }

