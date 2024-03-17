import javax.swing.*;
import java.awt.*;

public class MySlider extends JSlider {
    private Image img = null;

    public MySlider() {
//        try {
//            img = ImageIO.read(new File("C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\scale.png"));
//
//        }catch (IOException e)
//        {
//            e.printStackTrace();
//        }
        this.setOpaque(false);
    }



    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(173, 188, 159));
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.dispose();

        super.paintComponent(g);
    }
//    @Override
//    public void paintComponent(Graphics g)
//    {
//        g.drawImage(img, 0, 0, null);
//        super.paintComponent(g);
//    }

}
