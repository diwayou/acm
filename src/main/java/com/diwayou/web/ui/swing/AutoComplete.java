package com.diwayou.web.ui.swing;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AutoComplete implements DocumentListener {
    private static enum Mode {
        INSERT,
        COMPLETION
    }

    ;

    private JTextField textField;
    private final List<String> keywords;
    private Mode mode = Mode.INSERT;

    public AutoComplete(JTextField textField, List<String> keywords) {
        this.textField = textField;
        this.keywords = keywords.stream()
                .map(k -> k.substring(k.indexOf(".") + 1))
                .collect(Collectors.toList());
        Collections.sort(keywords);
    }

    @Override
    public void changedUpdate(DocumentEvent ev) {
    }

    @Override
    public void removeUpdate(DocumentEvent ev) {
    }

    @Override
    public void insertUpdate(DocumentEvent ev) {
        if (ev.getLength() != 1)
            return;

        int pos = ev.getOffset();
        String content = null;
        try {
            content = textField.getText(0, pos + 1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // Too few chars
        if (pos < 1)
            return;

        int n = Collections.binarySearch(keywords, content);
        if (n < 0 && -n <= keywords.size()) {
            String match = keywords.get(-n - 1);
            if (match.startsWith(content)) {
                // A completion is found
                String completion = match.substring(pos + 1);
                // We cannot modify Document from within notification,
                // so we submit a task that does the change later
                SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
            }
        } else {
            // Nothing found
            mode = Mode.INSERT;
        }
    }

    public class CommitAction extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 5794543109646743416L;

        @Override
        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = textField.getSelectionEnd();
                StringBuffer sb = new StringBuffer(textField.getText());
                sb.insert(pos, " ");
                textField.setText(sb.toString());
                textField.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            } else {
                textField.replaceSelection("\t");
            }
        }
    }

    private class CompletionTask implements Runnable {
        private String completion;
        private int position;

        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }

        public void run() {
            StringBuffer sb = new StringBuffer(textField.getText());
            String sub = sb.substring(position);
            if (completion.endsWith(sub)) {
                sb.delete(position, sb.length());
            }
            sb.insert(position, completion);

            textField.setText(sb.toString());
            textField.setCaretPosition(position + completion.length());
            textField.moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
    }
}
