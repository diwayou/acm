package com.diwayou.awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AntialiasedText extends JPanel {

    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D)g;

        String text = "The quick brown fox jumped over the lazy dog";
        Font font = new Font(Font.SERIF, Font.PLAIN, 32);
        g2d.setFont(font);

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2d.drawString(text, 20, 30);

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.drawString(text, 20, 70);

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g2d.drawString(text, 20, 110);

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.drawString(text, 20, 150);
    }

    public static void main(String[] args) {

        Frame f = new Frame("Antialiased Text Sample");

        f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.add(new AntialiasedText());
        f.setSize(new Dimension(800, 360));
        f.setVisible(true);
    }
}
