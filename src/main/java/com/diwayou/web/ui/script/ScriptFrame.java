package com.diwayou.web.ui.script;

import com.diwayou.web.concurrent.FixedRejectThreadPoolExecutor;
import com.diwayou.web.script.ScriptRegistry;
import com.diwayou.web.ui.spider.SpiderSingleton;
import com.google.common.collect.Maps;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScriptFrame extends JFrame {

    private static final Logger log = Logger.getGlobal();

    private static final ExecutorService threadPool = new FixedRejectThreadPoolExecutor(1);

    public ScriptFrame(JFrame mainFrame) {
        JPanel cp = new JPanel(new BorderLayout());

        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GROOVY);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        cp.add(sp, BorderLayout.CENTER);

        JButton exeButton = new JButton("执行");
        exeButton.addActionListener(ae -> {
            try {
                threadPool.submit(() -> {
                    Map<String, Object> bindings = Maps.newHashMap();
                    bindings.put("spider", SpiderSingleton.one());
                    ScriptRegistry.one().execute(textArea.getText(), bindings);
                });
            } catch (RejectedExecutionException ree) {
                JOptionPane.showInternalMessageDialog(null, "当前有脚本正在执行", "警告", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                log.log(Level.WARNING, "", e);
            }

            this.dispose();
        });
        cp.add(exeButton, BorderLayout.SOUTH);

        setContentPane(cp);
        setTitle("编写要执行的脚本");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(mainFrame);
    }
}
