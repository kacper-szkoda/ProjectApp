import Control.SQLControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PumpFrame extends JFrame implements ActionListener
{
    private JButton interrupt;
    private JButton pump_button;

    public PumpFrame()
    {
        //Set up frame with background
        GridBagConstraints gbc = new GridBagConstraints();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080, 1080);
        this.setTitle("pump");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setResizable(true);
        this.getContentPane().setBackground(new Color(173, 188, 159));
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,400, 40));
        jPanel.setBackground(new Color(173, 188, 159));
        this.add(jPanel, BorderLayout.SOUTH);

        //Set up button for pumping
        pump_button = new JButton();
        pump_button.addActionListener(this);
        pump_button.setFocusable(false);
        pump_button.setText("Pump");
        pump_button.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        pump_button.setForeground(new Color(18, 55, 42));
        pump_button.setBackground(new Color(130, 147, 108));
        pump_button.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        pump_button.setPreferredSize(new Dimension(200, 75));
        jPanel.add(pump_button, gbc);

        //Set up button for interruption
        interrupt = new JButton();
        interrupt.addActionListener(this);
        interrupt.setFocusable(false);
        interrupt.setText("Interrupt");
        interrupt.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        interrupt.setForeground(new Color(18, 55, 42));
        interrupt.setBackground(new Color(67, 104, 80));
        interrupt.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        interrupt.setPreferredSize(new Dimension(200, 75));
        jPanel.add(interrupt, gbc);

        //Show frame
        this.pack();
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Either execute query changing a control value to 1 or 0, RPi listens and turns pump on/off
        if (e.getSource() == pump_button)
        {
            SQLControl.update("https://studev.groept.be/api/a23ib2a01/all");
        }
        if (e.getSource() == interrupt)
        {
            SQLControl.update("https://studev.groept.be/api/a23ib2a01/pump_interrupt");
        }
    }
}
