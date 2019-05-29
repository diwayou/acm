package com.diwayou.image;

import com.diwayou.web.http.robot.HttpRobot;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.w3c.dom.html.HTMLDocument;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

/**
 * This application is intended to demonstrate the loading of image files into icons
 * for use in a Swing user interface. It creates a toolbar with a thumbnail preview
 * of each image.  Clicking on the thumbnail will show the full image
 * in the main display area.
 * <p>
 * IconDemoApp.java requires the following files: <br>
 * The following files are copyright 2006 spriggs.net and licensed under a
 * Creative Commons License (http://creativecommons.org/licenses/by-sa/3.0/)
 * <br>
 * images/sunw01.jpg <br>
 * images/sunw02.jpg <br>
 * images/sunw03.jpg <br>
 * images/sunw04.jpg <br>
 * images/sunw05.jpg <br>
 *
 * @author Collin Fagan
 * @version 2.0
 * @date 7/25/2007
 */
public class ImageIconExample extends JFrame {

    private JLabel photographLabel = new JLabel();
    private JToolBar buttonBar = new JToolBar();

    private String imagedir = "images/";

    private ImageIcon placeholderIcon = new ImageIcon();

    /**
     * List of all the descriptions of the image files. These correspond one to
     * one with the image file names
     */
    private String[] imageCaptions = {"Original SUNW Logo", "The Clocktower",
            "Clocktower from the West", "The Mansion", "Sun Auditorium"};

    /**
     * List of all the image files to load.
     */
    private String[] imageFileNames = {"sunw01.jpg", "sunw02.jpg",
            "sunw03.jpg", "sunw04.jpg", "sunw05.jpg"};

    /**
     * Main entry point to the demo. Loads the Swing elements on the "Event
     * Dispatch Thread".
     *
     * @param args
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            ImageIconExample app = new ImageIconExample();
            app.setVisible(true);
        });
    }

    /**
     * Default constructor for the demo.
     */
    public ImageIconExample() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Icon Demo: Please Select an Image");

        // A label for displaying the pictures
        photographLabel.setVerticalTextPosition(JLabel.BOTTOM);
        photographLabel.setHorizontalTextPosition(JLabel.CENTER);
        photographLabel.setHorizontalAlignment(JLabel.CENTER);
        photographLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // We add two glue components. Later in process() we will add thumbnail buttons
        // to the toolbar inbetween thease glue compoents. This will center the
        // buttons in the toolbar.
        buttonBar.add(Box.createGlue());
        buttonBar.add(Box.createGlue());
        buttonBar.setRollover(true);

        add(buttonBar, BorderLayout.SOUTH);
        add(photographLabel, BorderLayout.CENTER);

        setSize(800, 600);

        // this centers the frame on the screen
        setLocationRelativeTo(null);

        // start the image loading SwingWorker in a background thread
        loadimages.execute();
    }

    public class LoadImagesWorker extends SwingWorker<Void, ImageIconExample.ThumbnailAction> {
        /**
         * Creates full size and thumbnail versions of the target image files.
         */
        @Override
        protected Void doInBackground() throws Exception {
            HttpRobot robot = new HttpRobot();
            HTMLDocument htmlDocument = robot.get("http://m.51tiangou.com").getHtmlDocument();
            String content = new W3CDom().asString(htmlDocument);
            Document document = Jsoup.parse(content);

            document.select("img[src]").stream()
                    .map(e -> e.attr("src"))
                    .filter(s -> !s.contains("log"))
                    .forEach(this::publishImage);

            // unfortunately we must return something, and only null is valid to
            // return when the return type is void.
            return null;
        }

        private void publishImage(String url) {
            ImageIcon icon;
            icon = createImageIcon("http:" + url, url);
            if (icon == null) {
                System.out.println("未读取到图片url=" + url);
                return;
            }

            if (icon.getIconWidth() < 400 || icon.getIconHeight() < 300) {
                System.out.println("图片太小url=" + url);
                return;
            }

            ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 32, 32));
            ThumbnailAction thumbAction = new ThumbnailAction(icon, thumbnailIcon, url);

            publish(thumbAction);
        }

        /**
         * Process all loaded images.
         */
        @Override
        protected void process(List<ThumbnailAction> chunks) {
            for (ThumbnailAction thumbAction : chunks) {
                JButton thumbButton = new JButton(thumbAction);
                // add the new button BEFORE the last glue
                // this centers the buttons in the toolbar
                buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);
            }
        }
    }

    private SwingWorker<Void, ThumbnailAction> loadimages = new LoadImagesWorker();

    /**
     * Creates an ImageIcon if the path is valid.
     *
     * @param url         - resource path
     * @param description - description of the file
     */
    protected ImageIcon createImageIcon(String url,
                                        String description) {
        try {
            BufferedImage image = ImageIO.read(new URL(url));

            return new ImageIcon(image, description);
        } catch (Exception e) {
            System.err.println("Couldn't find file: " + url);
            return null;
        }
    }

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     *
     * @param srcImg - source image to scale
     * @param w      - desired width
     * @param h      - desired height
     * @return - the new resized image
     */
    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    /**
     * Action class that shows the image specified in it's constructor.
     */
    private class ThumbnailAction extends AbstractAction {

        /**
         * The icon if the full image we want to display.
         */
        private Icon displayPhoto;

        /**
         * @param photo - The full size photo to show in the button.
         * @param thumb - The thumbnail to show in the button.
         * @param desc  - The descriptioon of the icon.
         */
        public ThumbnailAction(Icon photo, Icon thumb, String desc) {
            displayPhoto = photo;

            // The short description becomes the tooltip of a button.
            putValue(SHORT_DESCRIPTION, desc);

            // The LARGE_ICON_KEY is the key for setting the
            // icon when an Action is applied to a button.
            putValue(LARGE_ICON_KEY, thumb);
        }

        /**
         * Shows the full image in the main area and sets the application title.
         */
        public void actionPerformed(ActionEvent e) {
            photographLabel.setIcon(displayPhoto);
            setTitle("Icon Demo: " + getValue(SHORT_DESCRIPTION).toString());
        }
    }
}

