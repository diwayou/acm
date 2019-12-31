package com.diwayou.awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.util.Hashtable;

public class AttributedText extends JPanel {

    public void paint(Graphics g) {

        Font font = new Font(Font.SERIF, Font.PLAIN, 24);
        g.setFont(font);
        String text = "Too WAVY";
        g.drawString(text, 45, 30);

        Hashtable<TextAttribute, Object> map =
                new Hashtable<TextAttribute, Object>();

        /* Kerning makes the text spacing more natural */
        map.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);
        font = font.deriveFont(map);
        g.setFont(font);
        g.drawString(text, 45, 60);

        /* Underlining is easy */
        map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        font = font.deriveFont(map);
        g.setFont(font);
        g.drawString(text, 45, 90);

        /* Strikethrouh is easy */
        map.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        font = font.deriveFont(map);
        g.setFont(font);
        g.drawString(text, 45, 120);

        /* This colour applies just to the font, not other rendering */
        map.put(TextAttribute.FOREGROUND, Color.BLUE);
        font = font.deriveFont(map);
        g.setFont(font);
        g.drawString(text, 45, 150);
    }

    public static void main(String[] args) {

        Frame f = new Frame("Attributed Text Sample");

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.add("Center", new AttributedText());
        f.setSize(new Dimension(250, 200));
        f.setVisible(true);
    }
}

