package com.diwayou.web.ui.component;

import com.diwayou.web.log.AppLog;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextAreaHighlighter;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class TextFrame extends JFrame {

    private static final Logger log = AppLog.getBrowser();

    public TextFrame(JFrame parentFrame, String title, String content) {
        JPanel cp = new JPanel(new BorderLayout());

        RTextArea textArea = new RTextArea(35, 120);

        SwingUtilities.invokeLater(() -> {
            textArea.setText(content);
            RTextAreaHighlighter highlighter = new RTextAreaHighlighter();
            textArea.setHighlighter(highlighter);

            SearchContext context = new SearchContext(title);
            context.setMarkAll(true);
            SearchEngine.markAll(textArea, context);
        });

        JScrollPane sp = new JScrollPane(textArea);
        cp.add(sp, BorderLayout.CENTER);

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    TextFrame.this.dispose();
                }
            }
        });

        setContentPane(cp);
        setTitle(title);
        cp.setSize(200, 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parentFrame);
    }
}
