package com.diwayou.awt;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

class SeeThroughComponent extends Component {

    private BufferedImage bi;
    float[] scales = {1f, 1f, 1f, 0.5f};
    float[] offsets = new float[4];
    RescaleOp rop;

    public SeeThroughComponent(URL imageSrc) {
        try {
            BufferedImage img = ImageIO.read(imageSrc);
            int w = img.getWidth(null);
            int h = img.getHeight(null);
            bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.getGraphics();
            g.drawImage(img, 0, 0, null);

        } catch (IOException e) {
            System.out.println("Image could not be read");
//            System.exit(1);
        }
        setOpacity(0.5f);
    }

    public Dimension getPreferredSize() {
        return new Dimension(bi.getWidth(null), bi.getHeight(null));
    }

    public void setOpacity(float opacity) {
        scales[3] = opacity;
        rop = new RescaleOp(scales, offsets, null);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Dialog", Font.BOLD, 24));
        g2d.drawString("Java 2D is great!", 10, 80);
        g2d.drawImage(bi, rop, 0, 0);
    }
}

public class SeeThroughImage extends JPanel {

    static String imageFileName = "/images/duke_skateboard.jpg";
    private URL imageSrc;

    public SeeThroughImage() {
    }

    public SeeThroughImage(URL imageSrc) {
        this.imageSrc = imageSrc;
    }

    public void init() {
        imageSrc = SeeThroughImage.class.getResource(imageFileName);
        buildUI();
    }

    public void buildUI() {
        final SeeThroughComponent st = new SeeThroughComponent(imageSrc);
        add("Center", st);
        JSlider opacitySlider = new JSlider(0, 100);
        opacitySlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                st.setOpacity(slider.getValue() / 100f);
                st.repaint();
            }

            ;
        });
        Dimension size = st.getPreferredSize();
        Dimension sliderSize = opacitySlider.getPreferredSize();
        resize(size.width, size.height + sliderSize.height);
        add("South", opacitySlider);
    }

    public static void main(String s[]) {
        JFrame f = new JFrame("See Through Image");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        URL imageSrc = null;
        try {
            imageSrc = ((new File(imageFileName)).toURI()).toURL();
        } catch (MalformedURLException e) {
        }
        SeeThroughImage sta = new SeeThroughImage(imageSrc);
        sta.init();
        f.add("Center", sta);
        f.pack();
        f.setVisible(true);
    }
}
