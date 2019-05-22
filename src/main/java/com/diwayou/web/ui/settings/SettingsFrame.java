package com.diwayou.web.ui.settings;

import com.diwayou.web.config.AppConfig;
import com.diwayou.web.log.AppLog;
import com.diwayou.web.ui.component.IntTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Logger;

public class SettingsFrame extends JFrame {
    private static final Logger log = AppLog.getBrowser();

    public SettingsFrame(JFrame mainFrame) {
        setLayout(new BorderLayout());

        addSettingsPanel();

        setTitle("设置");
        setSize(800, 600);
        setMinimumSize(new Dimension(200, 200));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(mainFrame);
    }

    private void addSettingsPanel() {
        JPanel settingsPanel = new JPanel(new GridLayout(4, 1));

        addSetting(settingsPanel, "图片", AppConfig.isStoreImage(), ie -> {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                AppConfig.toggleImage(true);
            } else {
                AppConfig.toggleImage(false);
            }
        });
        addSetting(settingsPanel, "文档", AppConfig.isStoreDoc(), ie -> {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                AppConfig.toggleDoc(true);
            } else {
                AppConfig.toggleDoc(false);
            }
        });
        addSetting(settingsPanel, "页面", AppConfig.isStoreHtml(), ie -> {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                AppConfig.toggleHtml(true);
            } else {
                AppConfig.toggleHtml(false);
            }
        });

        addImageLength(settingsPanel);

        add(settingsPanel, BorderLayout.CENTER);
    }

    private void addImageLength(JPanel settingsPanel) {
        JPanel imageLengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel("抓取图片大小限制(字节): ");
        imageLengthPanel.add(label);

        IntTextField textField = new IntTextField(AppConfig.getImageLength(), 10);
        textField.addActionListener(ae -> {
            AppConfig.setImageLengthLimit(textField.getValue());
        });
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                AppConfig.setImageLengthLimit(textField.getValue());
            }
        });
        imageLengthPanel.add(textField);

        settingsPanel.add(imageLengthPanel);
    }

    private void addSetting(JPanel settingsPanel, String title, boolean state, ItemListener listener) {
        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Checkbox checkbox = new Checkbox(title, state);
        checkBoxPanel.add(checkbox);
        checkbox.addItemListener(listener);

        settingsPanel.add(checkBoxPanel);
    }
}
