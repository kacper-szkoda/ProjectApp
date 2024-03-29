package ComponentClasses;

import javax.swing.*;
import java.awt.*;

public class OutlinePanel extends JLabel {
    private final String text;
    private final Color textColor;
    private final Color outlineColor;

    public OutlinePanel(String text, Color textColor, Color outlineColor) {
        this.text = text;
        this.textColor = textColor;
        this.outlineColor = outlineColor;
        this.setPreferredSize(new Dimension(400, 40));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set rendering hints for smoother text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Set font and get its metrics
        Font font = new Font("Arial", Font.BOLD, 36);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);

        // Calculate text position
        int x = (getWidth() - metrics.stringWidth(text)) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        // Draw outline
        g2d.setColor(outlineColor);
        int outlineThickness = 2;
        for (int xo = -outlineThickness; xo <= outlineThickness; xo++) {
            for (int yo = -outlineThickness; yo <= outlineThickness; yo++) {
                if (xo != 0 || yo != 0) {
                    g2d.drawString(text, x + xo, y + yo);
                }
            }
        }

        // Draw text
        g2d.setColor(textColor);
        g2d.drawString(text, x, y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 100); // Adjust size as needed
    }
}
