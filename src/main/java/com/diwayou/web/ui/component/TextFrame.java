package com.diwayou.web.ui.component;

import com.diwayou.web.log.AppLog;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class TextFrame extends JFrame {

    private static final Logger log = AppLog.getBrowser();

    public TextFrame(JFrame parentFrame, String title, String content) {
        JPanel cp = new JPanel(new BorderLayout());

        RSyntaxTextArea textArea = new RSyntaxTextArea(35, 120);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        textArea.setCodeFoldingEnabled(true);

        textArea.setText(content);

        RTextScrollPane sp = new RTextScrollPane(textArea);
        cp.add(sp, BorderLayout.CENTER);


        setContentPane(cp);
        setTitle(title);
        cp.setSize(200, 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parentFrame);
    }
}
