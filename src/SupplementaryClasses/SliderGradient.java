package SupplementaryClasses;

import javax.swing.*;
import java.awt.*;

public class SliderGradient extends JSlider {

    public Color getTicksColor() {
        return ticksColor;
    }

    public void setTicksColor(Color ticksColor) {
        this.ticksColor = ticksColor;
    }

    public int getTrackSize() {
        return trackSize;
    }

    public void setTrackSize(int trackSize) {
        this.trackSize = trackSize;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    private Color color1 = new Color(165, 175, 18);
    private Color color2 = new Color(0, 179, 211);
    private Color ticksColor = new Color(0, 0, 0);
    private int trackSize = 50;

    public SliderGradient(String title) {
        setUI(new SliderGradientUI(this, title ));
    }
}
