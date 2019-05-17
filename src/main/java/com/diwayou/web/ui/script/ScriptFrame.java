package com.diwayou.web.ui.script;

import com.diwayou.web.concurrent.FixedRejectThreadPoolExecutor;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.http.robot.HttpRobot;
import com.diwayou.web.log.AppLog;
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

    private static final Logger log = AppLog.getBrowser();

    private static final ExecutorService threadPool = new FixedRejectThreadPoolExecutor(1);

    public ScriptFrame(JFrame mainFrame) {
        JPanel cp = new JPanel(new BorderLayout());

        RSyntaxTextArea textArea = new RSyntaxTextArea(35, 120);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GROOVY);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        cp.add(sp, BorderLayout.CENTER);

        JButton exeButton = new JButton("执行");
        exeButton.addActionListener(ae -> {
            try {
                threadPool.submit(() -> {
                    try (HttpRobot robot = FetcherFactory.one().getFxWebviewFetcher().getRobot()) {
                        Map<String, Object> bindings = Maps.newHashMap();
                        bindings.put("spider", SpiderSingleton.one());
                        bindings.put("robot", robot);
                        ScriptRegistry.one().execute(textArea.getText(), bindings);
                    }
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
        setTitle("编写要执行的Groovy脚本");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(mainFrame);
    }
}
