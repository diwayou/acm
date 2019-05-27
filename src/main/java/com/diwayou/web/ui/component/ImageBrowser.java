package com.diwayou.web.ui.component;

import com.diwayou.web.log.AppLog;
import com.diwayou.web.store.IndexFieldName;
import com.diwayou.web.store.IndexType;
import com.diwayou.web.store.QueryResult;
import com.diwayou.web.ui.query.ResultSearcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 浏览抓取的图片文件
 */
public class ImageBrowser extends JFrame {

    private static final Logger log = AppLog.getBrowser();

    private JLabel photographLabel = new JLabel();

    private JToolBar buttonBar = new JToolBar();

    private JScrollPane toolbarScrollPane;

    private int maxDisplayCount;

    private ResultSearcher searcher;

    public ImageBrowser(JFrame parent, int maxDisplayCount) {
        this.maxDisplayCount = maxDisplayCount;
        try {
            this.searcher = new ResultSearcher(maxDisplayCount, (q, result) -> {
                display(result);
            });
        } catch (IOException e) {
            log.log(Level.WARNING, "", e);

            JOptionPane.showInternalMessageDialog(null, "初始化浏览失败e=" + e.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(this::dispose);
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("浏览图片");

        // A label for displaying the pictures
        photographLabel.setVerticalTextPosition(JLabel.BOTTOM);
        photographLabel.setHorizontalTextPosition(JLabel.CENTER);
        photographLabel.setHorizontalAlignment(JLabel.CENTER);
        photographLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        photographLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() != 2) {
                    return;
                }

                JLabel imageLabel = (JLabel) me.getSource();
                ImageIcon icon = (ImageIcon) imageLabel.getIcon();
                if (icon == null) {
                    return;
                }

                try {
                    Desktop.getDesktop().open(new File(icon.getDescription()));
                } catch (IOException e) {
                    log.log(Level.WARNING, "", e);
                }
            }
        });

        buttonBar.setDoubleBuffered(true);

        JScrollPane imageScrollPane = new JScrollPane(this.photographLabel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(imageScrollPane, BorderLayout.CENTER);

        toolbarScrollPane = new JScrollPane(this.buttonBar);
        toolbarScrollPane.setPreferredSize(new Dimension(100, 100));

        add(toolbarScrollPane, BorderLayout.SOUTH);

        setSize(800, 600);

        setLocationRelativeTo(parent);

        ForkJoinPool.commonPool().execute(() -> {
            try {
                Query query = new TermQuery(new Term(IndexFieldName.type.name(), IndexType.image.name()));
                searcher.search(query);
            } catch (Exception e) {
                log.log(Level.WARNING, "", e);

                JOptionPane.showInternalMessageDialog(null, "查询图片失败e=" + e.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void loadNextPage() {
        ForkJoinPool.commonPool().execute(() -> {
            try {
                searcher.nextPage();
            } catch (IOException e) {
                log.log(Level.WARNING, "", e);

                JOptionPane.showInternalMessageDialog(null, "查询下一页失败e=" + e.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void display(QueryResult result) {
        if (result.getDocs().isEmpty()) {
            return;
        }

        for (Document doc : result.getDocs()) {
            String path = doc.get(IndexFieldName.content.name());

            ImageIcon icon = createImageIcon(path, path);
            if (icon == null) {
                continue;
            }

            ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 64, 64));
            ThumbnailAction thumbAction = new ThumbnailAction(icon, thumbnailIcon, path);

            SwingUtilities.invokeLater(() -> process(Collections.singletonList(thumbAction)));
        }
    }


    protected void process(List<ThumbnailAction> chunks) {
        for (ThumbnailAction thumbAction : chunks) {
            JButton thumbButton = new JButton(thumbAction);
            // add the new button BEFORE the last glue
            // this centers the buttons in the toolbar
            buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);
        }
    }

    protected ImageIcon createImageIcon(String path, String description) {
        try {
            BufferedImage image = ImageIO.read(new File(path));

            return new ImageIcon(image, description);
        } catch (Exception e) {
            log.log(Level.WARNING, "Couldn't find file: " + path);
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
        @Override
        public void actionPerformed(ActionEvent e) {
            photographLabel.setIcon(displayPhoto);
            setTitle(getValue(SHORT_DESCRIPTION).toString());

            Component source = (Component) e.getSource();
            JToolBar toolBar = ImageBrowser.this.buttonBar;
            int idx = toolBar.getComponentIndex(source);

            if (idx >= toolBar.getComponentCount() - 2) {
                loadNextPage();
            }

            if (idx < toolBar.getComponentCount() - 1) {
                Component next = toolBar.getComponent(Math.min(idx + 1, toolBar.getComponentCount() - 1));
                toolBar.scrollRectToVisible(next.getBounds());
            }
        }
    }
}

