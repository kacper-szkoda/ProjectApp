package ComponentClasses;

import Control.SQLControl;
import SupplementaryClasses.Plant;
import SupplementaryClasses.SliderGradient;
import SupplementaryClasses.TimerPanel;

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
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

public class PlantPanel extends TimerPanel implements ActionListener, MouseListener {
    private Plant examined_plant;
    private final java.util.Timer timer;
    private final SliderGradient sgl, sgm;
    private final JButton interrupt;
    private final JButton pump_button;
    private final JLabel pictureLabel;
    private final Hashtable<String, Integer> pref_to_int;
    public PlantPanel(Plant plant) {
        pref_to_int = new Hashtable<String, Integer>();
        pref_to_int.put("Dry", 25);
        pref_to_int.put("Slightly Dry", 50);
        pref_to_int.put("Moist", 75);
        pref_to_int.put("Very Moist", 100);

        examined_plant = plant;

        this.setBackground(new Color(173, 188, 159));

        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints c = new GridBagConstraints();

        pictureLabel = new JLabel();
        pictureLabel.addMouseListener(this);
        this.setPreferredSize(new Dimension(377, 550));
        updateImage(examined_plant.getPlant_img().getAbsolutePath());

        sgl = new SliderGradient();
        sgl.setValue(getLight());
        sgl.setBackground(new Color(173, 188, 159));
        sgl.setEnabled(false);

        sgm = new SliderGradient();
        sgm.setValue(getMoist());
        sgm.setEnabled(false);
        sgm.setBackground(new Color(173, 188, 159));

        pump_button = new CustomButton("Pump", 60);
        pump_button.addActionListener(this);

        interrupt = new CustomButton("Interrupt", 60);
        interrupt.addActionListener(this);

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
        this.add(sgl, c);

        c.gridx = 2;
        c.gridy = 1;
        this.add(sgm, c);

        c.gridy = 2;
        c.gridx = 2;
        c.gridwidth = 1;
        c.insets = new Insets(10, 10, 40, 40);
        this.add(pump_button, c);

        c.gridx = 4;
        c.insets = new Insets(10, 40, 40, 40);
        this.add(interrupt, c);

        timer = new java.util.Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                fetchDataAndUpdateUI();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 2000);
    }

    public Timer getTimer() {
        return timer;
    }

    private void fetchDataAndUpdateUI() {
        SwingUtilities.invokeLater(() -> {
            try {
                sgl.setValue(getLight());
                sgm.setValue(getMoist());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pump_button) {
            SQLControl.update("https://studev.groept.be/api/a23ib2a01/toggle/" + 1 + "/" + pref_to_int.get(examined_plant.getPref_moist_plant()) + "/" + examined_plant.getPlant_name().replace(' ', '+') + "/" + 1);
        }
        if (e.getSource() == interrupt) {
            SQLControl.update("https://studev.groept.be/api/a23ib2a01/toggle/" + 0 + "/" + pref_to_int.get(examined_plant.getPref_moist_plant()) + "/" + examined_plant.getPlant_name().replace(' ', '+') + "/" + 1);
        }
    }

    public int getMoist() {
        return SQLControl.parseJSONForMoist(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/get_moist_curr/" + examined_plant.getPlant_name().replace(' ', '+')));
    }

    public int getLight() {
        return SQLControl.parseJSONForLight(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/get_ldr_curr/" + examined_plant.getPlant_name().replace(' ', '+')));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
        }
        if (SwingUtilities.isRightMouseButton(e)) {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                updateImage(file.getAbsolutePath());
            }
        }

    }

    public void updateImage(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            Image scaled = image.getScaledInstance(451, 600, Image.SCALE_SMOOTH);
            examined_plant.setPlant_img(path);
            ImageIcon icon = new ImageIcon(scaled);

            int borderWidth = 1;
            int spaceAroundIcon = 0;
            Color borderColor = new Color(18, 55, 42);

            BufferedImage bi = new BufferedImage(icon.getIconWidth() + (2 * borderWidth + 2 * spaceAroundIcon), icon.getIconHeight() + (2 * borderWidth + 2 * spaceAroundIcon), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = bi.createGraphics();
            g.setColor(borderColor);
            g.drawImage(icon.getImage(), borderWidth + spaceAroundIcon, borderWidth + spaceAroundIcon, null);

            BasicStroke stroke = new BasicStroke(5); //5 pixels wide (thickness of the border)
            g.setStroke(stroke);

            g.drawRect(0, 0, bi.getWidth() - 1, bi.getHeight() - 1);
            g.dispose();

            pictureLabel.setIcon(new ImageIcon(bi));

        } catch (IOException e) {
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
}

