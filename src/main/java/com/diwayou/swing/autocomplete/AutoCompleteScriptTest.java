package com.diwayou.swing.autocomplete;

import com.diwayou.web.crawl.handler.ScriptHelper;
import com.google.common.collect.Lists;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.util.List;

public class AutoCompleteScriptTest extends JFrame {
    public AutoCompleteScriptTest() {
        JPanel contentPane = new JPanel(new BorderLayout());
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GROOVY);
        textArea.setCodeFoldingEnabled(true);
        contentPane.add(new RTextScrollPane(textArea));

        init(textArea);
        CompletionProvider provider = createCompletionProvider();

        AutoCompletion ac = new AutoCompletion(provider);
        ac.setTriggerKey(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        ac.install(textArea);

        setContentPane(contentPane);
        setTitle("AutoComplete Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }

    private void init(RSyntaxTextArea textArea) {
        String initScript = "package scripts\n" +
                "\n" +
                "import java.nio.file.Path\n" +
                "\n" +
                "if (helper.isImage()) {\n" +
                "    if (helper.contentLength() > 20 * 1000) {\n" +
                "        helper.storeWithIndex(Path.of(\"D:/tmp/image\"))\n" +
                "    }\n" +
                "    return\n" +
                "}\n" +
                "\n" +
                "helper.storeWithIndex(Path.of(\"D:/tmp/doc\"))\n" +
                "\n" +
                "return urls";
        textArea.setText(initScript);
    }

    /**
     * Create a simple provider that adds some Java-related completions.
     */
    private CompletionProvider createCompletionProvider() {

        DefaultCompletionProvider provider = new DefaultCompletionProvider();

        provider.addCompletion(new BasicCompletion(provider, "helper"));
        provider.addCompletions(getCompletions(provider, ScriptHelper.class));

        return provider;

    }

    private List<Completion> getCompletions(CompletionProvider provider, Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        List<Completion> result = Lists.newArrayListWithCapacity(methods.length);
        for (Method method : methods) {
            FunctionCompletion completion = new FunctionCompletion(provider, method.getName(), method.getReturnType().getName());

            result.add(completion);
        }

        return result;
    }

    public static void main(String[] args) {
        // Instantiate GUI on the EDT.
        SwingUtilities.invokeLater(() -> {
            try {
                String laf = UIManager.getSystemLookAndFeelClassName();
                UIManager.setLookAndFeel(laf);
            } catch (Exception e) { /* Never happens */ }
            new AutoCompleteScriptTest().setVisible(true);
        });
    }
}
