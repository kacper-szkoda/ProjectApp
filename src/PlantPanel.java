import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlantPanel extends JPanel implements ActionListener, MouseListener {
    private JButton interrupt, pump_button;
    private JLabel pictureLabel;
    private ScaleLabel scale_moist, scale_light;
    private float needed_moist;
    Plant examined_plant;
    //private File plant_img = new File("C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\71NAGPZug1L.jpg");

    StateStorage ss;
    public PlantPanel(Plant plant ) {
        this.setBackground(new Color(173, 188, 159));
        examined_plant = plant;
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints c = new GridBagConstraints();
        pictureLabel = new JLabel();
        pictureLabel.addMouseListener(this);
        this.setPreferredSize(new Dimension(377,550));
        updateImage(examined_plant.getPlant_img().getAbsolutePath());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 3;
        c.insets = new Insets(10, 80, 10, 10);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        this.add(pictureLabel, c);
        c.insets = new Insets(10, 10, 10, 40);
        c.gridy = 0;
        c.gridx = 2;
        c.gridwidth = 4;
        c.gridheight = 1;
        SliderGradient sgl = new SliderGradient();
        sgl.setValue(getLight());
        sgl.setBackground(new Color(173, 188, 159));
        sgl.setEnabled(false);
        this.add(sgl, c);
        c.gridx = 2;
        c.gridy = 1;
        SliderGradient sgm = new SliderGradient();
        sgm.setValue(getMoist());
        sgm.setEnabled(false);
        sgm.setBackground(new Color(173, 188, 159));
        this.add(sgm, c);
        pump_button = new PumpButton("Pump");
        pump_button.addActionListener(this);
        interrupt = new PumpButton("Interrupt");
        interrupt.addActionListener(this);
        c.gridy = 2;
        c.gridx = 2;
        c.gridwidth = 1;
        c.insets = new Insets(10, 10, 40, 40);
        this.add(pump_button, c);
        c.gridx = 4;
        c.insets = new Insets(10, 40, 40, 40);
        this.add(interrupt, c);
    }


        @Override
        public void actionPerformed (ActionEvent e){
            if (e.getSource() == pump_button) {
                SQLControl.update("https://studev.groept.be/api/a23ib2a01/toggle/" + 1 + "/" + 1);
            }
            if (e.getSource() == interrupt) {
                SQLControl.update("https://studev.groept.be/api/a23ib2a01/toggle/" + 0 + "/" + 1);
            }
        }

        public int getMoist()
        {
            return SQLControl.parseJSONForMoist(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/get_moist_curr/" + examined_plant.getPlant_name()));
        }

        public int getLight()
        {
            return SQLControl.parseJSONForLight(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/get_ldr_curr/" + examined_plant.getPlant_name()));
        }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
        {
            System.out.println("left");
        }
        if (SwingUtilities.isRightMouseButton(e))
        {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            System.out.println("right");
            if (response == JFileChooser.APPROVE_OPTION)
            {
                File file = new File(String.valueOf(fileChooser.getSelectedFile().getAbsolutePath()));
                updateImage(file.getAbsolutePath());
            }
        }

    }

    public void updateImage(String path)
    {
        try {
            System.out.println(path);
            BufferedImage image = ImageIO.read(new File(path));
            Image scaled = image.getScaledInstance(377, 550, Image.SCALE_SMOOTH);
            examined_plant.setPlant_img(path);
            pictureLabel.setIcon(new ImageIcon(scaled));
        }   catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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

