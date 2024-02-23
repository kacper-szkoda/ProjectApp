import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PumpFrame extends JFrame implements ActionListener {

    private JButton interrupt;
    private JButton pump_button;

    public PumpFrame() {
        GridBagConstraints gbc = new GridBagConstraints();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080, 1080);
        this.setTitle("pump");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setResizable(true);
        this.getContentPane().setBackground(new Color(240, 253, 197));
        JPanel jPanel = new JPanel(new GridBagLayout());
        jPanel.setBackground(new Color(240, 253, 197));
        this.add(jPanel, BorderLayout.SOUTH);

//        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 300 , 20 ,300);

        pump_button = new JButton();
        pump_button.addActionListener(this);
        pump_button.setFocusable(false);
        pump_button.setText("Pump");
        pump_button.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        pump_button.setForeground(Color.white);
        pump_button.setBackground(new Color(221, 235, 134));
        pump_button.setBorder(BorderFactory.createEtchedBorder());
        jPanel.add(pump_button, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;

        interrupt = new JButton();
        interrupt.addActionListener(this);
        interrupt.setFocusable(false);
        interrupt.setText("Interrupt");
        interrupt.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        interrupt.setForeground(Color.white);
        interrupt.setBackground(new Color(221, 235, 134));
        interrupt.setBorder(BorderFactory.createEtchedBorder());
        jPanel.add(interrupt, gbc);


        this.pack();
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pump_button) {
            SQLControl.makePOSTRequest("https://studev.groept.be/api/a23ib2a01/all");
        }
        if (e.getSource() == interrupt) {
            SQLControl.makePOSTRequest("https://studev.groept.be/api/a23ib2a01/pump_interrupt");
        }
    }
}
