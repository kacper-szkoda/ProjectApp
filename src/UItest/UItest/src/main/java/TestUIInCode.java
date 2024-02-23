package UItest.UItest.src.main.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestUIInCode extends JFrame{
    private JTextField myText;
    private JButton myButton;
    private JLabel myLabel;
    private JPanel myPanel;


    public TestUIInCode(String title){
        //generate Jframe
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create a JPanel
        myPanel = new JPanel();

        // create a JLabel and add some placeholder text
        myLabel = new JLabel();
        myLabel.setText("This is placeholder text");

        // create a textfield and set the size to 10
        myText = new JTextField(10);


        //create a button
        myButton = new JButton();

        //configure the actionlistener for the button
        myButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                myLabel.setText(String.valueOf(Integer.parseInt(myText.getText()) + 1));
                myText.setText(String.valueOf(Integer.parseInt(myText.getText()) + 1));
            };
        });
        myPanel.add(myLabel);
        myPanel.add(myText);
        myPanel.add(myButton);

        //add the Jpanel to the Jframe
        this.setContentPane(myPanel);

    }


    //Code always starts running at main
    public static void main(String[] args) {
        //generate my UI
        JFrame ui= new TestUIInCode("Mytitle");
        //the frame needs to become visible
        ui.setVisible(true);
        ui.pack();
    }


}
